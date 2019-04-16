package com.research.demo.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.research.demo.ResearchDemoApp;
import com.research.demo.domain.Field;
import java.util.HashSet;
import java.util.Set;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchDemoApp.class)
public class ExpressionParserTest{

    private static final Log log = LogFactory.getLog(ExpressionParserTest.class);

    private static final String TEST_MONTH_OF_YEAR = "MONTH_OF_YEAR([A])";
    private static final String TEST_DAY_OF_MONTH = "DAY_OF_MONTH([A])";
    private static final String TEST_SUM = "SUM([A])";
    private static final String TEST_FAILED_EXPRESSION = "month_of_year([A])";
    private static final String TEST_INVALID_EXPRESSION = "MONTH_OF_YEAR([A]";
    private static final String TEST_MONTH_OF_YEAR_RESULT = "doc['field1'].date.monthOfYear";
    private static final String TEST_SUM_RESULT = "doc['field1'].sum()";
    private static final String TEST_DAY_OF_MONTH_RESULT = "doc['field1'].date.dayOfMonth";
    private static final String TEST_FIELD_NAME = "field1";
    private static final String TEST_FIELD_DISPLAYNAME = "A";

    private Set<Field> fields;

    @Before
    public void setUp() {
        Field field = new Field();
        field.setName(TEST_FIELD_NAME);
        field.setDisplayName(TEST_FIELD_DISPLAYNAME);
        this.fields = new HashSet<>();
        this.fields.add(field);
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

    @Test
    public void assertThatNoSuchFunctionTest() {
        ExpressionParser parser = new ExpressionParser(TEST_FAILED_EXPRESSION, this.fields);
        try {
            parser.build();
            String result = parser.parseToScript();
            assertThat(result).isNull();
        } catch (UnknownFunctionOrVariableException e1) {
            log.error(e1.getMessage());
        }
    }

    @Test
    public void assertThatExistInvalidExpression() {
        ExpressionParser parser = new ExpressionParser(TEST_INVALID_EXPRESSION, this.fields);
        try {
            parser.build();
            String result = parser.parseToScript();
            assertThat(result).isNull();
        } catch (IllegalArgumentException e1) {
            log.error(e1.getMessage());
        }
    }
}
