package com.research.demo.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticsearchClientHelper{

    private static RestHighLevelClient client;

    static {
        client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http")
            )
        );
    }

    public static RestHighLevelClient getClient() {
        return client;
    }
}
