package com.research.demo.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.research.demo.ResearchDemoApp;
import com.research.demo.domain.Field;
import java.util.HashSet;
import java.util.Set;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchDemoApp.class)
public class ExpressionParserTest{

    private static final String TEST_MONTH_OF_YEAR = "MONTH_OF_YEAR([A])";
    private static final String TEST_DAY_OF_MONTH = "DAY_OF_MONTH([A])";
    private static final String TEST_SUM = "SUM([A])";
    private static final String TEST_FIELDS_AVG_EXPRESSION = "FIELDS_AVG([A] + [B])";
    private static final String TEST_FAILED_EXPRESSION = "month_of_year([A])";
    private static final String TEST_INVALID_EXPRESSION = "MONTH_OF_YEAR([A]";
    private static final String TEST_MONTH_OF_YEAR_RESULT = "doc['field1'].date.monthOfYear";
    private static final String TEST_SUM_RESULT = "doc['field1'].sum()";
    private static final String TEST_DAY_OF_MONTH_RESULT = "doc['field1'].date.dayOfMonth";
    private static final String TEST_FIELD_NAME = "field1";
    private static final String TEST_FIELD_NAME_2 = "field2";
    private static final String TEST_FIELD_DISPLAYNAME_A = "A";
    private static final String TEST_FIELD_DISPLAYNAME_B = "B";
    private static final String TEST_FIELDS_AVG_RESULT = "(doc['field1'].value + doc['field2'].value)/2";

    private Set<Field> fields;

    @Before
    public void setUp() {
        this.fields = new HashSet<>();
        Field field = new Field();
        field.setName(TEST_FIELD_NAME);
        field.setDisplayName(TEST_FIELD_DISPLAYNAME_A);
        this.fields.add(field);
        Field field2 = new Field();
        field2.setName(TEST_FIELD_NAME_2);
        field2.setDisplayName(TEST_FIELD_DISPLAYNAME_B);
        this.fields.add(field2);

    }

    @Test
    public void testMonthOfYear() {
        ExpressionParser parser = new ExpressionParser(TEST_MONTH_OF_YEAR, this.fields);
        parser.build();
        assertThat(parser.parseToScript()).isEqualTo(TEST_MONTH_OF_YEAR_RESULT);
    }

    @Test
    public void testDayOfMonth() {
        ExpressionParser parser = new ExpressionParser(TEST_DAY_OF_MONTH, this.fields);
        parser.build();
        assertThat(parser.parseToScript()).isEqualTo(TEST_DAY_OF_MONTH_RESULT);
    }

    @Test
    public void testSumFunction() {
        ExpressionParser parser = new ExpressionParser(TEST_SUM, this.fields);
        parser.build();
        assertThat(parser.parseToScript()).isEqualTo(TEST_SUM_RESULT);
    }

    @Test(expected = UnknownFunctionOrVariableException.class)
    public void testUnknownFunctionOrVariable() throws UnknownFunctionOrVariableException {
        ExpressionParser parser = new ExpressionParser(TEST_FAILED_EXPRESSION, this.fields);
        parser.build();
        parser.parseToScript();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpression() throws IllegalArgumentException {
        ExpressionParser parser = new ExpressionParser(TEST_INVALID_EXPRESSION, this.fields);
        parser.build();
        parser.parseToScript();
    }

    /**
     * Test assertThatThrownBy():
     *
     * @throws IllegalArgumentException Exception
     */
    @Test
    public void testException() throws IllegalArgumentException {
        ExpressionParser parser = new ExpressionParser(TEST_INVALID_EXPRESSION, this.fields);
        assertThatThrownBy(() -> {
            parser.build();
            parser.parseToScript();
        }).hasMessage("Mismatched parentheses detected. Please check the expression");
    }

    @Test
    public void testFieldsAverage() {
        ExpressionParser parser = new ExpressionParser(TEST_FIELDS_AVG_EXPRESSION, this.fields);
        parser.build();
        assertThat(parser.parseToScript()).isEqualTo(TEST_FIELDS_AVG_RESULT);
    }

    @Test
    public void testMinFunction() {
        ExpressionParser parser = new ExpressionParser("MIN([A])", this.fields);
        parser.build();
        assertThat(parser.parseToScript()).isEqualTo("doc['field1'].min()");
    }
}
