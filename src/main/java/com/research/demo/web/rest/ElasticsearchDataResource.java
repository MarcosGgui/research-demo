package com.research.demo.web.rest;

import com.research.demo.service.ElasticsearchDataService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ElasticsearchDataResource: CURD manage o
 *
 * @author marcosgui
 */
@RestController
@RequestMapping("/api")
public class ElasticsearchDataResource{

    private final ElasticsearchDataService esDataService;

    public ElasticsearchDataResource(ElasticsearchDataService esDataService) {
        this.esDataService = esDataService;
    }

    @PostMapping("/update-by-query/add-field/{esIndex}")
    public void createField(@PathVariable("esIndex") String esIndex) throws IOException {
        esDataService.addFieldMappings(esIndex);
        esDataService.updateEsDataByQuery(esIndex);
    }

    @PostMapping("/update-by-query/delete/{esIndex}")
    public void deleteField(@PathVariable("esIndex") String esIndex) {
        esDataService.removeFieldByIndex(esIndex);
    }

    @PostMapping("/script/query-script/{esIndex}")
    public List<Map> createFieldByPutStoredScript(@PathVariable("esIndex") String esIndex,
        @RequestParam("script") String script, @RequestParam("fieldName") String fieldName) throws IOException {
//        esDataService.addFieldByScript();
        return esDataService.searchByScript(esIndex, fieldName, script);
    }
}
