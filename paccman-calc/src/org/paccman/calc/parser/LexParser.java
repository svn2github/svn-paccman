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
        public void out(String strOut) throws IOException {
            outputWriter.write(strOut);
        }
        public void out(char charOut) throws IOException {
            outputWriter.write(charOut);
        }
    }
    Output output;
    
    public LexParser(Writer outputWriter) {
        output = new Output(outputWriter);
    }

    public static class ParseException extends Exception {

        public ParseException(char c) {
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

    private enum State {

        Init, ParseInt, ParseDec, WaitOp, ReadOp, WaitNext
    }
    State state = State.Init;

    private State reset() {
        intValue = decValue = null;
        negative = false;
        return State.Init;
    }

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

    private static boolean isOperator(char c) {
        return (c == '+') || (c == '-') || (c == '*') || (c == '/');
    }
    private static final char SIGN_CHAR = '±';
    private static final char EVAL_CHAR = '=';
    private static final char DEC_POINT = '.';
    private static final char OPEN_PAR = '(';
    private static final char CLOSE_PAR = ')';
    private static final char NEG_SIGN = '-';

    private State doInit(char c) throws ParseException {
        if (Character.isDigit(c)) {
            negative = false;
            intValue = new StringBuffer();
            intValue.append(c);
            return State.ParseInt;
        } else if (c == DEC_POINT) {
            intValue = new StringBuffer("0");
            decValue = new StringBuffer();
            return State.ParseDec;
        } else if (c == OPEN_PAR) {
            return State.WaitNext;
        }
        throw new ParseException(c);
    }

    private State doParseInt(char c) throws ParseException, IOException {
        if (c == EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            if (negative) {
                output.out("-");
            }
            output.out(intValue.toString());
            output.out(""); //TODO: replace this by EOL or anything else
            return reset();
        } else if (c == DEC_POINT) {
            decValue = new StringBuffer();
            return State.ParseDec;
        } else if (c == CLOSE_PAR) {
            if (parenLvl > 0) {
                if (negative) {
                    output.out("-");
                }
                output.out(intValue.toString());
                output.out(CLOSE_PAR);
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (isOperator(c)) {
            lastOperator = c;
            return State.ReadOp;
        } else if (c == SIGN_CHAR) {
            negative = !negative;
            return State.ParseInt;
        } else if (Character.isDigit(c)) {
            intValue.append(c);
            return State.ParseInt;
        }
        throw new ParseException(c);
    }

    private State doParseDec(char c) throws ParseException, IOException {
        if (c == EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing closing parenthesis");
            }
            if (negative) {
                output.out(NEG_SIGN);
            }
            output.out(intValue.toString());
            output.out(DEC_POINT);
            output.out(decValue.toString());
            return reset();
        } else if (c == CLOSE_PAR) {
            if (parenLvl > 0) {
                if (negative) {
                    output.out(NEG_SIGN);
                }
                output.out(intValue.toString());
                output.out(DEC_POINT);
                output.out(decValue.toString());
                return State.WaitOp;
            } else {
                throw new ParseException(CLOSE_PAR);
            }
        } else if (isOperator(c)) {
            if (parenLvl > 0) {
                if (negative) {
                    output.out(NEG_SIGN);
                }
                output.out(intValue.toString());
                output.out(DEC_POINT);
                output.out(decValue.toString());
                return State.ReadOp;
            }
        } else if (c == SIGN_CHAR) {
            negative = !negative;
            return State.ParseDec;
        } else if (Character.isDigit(c)) {
            decValue.append(c);
            return State.ParseDec;
        }
        throw new ParseException(c);
    }

    private State doWaitNext(char c) throws ParseException {
        if (c == DEC_POINT) {
            negative = false;
            intValue = new StringBuffer("");
            decValue.append(c);
            return State.ParseDec;
        } else if (c == OPEN_PAR) {
            parenLvl++;
            return State.WaitOp;
        } else if (Character.isDigit(c)) {
            negative = false;
            intValue = new StringBuffer(c);
            return State.ParseInt;
        }
        throw new ParseException(c);
    }

    private State doWaitOp(char c) throws IOException, ParseException {
        if (c == CLOSE_PAR) {
            if (parenLvl > 0) {
                output.out(CLOSE_PAR);
                parenLvl--;
                return State.WaitOp;
            } else {
                throw new ParseException(c);
            }
        } else if (isOperator(c)) {
            lastOperator = c;
            return State.ReadOp;
        } else if (c == EVAL_CHAR) {
            if (parenLvl > 0) {
                throw new ParseException("Missing ')'");
            }
            return reset();
        }
        throw new ParseException(c);
    }

    private State doReadOp(char c) throws ParseException {
        if (isOperator(c)) {
            lastOperator = c;
            return State.ReadOp;
        } else if (Character.isDigit(c)) {
            negative = false;
            intValue = new StringBuffer(c);
            return State.ParseInt;
        } else if (c == DEC_POINT) {
            negative = false;
            intValue = new StringBuffer("0");
            decValue = new StringBuffer(c);
            return State.ParseDec;
        }
        throw new ParseException(c);
    }

}

