package com.research.demo.parser.operator;

/**
 * Define the allowed operators and precedence of the operator
 *
 * @author marcosgui
 * @date 2019-04-11
 */
public abstract class Operator{

    public static final int PRECEDENCE_ADDITION = 500;

    public static final int PRECEDENCE_SUBTRANCTION = PRECEDENCE_ADDITION;

    public static final int PRECEDENCE_MULTIPLICATION = 1000;

    public static final int PRECEDENCE_DIVISION = PRECEDENCE_MULTIPLICATION;

    public static final int PRECEDENCE_MODULO = PRECEDENCE_DIVISION;

    public static final int PRECEDENCE_POWER = 10000;

    public static final int PRECEDENCE_UNARY_MINUS = 5000;

    public static final int PRECEDENCE_UNARY_PLUS = PRECEDENCE_UNARY_MINUS;

    /**
     * Valid characters as operators
     */
    public static final char[] VALID_OPERATORS_CHARS = {'+', '-', '*', '/'};

    protected final boolean leftAssociative;
    protected final int precedence;
    protected final String symbol;
    protected final int numOperands;

    /**
     * @param symbol The symbol of the operator
     * @param numberOfOperands The number of operands the operator takes(1 or 2)
     * @param leftAssociative Set to true if the operator is left associative, false is right associative
     * @param precedence The precedence value of the operator
     */
    public Operator(String symbol, int numberOfOperands, boolean leftAssociative, int precedence) {

        super();
        this.numOperands = numberOfOperands;
        this.leftAssociative = leftAssociative;
        this.symbol = symbol;
        this.precedence = precedence;

    }

    /**
     * Check whether the character is valid or not
     *
     * @param ch The char to check
     * @return true if the char is valid
     */
    public static boolean isValidOperatorChar(char ch) {
        for (char valid : VALID_OPERATORS_CHARS) {
            if (valid == ch) {
                return true;
            }
        }
        return false;
    }

    public boolean isLeftAssociative() {
        return leftAssociative;
    }

    public abstract double apply(double... args);

    public int getPrecedence() {
        return precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNumOperands() {
        return numOperands;
    }
}
