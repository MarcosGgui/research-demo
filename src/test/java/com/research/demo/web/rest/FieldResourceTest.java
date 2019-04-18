package com.research.demo.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.research.demo.ResearchDemoApp;
import com.research.demo.domain.Field;
import com.research.demo.repository.FieldRepository;
import com.research.demo.service.FieldService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResearchDemoApp.class)
public class FieldResourceTest{

    private static final String DEFAULT_NAME = "AAAA";

    private static final String DEFAULT_DISPLAY_NAME = "BBBB";

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldService fieldService;

    private Field field;

    private MockMvc restFieldMockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final FieldResource fieldResource = new FieldResource(fieldService);
        this.restFieldMockMvc = MockMvcBuilders.standaloneSetup(fieldResource).build();
    }

    @Test
    @Transactional
    public void getField() throws Exception {

        fieldRepository.saveAndFlush(field);
        restFieldMockMvc.perform(get("api/get-field/{id}", field.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(field.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME));
    }
}
