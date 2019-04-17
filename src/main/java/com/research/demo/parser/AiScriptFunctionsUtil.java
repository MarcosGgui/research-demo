package com.research.demo.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import net.objecthunter.exp4j.function.Function;

/**
 * Define the functions that use in the elasticsearch expression/painless scripting language; To get the expression
 * after applying the function;
 *
 * @author marcosgui
 */
public class AiScriptFunctionsUtil{

    private static List<Function> functionList = new ArrayList<>();

    private static String expression;

    private static String functionVal = "value";

    private static String lang = "expression";

    static {
        Function minFun = new Function("MIN", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("MIN");
                functionVal = "min()";
                return 0;
            }
        };
        Function maxFun = new Function("MAX", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("MAX");
                functionVal = "max()";
                return 0;
            }
        };
        Function lengthFun = new Function("LENGTH", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("LENGTH");
                functionVal = "length";
                return 0;
            }
        };
        Function avgFun = new Function("AVG", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("AVG");
                functionVal = "avg()";
                return 0;
            }
        };
        Function sumFun = new Function("SUM", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("SUM");
                functionVal = "sum()";
                return 0;
            }
        };
        Function yearFun = new Function("YEAR", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("YEAR");
                functionVal = "date.year";
                return 0;
            }
        };
        Function monthOfYearFun = new Function("MONTH_OF_YEAR", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("MONTH_OF_YEAR");
                functionVal = "date.monthOfYear";
                return 0;
            }
        };
        Function dayOfMonthFun = new Function("DAY_OF_MONTH", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("DAY_OF_MONTH");
                functionVal = "date.dayOfMonth";
                return 0;
            }
        };
        Function dayOfWeekFun = new Function("DAY_OF_WEEK", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("DAY_OF_WEEK");
                functionVal = "date.dayOfWeek";
                return 0;
            }
        };
        Function fieldsAvgFun = new Function("FIELDS_AVG", 1){
            @Override
            public double apply(double... args) {
                expression = formatFunctionExpression("FIELDS_AVG");
                functionVal = "value";
                return 0;
            }
        };

        functionList.add(minFun);
        functionList.add(maxFun);
        functionList.add(lengthFun);
        functionList.add(avgFun);
        functionList.add(sumFun);
        functionList.add(yearFun);
        functionList.add(monthOfYearFun);
        functionList.add(dayOfMonthFun);
        functionList.add(dayOfWeekFun);
        functionList.add(fieldsAvgFun);
    }

    static List<Function> getFunctions(String exp) {
        expression = exp;
        return functionList;
    }

    static String getFunctionValue() {
        return functionVal;
    }

    static String getExpression() {
        return expression;
    }

    static String getLang() {
        return lang;
    }


    private static String formatFunctionExpression(String functionName) {
        List<Character> output = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        int count = 0;
        char[] chars = expression.toCharArray();
        for (Character ch : chars) {
            if (ch == '(') {
                stack.push(ch);
            } else if (!stack.empty() && stack.peek() == '(') {
                if (ch != ')') {
                    output.add(ch);
                    if (ch == '[') {
                        count++;
                    }
                } else {
                    stack.pop();
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Character ch : output) {
            builder.append(ch);
        }
        expression = expression.replace(functionName + "(" + builder.toString() + ")", builder.toString());
        switch (functionName) {
            case "FIELDS_AVG":
                expression = "(" + expression + ")/" + count;
                lang = "painless";
                break;
            default:
                break;
        }
        return expression;
    }
}
