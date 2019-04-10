package com.research.demo.domain;

public class Operators{

    private static final int INDEX_PLUS = 0;

    private static final int INDEX_MINUS = 1;

    private static final int INDEX_MULTIPLICATION = 2;

    private static final int INDEX_DIVISION = 3;

    private static final Operator[] BUILT_OPERATORS = new Operator[4];

    static {
        BUILT_OPERATORS[INDEX_PLUS] = new Operator("+");
        BUILT_OPERATORS[INDEX_MINUS] = new Operator("-");
        BUILT_OPERATORS[INDEX_MULTIPLICATION] = new Operator("*");
        BUILT_OPERATORS[INDEX_DIVISION] = new Operator("/");
    }

    public static Operator getBuiltOperatorInstance(char symbol) {
        switch (symbol) {
            case '+':
                return BUILT_OPERATORS[INDEX_PLUS];
            case '-':
                return BUILT_OPERATORS[INDEX_MINUS];
            case '*':
                return BUILT_OPERATORS[INDEX_MULTIPLICATION];
            case '/':
                return BUILT_OPERATORS[INDEX_DIVISION];
            default:
                return null;
        }
    }

}
