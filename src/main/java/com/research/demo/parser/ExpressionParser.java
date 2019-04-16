package com.research.demo.parser;

import com.research.demo.domain.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

/**
 * Parse the customize script
 *
 * @author marcosgui
 */
public class ExpressionParser{

    private static String functionValue = "value";
    private String expression;
    private Set<Field> variables;

    ExpressionParser(String expression, Set<Field> variables) {
        this.expression = expression;
        this.variables = variables;
    }

    public String build() {
        for (Field variable : variables) {
            this.expression = this.expression.replace('[' + variable.getDisplayName() + ']', variable.getName());
        }
        return this.expression.trim();
    }

    String parseToScript() {
        Expression e;
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(this.expression)
            .function(new Function("AVG", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("AVG");
                    functionValue = "avg()";
                    return 0;
                }
            })
            .function(new Function("MIN", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("MIN");
                    functionValue = "min()";
                    return 0;
                }
            })
            .function(new Function("MAX", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("MAX");
                    functionValue = "max()";
                    return 0;
                }
            })
            .function(new Function("SUM", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("SUM");
                    functionValue = "sum()";
                    return 0;
                }
            }).function(new Function("YEAR", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("YEAR");
                    functionValue = "date.year";
                    return 0;
                }
            })
            .function(new Function("MONTH_OF_YEAR", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("MONTH_OF_YEAR");
                    functionValue = "date.monthOfYear";
                    return 0;
                }
            })
            .function(new Function("DAY_OF_MONTH"){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("DAY_OF_MONTH");
                    functionValue = "date.dayOfMonth";
                    return 0;
                }
            })
            .function(new Function("DAY_OF_WEEK", 1){
                @Override
                public double apply(double... args) {
                    expression = formatFunctionExpression("DAY_OF_WEEK");
                    functionValue = "date.dayOfWeek";
                    return 0;
                }
            });
        for (Field v : variables) {
            expressionBuilder.variable(v.getName());
        }
        e = expressionBuilder.build();

        for (Field v : variables) {
            e.setVariable(v.getName(), 0);
            e.evaluate();
            this.expression = this.expression.replace(v.getName(), "doc['" + v.getName() + "']." + functionValue);
        }
        return expression;
    }

    /**
     * format the expression according to the function definition
     *
     * @param functionName The name of the function
     * @return The expression String
     */
    private String formatFunctionExpression(String functionName) {
        List<Character> output = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        char[] chars = this.expression.toCharArray();
        for (Character ch : chars) {
            if (ch == '(') {
                stack.push(ch);
            } else if (!stack.empty() && stack.peek() == '(') {
                if (ch != ')') {
                    output.add(ch);
                } else {
                    stack.pop();
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Character ch : output) {
            builder.append(ch);
        }
        return this.expression.replace(functionName + "(" + builder.toString() + ")", builder.toString());
    }
}
