package com.research.demo.parser.token;

import static java.lang.Character.isAlphabetic;

import com.research.demo.parser.function.Function;
import com.research.demo.parser.function.Functions;
import com.research.demo.parser.operator.Operator;
import com.research.demo.parser.operator.Operators;
import java.util.Map;
import java.util.Set;

public class Tokenizer{

    private final char[] expression;

    private final int expressionLength;

    private final Map<String, Function> userFunctions;

    private final Map<String, Operator> userOperators;

    private final Set<String> variableNames;

    private final boolean implicitMultiplication;

    private int pos = 0;

    private Token lastToken;

    public Tokenizer(String expression,
        final Map<String, Function> userFunctions,
        final Map<String, Operator> userOperators,
        final Set<String> variableNames,
        final boolean implicitMultiplication) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
        this.implicitMultiplication = implicitMultiplication;
    }

    public Tokenizer(String expression, final Map<String, Function> userFunctions,
        final Map<String, Operator> userOperators, final Set<String> variableNames) {
        this.expression = expression.trim().toCharArray();
        this.expressionLength = this.expression.length;
        this.userFunctions = userFunctions;
        this.userOperators = userOperators;
        this.variableNames = variableNames;
        this.implicitMultiplication = true;
    }

    public boolean hasNext() {
        return this.expression.length > pos;
    }

    public Token nextToken() {
        char ch = expression[pos];
        while (Character.isWhitespace(ch)) {
            ch = expression[++pos];
        }
        if (Character.isDigit(ch) || ch == '.') {
            if (lastToken != null) {
                if (lastToken.getType() == Token.TOKEN_NUMBER) {
                    throw new IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + (int) ch + ") at [" + pos + "]");
                } else if (implicitMultiplication && (lastToken.getType() != Token.TOKEN_OPERATOR
                    && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                    && lastToken.getType() != Token.TOKEN_FUNCTION
                    && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                    // insert an implicit multiplication token
                    lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                    return lastToken;
                }
            }
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken(ch);
        } else if (isOpenParentheses(ch)) {
            if (lastToken != null && implicitMultiplication &&
                (lastToken.getType() != Token.TOKEN_OPERATOR
                    && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                    && lastToken.getType() != Token.TOKEN_FUNCTION
                    && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseParentheses(true);
        } else if (isCloseParentheses(ch)) {
            return parseParentheses(false);
        } else if (isValidOperatorToken(ch)) {
            return parseOperatorToken(ch);
        } else if (isAlphabetic(ch)) {
            if (lastToken != null && implicitMultiplication &&
                (lastToken.getType() != Token.TOKEN_OPERATOR
                    && lastToken.getType() != Token.TOKEN_PARENTHESES_OPEN
                    && lastToken.getType() != Token.TOKEN_FUNCTION
                    && lastToken.getType() != Token.TOKEN_SEPARATOR)) {
                // insert an implicit multiplication token
                lastToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                return lastToken;
            }
            return parseFunctionOrVariable(ch);
        } else if (isOpenFieldNode(ch)) {
            return parseFieldNodeToken(true);
        } else if (isCloseFieldNode(ch)) {
            return parseFieldNodeToken(true);
        }
        return null;
    }

    private boolean isOpenParentheses(char ch) {
        return ch == '(';
    }

    private boolean isCloseParentheses(char ch) {
        return ch == ')';
    }

    private boolean isOpenFieldNode(char ch) {
        return ch == '[';
    }

    private boolean isCloseFieldNode(char ch) {
        return ch == ']';
    }

    private Token parseNumberToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + len)) {
            lastToken = new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            return lastToken;
        }
        while (!isEndOfExpression(offset + len) &&
            isNumeric(expression[offset + len], expression[offset + len - 1] == 'e' ||
                expression[offset + len - 1] == 'E')) {
            len++;
            this.pos++;
        }
        // check if the e is at the end
        if (expression[offset + len - 1] == 'e' || expression[offset + len - 1] == 'E') {
            // since the e is at the end it's not part of the number and a rollback is necessary
            len--;
            pos--;
        }
        lastToken = new NumberToken(expression, offset, len);
        return lastToken;
    }

    private Token parseArgumentSeparatorToken(char ch) {
        this.pos++;
        this.lastToken = new ArgumentSeparatorToken();
        return lastToken;
    }

    private Token parseParentheses(boolean open) {
        if (open) {
            this.lastToken = new OpenParenthesesToken();
        } else {
            this.lastToken = new CloseParenthesesToken();
        }
        ++pos;
        return lastToken;
    }

    private boolean isArgumentSeparator(char ch) {
        return ch == ',';
    }

    private boolean isValidOperatorToken(char ch) {
        return Operator.isValidOperatorChar(ch);
    }

    private Token parseOperatorToken(char firstChar) {
        final int offset = this.pos;
        int len = 1;
        final StringBuilder symbol = new StringBuilder();
        Operator lastValid = null;
        symbol.append(firstChar);

        while (!isEndOfExpression(offset + len) && Operator.isValidOperatorChar(expression[offset + len])) {
            symbol.append(expression[offset + len++]);
        }

        while (symbol.length() > 0) {
            Operator op = this.getOperator(symbol.toString());
            if (op == null) {
                symbol.setLength(symbol.length() - 1);
            } else {
                lastValid = op;
                break;
            }
        }

        pos += symbol.length();
        lastToken = new OperatorToken(lastValid);
        return lastToken;
    }

    private Token parseFunctionOrVariable(char ch) {
        final int offset = this.pos;
        int testPos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        testPos = offset + len - 1;
        while (!isEndOfExpression(testPos) &&
            isVariableOrFunctionCharacter(expression[testPos])) {
            String name = new String(expression, offset, len);
            if (variableNames != null && variableNames.contains(name)) {
                lastValidLen = len;
                lastValidToken = new VariableToken(name);
            } else {
                final Function f = getFunction(name);
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new FunctionToken(f);
                }
            }
            len++;
            testPos = offset + len - 1;
        }
        pos += lastValidLen;
        lastToken = lastValidToken;
        return lastToken;
    }

    private Token parseFieldNodeToken(boolean open) {
        if (open) {
            lastToken = new FieldOpenNodeToken();
        } else {
            lastToken = new FieldCloseNodeToken();
        }
        return lastToken;
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }

    private Operator getOperator(String symbol) {
        Operator op = null;
        if (this.userOperators != null) {
            op = this.userOperators.get(symbol);
        }
        if (op == null && symbol.length() == 1) {
            int argc = 2;
            if (lastToken == null) {
                argc = 1;
            } else {
                int lastTokenType = lastToken.getType();
                if (lastTokenType == Token.TOKEN_PARENTHESES_OPEN || lastTokenType == Token.TOKEN_SEPARATOR) {
                    argc = 1;
                } else if (lastTokenType == Token.TOKEN_OPERATOR) {
                    final Operator lastOp = ((OperatorToken) lastToken).getOperator();
                    if (lastOp.getNumOperands() == 2 || (lastOp.getNumOperands() == 1 && !lastOp.isLeftAssociative())) {
                        argc = 1;
                    }
                }

            }
            op = Operators.getBuiltinOperator(symbol.charAt(0), argc);
        }
        return op;
    }

    private static boolean isNumeric(char ch, boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' ||
            (lastCharE && (ch == '-' || ch == '+'));
    }

    private static boolean isAlphabetic(int codePoint) {
        return Character.isLetter(codePoint);
    }

    private static boolean isVariableOrFunctionCharacter(int codePoint) {
        return isAlphabetic(codePoint) ||
            Character.isDigit(codePoint) ||
            codePoint == '_' ||
            codePoint == '.';
    }

    private Function getFunction(String name) {
        Function f = null;
        if (this.userFunctions != null) {
            f = this.userFunctions.get(name);
        }
        if (f == null) {
            f = Functions.getBuiltinFunction(name);
        }
        return f;
    }


}
