package com.test;

import com.google.gson.Gson;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saifasif
 */
public class ConnectionTest {

    @Test
    public void test() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", false).build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        Map map = new HashMap();
        map.put("Time", DateTime.now().toDateTimeISO().toString());
        map.put("name", "blah");

        IndexRequest indexRequest = new IndexRequest("testindex1","testtype", "1");
        indexRequest.source(new Gson().toJson(map));
        IndexResponse response = client.index(indexRequest).actionGet();
        System.out.println(response.status().getStatus());
    }

}
