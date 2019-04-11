package com.research.demo.parser.expression;

import com.research.demo.domain.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author marcosgui
 */
public class ExpressionParser{

    private String expression;

    private Set<Field> variables;

    private ArrayList<String> output = new ArrayList<>();

    private Stack stack = new Stack<>();


    public ExpressionParser(String expression, Set<Field> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    public String build() {
        for (Field variable : variables) {
            this.expression = this.expression.replace('[' + variable.getDisplayName() + ']', variable.getName());
        }
        return this.expression.trim();
    }

    public List<String> parseToScript() {
        char[] chars = this.expression.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            char currentChar = chars[index];
            if (isOpenParentheses(currentChar)) {
                output.add(expression.substring(0, index));
            } else if (isCloseParentheses(currentChar)) {
                String pre = this.expression.substring(0, index);
                output.add(pre);
            }
        }
        return output;
    }

    private boolean isOpenParentheses(char ch) {
        return ch == '(';
    }

    private boolean isCloseParentheses(char ch) {
        return ch == ')';
    }
}
