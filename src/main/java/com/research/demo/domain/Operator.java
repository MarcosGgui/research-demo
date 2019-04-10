package com.research.demo.domain;

public class Operator{
    private static final char[] VALID_OPERATORS_CHARS = {'+', '-', '*', '/'};

    private static final int OPERATOR_PLUS = 100;

    private static final int OPERATOR_MINUS = 200;

    private static final int OPERATOR_DIVISION = 300;

    private static final int OPERATOR_MULTIPLICATION = 400;

    private final int value;

    protected String symbol;

    public Operator(String symbol ) {

        value = 0;
    }

    public static boolean isValidOperatorChar(char ch) {
        for(char valid: VALID_OPERATORS_CHARS) {
            if( valid == ch) {
                return true;
            }
        }
        return false;
    }
}
