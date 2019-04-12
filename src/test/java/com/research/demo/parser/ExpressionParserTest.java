package com.research.demo.parser;

import static org.assertj.core.api.Assertions.assertThat;

import com.research.demo.ResearchDemoApp;
import com.research.demo.domain.Field;
import java.util.HashSet;
import java.util.Set;
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

    private static final String TEST_PARSE_EXPRESSION = "Year([A]) + 1";

    private static final String TEST_PARSE_RESULT = "doc['field1'].date.year + 1";

    private static final String TEST_FIELD_NAME = "field1";

    private static final String TEST_FIELD_DISPLAYNAME = "A";

    private static final String TEST_FIELD_NAME_2 = "field2";

    private static final String TEST_FIELD_DISPLAYNAME_2 = "B";

    private Set<Field> fields;

    @Before
    public void setUp() throws Exception {
        Field field = new Field();
        field.setName(TEST_FIELD_NAME);
        field.setDisplayName(TEST_FIELD_DISPLAYNAME);
        Field field2 = new Field();
        field2.setName(TEST_FIELD_NAME_2);
        field2.setDisplayName(TEST_FIELD_DISPLAYNAME_2);
        this.fields = new HashSet<>();
        this.fields.add(field);
//        this.fields.add(field2);
    }

    @Test
    public void testParseStringToEsScript() throws Exception {
        ExpressionParser parser = new ExpressionParser(TEST_PARSE_EXPRESSION, this.fields);
        parser.build();
        String result = parser.parseToScript();
        assertThat(result).isEqualTo(TEST_PARSE_RESULT);
        log.info("Result is: " + result);
    }
}
