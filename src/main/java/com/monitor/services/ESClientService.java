package com.monitor.services;

import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author saifasif
 */
public class ESClientService {

    private static TransportClient INSTANCE;

    private ESClientService() {
    }

    /**
     * Get connection to underlying ES
     *
     * @return TransportClient instance
     */
    public static TransportClient getTransportClient() {
        if (INSTANCE == null) {
            Settings settings = Settings.builder()
                    .put("cluster.name", "elasticsearch")
                    .put("client.transport.sniff", false).build();
            try {
                INSTANCE = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return INSTANCE;

    }

    public static <T> void indexDocument(T instance) {
        String json = new Gson().toJson(instance);
        IndexRequest indexRequest = new IndexRequest("statistics", "stats").source(json);
        IndexResponse response = getTransportClient().index(indexRequest).actionGet();

    }

}
