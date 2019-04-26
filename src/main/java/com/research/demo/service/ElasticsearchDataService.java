package com.research.demo.service;

import com.research.demo.util.ElasticsearchClientHelper;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.stereotype.Service;

/**
 * ElasticsearchDataService:
 * @author marcosgui
 */
@Service
public class ElasticsearchDataService{

    private static final Log log = LogFactory.getLog(ElasticsearchDataService.class);
    private static RestHighLevelClient client;

    static {
        client = ElasticsearchClientHelper.getClient();
    }

    long updateCount = 0;

    public void updateEsDataByQuery(String esIndex) {
        String script = "ctx._source.newAge = ctx._source.age * 2";
        UpdateByQueryRequest request = new UpdateByQueryRequest(esIndex);
        request
            .setScript(new Script(ScriptType.INLINE, "painless",script, Collections.emptyMap()));
        request.setQuery(QueryBuilders.matchQuery("age", 18));
        client.updateByQueryAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>(){
            @Override
            public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                updateCount = bulkByScrollResponse.getUpdated();
                log.info("Update By Query Success! " + updateCount + " Docs have already updated!");
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Update By Query Failed!");
            }
        });
    }

    public void addFieldMappings(String esIndex) throws IOException {
        PutMappingRequest request = new PutMappingRequest(esIndex);
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("newAge");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.source(builder);
        client.indices().putMappingAsync(request, RequestOptions.DEFAULT, new ActionListener<AcknowledgedResponse>(){
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                log.info("Put Mapping Success!");
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                log.error("Put Mapping Failed!");
            }
        });
    }

    public Map<String, Object> getMappings(String esIndex) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(esIndex);
        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> allMappings = response.mappings();
        MappingMetaData indexMapping = allMappings.get(esIndex);
        return indexMapping.getSourceAsMap();
    }

    public void removeFieldByIndex(String esIndex) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(esIndex);
        request
            .setScript(
                new Script(ScriptType.INLINE, "painless", "ctx._source.remove('salary')", Collections.emptyMap()));
        client.updateByQueryAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>(){
            @Override
            public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                log.info("Remove Field Success!");
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Remove Field Failed!");
            }
        });
    }

    public void addFieldByScript() throws IOException {
        PutStoredScriptRequest request = new PutStoredScriptRequest();
        request.id("-dqqTmoBm7gfewc_Sf3R");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("script");
            {
                builder.field("lang", "painless");
                builder.field("source", "doc['newAge'].value doc['age'].value * 2");
            }
            builder.endObject();
        }
        builder.endObject();
        request.content(BytesReference.bytes(builder), XContentType.JSON);
        client.putScriptAsync(request, RequestOptions.DEFAULT, new ActionListener<AcknowledgedResponse>(){
            @Override
            public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                log.info("put stored script success!");
            }

            @Override
            public void onFailure(Exception e) {
                log.error("put stored script failed!");
            }
        });
    }
}
