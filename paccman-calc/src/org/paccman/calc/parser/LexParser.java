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
import java.io.Writer;
import org.paccman.calc.parser.LexParser.ParseException;

/**
 *
 * @author joao
 */
public class LexParser {

    class Output {

        // Output to JavaCC parser
        Writer outputWriter;

        Output(Writer outputWriter) {
            this.outputWriter = outputWriter;
        }

        private int iter = 0;
        
        public void out(String strOut) throws IOException {
            System.out.printf("  S%1$d> %2$s", iter, strOut);
            iter++;
            outputWriter.write(strOut);
            outputWriter.flush();
        }

        public void out(char charOut) throws IOException {
            System.out.printf("  S%1$d> %2$c", iter, charOut);
            System.out.println(charOut);
            outputWriter.write(charOut);
            outputWriter.flush();
        }
    }
    Output output;

    /**
     *
     * @param outputWriter
     */
    public LexParser(Writer outputWriter) {
        output = new Output(outputWriter);
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
    char lastOperator;

    private void resetValue() {
        intValue = null;
        decValue = null;
        negative = false;
    }

    private void outValue() throws IOException {
        if (negative) {
            output.out("-");
        }
        output.out(intValue.toString());
        if (decValue != null) {
            output.out(LexToken.DEC_POINT);
            output.out(decValue.toString());
        }
        resetValue();
    }

    private enum State {

        Init, ParseInt, ParseDec, WaitOp, ReadOp, WaitNext
    }
    State state = State.Init;

    private State reset() {
        intValue = decValue = null;
        negative = false;
        return State.Init;
    }

    /**
     *
     * @param c
     * @throws org.paccman.calc.parser.LexParser.ParseException
     * @throws java.io.IOException
     */
    public void parseChar(char c) throws ParseException, IOException {
        switch (state) {
            case Init:
                state = doInit(c);
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

    private State doInit(char c) throws ParseException {
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
        }
        throw new ParseException(c);
    }

    private State doParseInt(char c) throws ParseException, IOException {
        if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            if (negative) {
                output.out("-");
            }
            output.out(intValue.toString());
            output.out(""); //TODO: replace this by EOL or anything else
            return reset();
        } else if (c == LexToken.DEC_POINT) {
            decValue = new StringBuffer();
            return State.ParseDec;
        } else if (c == LexToken.CLOSE_PAR) {
            if (parenLvl > 0) {
                outValue();
                output.out(LexToken.CLOSE_PAR);
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (LexToken.isOperator(c)) {
            outValue();
            lastOperator = c;
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
            if (negative) {
                output.out(LexToken.NEG_SIGN);
            }
            output.out(intValue.toString());
            output.out(LexToken.DEC_POINT);
            output.out(decValue.toString());
            return reset();
        } else if (c == LexToken.CLOSE_PAR) {
            if (parenLvl > 0) {
                if (negative) {
                    output.out(LexToken.NEG_SIGN);
                }
                output.out(intValue.toString());
                output.out(LexToken.DEC_POINT);
                output.out(decValue.toString());
                return State.WaitOp;
            } else {
                throw new ParseException(LexToken.CLOSE_PAR);
            }
        } else if (LexToken.isOperator(c)) {
            if (parenLvl > 0) {
                if (negative) {
                    output.out(LexToken.NEG_SIGN);
                }
                output.out(intValue.toString());
                output.out(LexToken.DEC_POINT);
                output.out(decValue.toString());
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
            if (parenLvl > 0) {
                output.out(LexToken.CLOSE_PAR);
                parenLvl--;
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (LexToken.isOperator(c)) {
            lastOperator = c;
            return State.ReadOp;
        } else if (c == LexToken.EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing ')'");
            }
            return reset();
        }
        throw new ParseException(c);
    }

    private State doReadOp(char c) throws ParseException, IOException {
        if (LexToken.isOperator(c)) {
            lastOperator = c;
            return State.ReadOp;
        } else if (Character.isDigit(c)) {
            output.out(lastOperator);
            intValue = new StringBuffer();
            intValue.append(c);
            return State.ParseInt;
        } else if (c == LexToken.DEC_POINT) {
            decValue = new StringBuffer();
            decValue.append(c);
            return State.ParseDec;
        }
        throw new ParseException(c);
    }
}