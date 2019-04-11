package com.research.demo;

import com.research.demo.domain.Field;
import com.research.demo.parser.expression.ExpressionParser;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestDemo{

    @Test
    public void test1(){
        Field f = new Field();
        f.setDisplayName("f1");
        f.setName("field1");
        Set<Field>  s = new HashSet<>();
        s.add(f);
        String e = "SUM([f1])";
        ExpressionParser parser = new ExpressionParser(e, s);
        parser.build();
        List list  = parser.parseToScript();
        System.out.println(list);
    }
}
