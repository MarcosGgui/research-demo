package com.research.demo.service;

import com.research.demo.util.ElasticsearchClientHelper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.xml.ws.Response;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchDataService{

    public List<Map> getEsData() throws IOException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();
        request.id("id");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("script");
            {
                builder.field("lang", "painless");
                builder.field("source", "Math.");
            }
        }
        builder.endObject();
        request.content(builder.bytes(), XContentType.JSON);
//        Response response = ElasticsearchClientHelper.getClient().(request, RequestOptions.DEFAULT);
        return null;
    }
}
