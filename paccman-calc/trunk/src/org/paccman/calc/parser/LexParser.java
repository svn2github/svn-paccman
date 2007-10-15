/*
 * LexParser.java
 *
 * Created on 11 sept. 2007, 06:17:28
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.paccman.calc.parser;

import java.io.IOException;
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

        private static final long serialVersionUID = 1L;

        private ParseException(char c) {
            super("Unexpected char: " + Character.toString(c));
        }

        private ParseException(String msg) {
            super(msg);
        }
    }
    // Parser context data
    StringBuffer intValue;
    StringBuffer decValue;
    boolean negative;
    int parenLvl = 0;

    enum State {

        Init, Idle, ParseInt, ParseDec, WaitOp, ReadOp, WaitNext
    }
    State state;

    /**
     *
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * @throws java.io.IOException
     */
    public void reset() throws ParseException, IOException {
        resetValue();
        state = State.Idle;
        parseChar('0');
    }

    private void resetValue() {
        intValue = new StringBuffer();
        decValue = new StringBuffer();
        negative = false;
    }

    /**
     *
     * @param c
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * @throws java.io.IOException
     */
    public void parseChar(char c) throws ParseException, IOException {
        switch (state) {
            case Idle:
                state = doIdle(c);
                break;
            case ParseInt:
                state = doParseInt(c);
                break;
            case ParseDec:
                state = doParseDec(c);
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
            negative = false;
            intValue = new StringBuffer();
            intValue.append(c);
            return State.ParseInt;
        } else if (c == LexToken.DEC_POINT) {
            decValue = new StringBuffer();
            return State.ParseDec;
        } else if (c == LexToken.OPEN_PAR) {
            return State.WaitNext;
        } else if (LexToken.isOperator(c)) {
            //:TODO:
            return State.ReadOp;
        }
        throw new ParseException(c);
    }

    /**
     *
     * @return
     */
    public BigDecimal getLastOperand() {
        StringBuffer sb = new StringBuffer();
        if (negative) {
            sb.append("-");
        }
        sb.append(intValue);
        sb.append(LexToken.DEC_POINT);
        sb.append(decValue);
        return new BigDecimal(sb.toString());
    }
    private static final int PAREN_PRIO_ADJUST = 10;

    private State doParseInt(char c) throws ParseException, IOException {
        if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            yaccParser.parseOperand(getLastOperand());
            yaccParser.parseEnd();
            return State.Idle;
        } else if (c == LexToken.DEC_POINT) {
            decValue = new StringBuffer();
            return State.ParseDec;
        } else if (c == LexToken.CLOSE_PAR) {
            if (parenLvl > 0) {
                yaccParser.parseOperand(getLastOperand());
                yaccParser.parseClosePar(parenLvl * PAREN_PRIO_ADJUST);
                parenLvl--;
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (LexToken.isOperator(c)) {
            yaccParser.parseOperand(getLastOperand());
            yaccParser.parseOperator(new OperatorToken(c, parenLvl * PAREN_PRIO_ADJUST));
            return State.ReadOp;
        } else if (c == LexToken.SIGN_CHAR) {
            negative = !negative;
            return State.ParseInt;
        } else if (Character.isDigit(c)) {
            intValue.append(c);
            return State.ParseInt;
        }
        throw new ParseException(c);
    }

    private State doParseDec(char c) throws ParseException, IOException {
        if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            return State.Idle;
        } else if (c == LexToken.CLOSE_PAR) {
            if (parenLvl > 0) {
                return State.WaitOp;
            } else {
                throw new ParseException(LexToken.CLOSE_PAR);
            }
        } else if (LexToken.isOperator(c)) {
            if (parenLvl > 0) {
                return State.ReadOp;
            }
        } else if (c == LexToken.SIGN_CHAR) {
            negative = !negative;
            return State.ParseDec;
        } else if (Character.isDigit(c)) {
            decValue.append(c);
            return State.ParseDec;
        }
        throw new ParseException(c);
    }

    private State doWaitNext(char c) throws ParseException {
        if (c == LexToken.DEC_POINT) {
            decValue.append(c);
            return State.ParseDec;
        } else if (c == LexToken.OPEN_PAR) {
            parenLvl++;
            return State.WaitOp;
        } else if (Character.isDigit(c)) {
            negative = false;
            intValue = new StringBuffer();
            intValue.append(c);
            return State.ParseInt;
        }
        throw new ParseException(c);
    }

    private State doWaitOp(char c) throws IOException, ParseException {
        if (c == LexToken.CLOSE_PAR) {
            if (parenLvl == 0) {
                throw new ParseException(c);
            }
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

    private State doReadOp(char c) throws ParseException, IOException {
        if (LexToken.isOperator(c)) {
            yaccParser.replaceOperator(new OperatorToken(c, parenLvl * PAREN_PRIO_ADJUST));
            return State.ReadOp;
        } else if (Character.isDigit(c)) {
            resetValue();
            intValue.append(c);
            return State.ParseInt;
        } else if (c == LexToken.DEC_POINT) {
            decValue = new StringBuffer();
            decValue.append(c);
            return State.ParseDec;
        } else if (c == LexToken.OPEN_PAR) {
            parenLvl++;
            return state.WaitNext;
        }
        throw new ParseException(c);
    }
}