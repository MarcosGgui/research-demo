package com.research.demo.parser.expression;

import com.research.demo.parser.function.Functions;
import com.research.demo.parser.token.Token;
import com.research.demo.parser.token.VariableToken;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author MarcosGui
 */
public class Expression{

    private final Token[] tokens;

    private final Map<String, Double> variables;

    private final Set<String> userFunctionNames;


    public Expression(final Expression existing) {
        this.tokens = Arrays.copyOf(existing.tokens, existing.tokens.length);
        this.variables = new HashMap<String, Double>();
        this.variables.putAll(existing.variables);
        this.userFunctionNames = new HashSet<String>(existing.userFunctionNames);
    }

    public Expression(final Token[] tokens) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = Collections.<String>emptySet();
    }


    Expression(final Token[] tokens, Set<String> userFunctionNames) {
        this.tokens = tokens;
        this.variables = createDefaultVariables();
        this.userFunctionNames = userFunctionNames;
    }

    private static Map<String, Double> createDefaultVariables() {
        final Map<String, Double> vars = new HashMap<String, Double>(4);
        vars.put("pi", Math.PI);
        vars.put("π", Math.PI);
        vars.put("φ", 1.61803398874d);
        vars.put("e", Math.E);
        return vars;
    }

    public Expression setVariable(final String name, final double value) {
        this.checkVariableName(name);
        this.variables.put(name, Double.valueOf(value));
        return this;
    }

    private void checkVariableName(String name) {
        if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
            throw new IllegalArgumentException(
                "The variable name '" + name + "' is invalid. Since there exists a function with the same name");
        }
    }

    public Expression setVariables(Map<String, Double> variables) {
        for (Map.Entry<String, Double> v : variables.entrySet()) {
            this.setVariable(v.getKey(), v.getValue());
        }
        return this;
    }

    public Set<String> getVariableNames() {
        final Set<String> variables = new HashSet<String>();
        for (final Token t : tokens) {
            if (t.getType() == Token.TOKEN_VARIABLE) {
                variables.add(((VariableToken) t).getName());
            }
        }
        return variables;
    }

    public Object evaluate() {
        return null;
    }
}
