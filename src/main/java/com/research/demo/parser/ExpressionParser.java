package com.research.demo.parser;

import com.research.demo.domain.Field;
import java.util.Set;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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
            if (this.expression.contains(variable.getDisplayName())) {
                this.expression = this.expression
                    .replace("[" + variable.getDisplayName() + "]", "[" + variable.getName() + "]");
            }
        }
        return this.expression.trim();
    }

    String parseToScript() {
        Expression e;
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(this.expression)
            .functions(AiScriptFunctionsUtil.getFunctions(this.expression));
        for (Field v : variables) {
            if (this.expression.contains(v.getName())) {
                expressionBuilder.variable(v.getName());
            }
        }
        e = expressionBuilder.build();
        for (Field v : variables) {
            if (this.expression.contains(v.getName())) {
                e.setVariable(v.getName(), 0);
            }
        }
        e.evaluate();
        this.expression = AiScriptFunctionsUtil.getExpression();
        for (Field v : variables) {
            this.expression = this.expression
                .replace("[" + v.getName() + "]", "doc['" + v.getName() + "']." + AiScriptFunctionsUtil
                    .getFunctionValue());
        }
        return expression;
    }
}
