package com.research.demo.parser;

import com.research.demo.domain.Field;
import java.util.Set;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author marcosgui
 */
public class ExpressionParser{

    private static final Log log = LogFactory.getLog(Expression.class);
    public static String functionValue = "value";
    private String expression;
    private Set<Field> variables;

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

    String parseToScript() {
        Expression e;
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(this.expression)
            .function(new Function("SUM", 1){
                @Override
                public double apply(double... args) {
                    log.debug("Arguments in Function" + args[0]);
                    //TODO do something to define the function "sum"
                    return 0;
                }
            }).function(new Function("Year", 1){
                @Override
                public double apply(double... args) {
                    log.info(args);
                    functionValue = "date.year";
                    return 0;
                }
            });
        for (Field v : variables) {
            expressionBuilder.variable(v.getName());
        }
        e = expressionBuilder.build();

        for (Field v : variables) {
            e.setVariable(v.getName(), 0);
            this.expression = this.expression.replace(v.getName(), "doc['" + v.getName() + "']." + functionValue);

        }

        log.info("Calculate Result: " + e.evaluate());
        log.info("Expression: " + expression);
        return expression;
    }
}
