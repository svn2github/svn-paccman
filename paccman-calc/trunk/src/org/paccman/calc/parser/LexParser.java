/*
 * LexParser.java
 *
 * Created on 11 sept. 2007, 06:17:28
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paccman.calc.parser;

import java.math.BigDecimal;
import org.paccman.calc.parser.LexParser.ParseException;

/**
 *
 * @author joao
 */
public class LexParser {

    YaccParser yaccParser;

    /**
     *
     * @param yaccParser
     */
    public LexParser(YaccParser yaccParser) {
        this.yaccParser = yaccParser;
    }

    /**
     *
     */
    public static class ParseException extends Exception {

        private ParseException(char c) {
            super("Unexpected char: " + Character.toString(c));
        }

        private ParseException(String msg) {
            super(msg);
        }
    }
    // Parser context data

    StringBuffer operandValue;
    int parenLvl = 0;

    enum State {

        Init, Idle, ParseOperand, WaitOp, ReadOp, WaitNext
    }
    State state;

    /**
     *
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * 
     */
    public void reset() throws ParseException {
        state = State.Idle;
        operandValue = new StringBuffer();
        parseChar('0');
    }

    /**
     *
     * @param c
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * 
     */
    public void parseChar(char c) throws ParseException {
        switch (state) {
            case Idle:
                state = doIdle(c);
                break;
            case ParseOperand:
                state = doParseOperand(c);
                break;
            case WaitNext:
                state = doWaitNext(c);
                break;
            case WaitOp:
                state = doWaitOp(c);
                break;
            case ReadOp:
                state = doReadOp(c);
                break;
            default:
                throw new IllegalStateException("Invalid state");
        }
    }

    private State doIdle(char c) throws ParseException {
        if (Character.isDigit(c)) {
            if (operandValue.toString().equals("0")) {
                operandValue = new StringBuffer();
            }
            operandValue = new StringBuffer(new String(new char[]{c}));
            return State.ParseOperand;
        } else if (c == LexToken.DEC_POINT) {
            operandValue = new StringBuffer("0.");
            return State.ParseOperand;
        } else if (c == LexToken.SIGN_CHAR) {
            yaccParser.parseNegate();
            return State.Idle;
        } else if (c == LexToken.OPEN_PAR) {
            return State.WaitNext;
        } else if (LexToken.isOperator(c)) {
            yaccParser.parseOperator(new OperatorToken(c, 0));
            return State.ReadOp;
        } else if (c == LexToken.CE_CHAR) {
            yaccParser.parseClearEntry();
            return State.Idle;
        }
        throw new ParseException(c);
    }
    private static final int PAREN_PRIO_ADJUST = 10;

    /**
     *
     * @return
     */
    private BigDecimal getOperandValue() {
        assert operandValue != null;
        return new BigDecimal(operandValue.toString());
    }

    public String getOperandDisplay() {
        return operandValue.toString();
    }

    private State doParseOperand(char c) throws ParseException {
        if (Character.isDigit(c)) {
            if (operandValue.toString().equals("0")) {
                operandValue = new StringBuffer();
            }
            operandValue.append(c);
            return State.ParseOperand;
        } else if (c == LexToken.DEC_POINT) {
            // Test if operand has already the decimal point
            if (operandValue.toString().indexOf(LexToken.DEC_POINT) != -1) {
                throw new ParseException("Invalid character");
            }
            operandValue.append(c);
            return State.ParseOperand;
        } else if (c == LexToken.SIGN_CHAR) {
            if (operandValue.charAt(0) == '-') {
                operandValue.deleteCharAt(0);
            } else {
                operandValue.insert(0, '-');
            }
            return State.ParseOperand;
        } else if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            yaccParser.parseOperand(getOperandValue());
            yaccParser.parseEnd();
            return State.Idle;
        } else if (c == LexToken.CLOSE_PAR) {
            if (parenLvl > 0) {
                yaccParser.parseOperand(getOperandValue());
                yaccParser.parseClosePar(parenLvl * PAREN_PRIO_ADJUST);
                parenLvl--;
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (LexToken.isOperator(c)) {
            yaccParser.parseOperand(getOperandValue());
            yaccParser.parseOperator(new OperatorToken(c, parenLvl * PAREN_PRIO_ADJUST));
            return State.ReadOp;
        } else if (c == LexToken.CE_CHAR) {
            operandValue = new StringBuffer("0");
            return State.ParseOperand;
        }
        throw new ParseException(c);
    }

    private State doWaitNext(char c) throws ParseException {
        if (c == LexToken.DEC_POINT) {
            operandValue.append(c);
            return State.ParseOperand;
        } else if (c == LexToken.OPEN_PAR) {
            parenLvl++;
            return State.WaitOp;
        } else if (Character.isDigit(c)) {
            operandValue = new StringBuffer();
            operandValue.append(c);
            return State.ParseOperand;
        }
        throw new ParseException(c);
    }

    private State doWaitOp(char c) throws ParseException {
        if (c == LexToken.CLOSE_PAR) {
            if (parenLvl == 0) {
                throw new ParseException(c);
            }
            yaccParser.parseClosePar(parenLvl * PAREN_PRIO_ADJUST);
            parenLvl--;
            return State.WaitOp;
        } else if (LexToken.isOperator(c)) {
            yaccParser.parseOperator(new OperatorToken(c, parenLvl * PAREN_PRIO_ADJUST));
            return State.ReadOp;
        } else if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing ')'");
            }
            yaccParser.parseEnd();
            return State.Idle;
        }
        throw new ParseException(c);
    }

    private State doReadOp(char c) throws ParseException {
        if (LexToken.isOperator(c)) {
            yaccParser.replaceOperator(new OperatorToken(c, parenLvl * PAREN_PRIO_ADJUST));
            return State.ReadOp;
        } else if (Character.isDigit(c)) {
            operandValue = new StringBuffer(new String(new char[]{c}));
            return State.ParseOperand;
        } else if (c == LexToken.OPEN_PAR) {
            parenLvl++;
            return state.WaitNext;
        } else if (c == LexToken.DEC_POINT) {
            operandValue = new StringBuffer("0.");
            return State.ParseOperand;
        }
        throw new ParseException(c);
    }
}