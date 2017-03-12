package com.main.elastic;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.bson.Document;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class Program {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
/*
		MongoClient myClient = new MongoClient("localhost:27017");
		MongoDatabase database = myClient.getDatabase("MonitorSet");
		MongoCollection<Document> collection = database.getCollection("testCollection");
	*/	
		ProcessLog.getProcesses("ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e");
		//ProcessLog.initSimulation();
		
		
		//ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e

		
		
		
		/*
		/*
		Client client = TransportClient.builder().build()
				   .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
	
	
		
		
	Settings settings = Settings.settingsBuilder().put("cluster.name","elasticsearch").build();
	
	TransportClient transportClient = new TransportClient.Builder().settings(settings).build();
		 transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
		 System.out.println(transportClient.connectedNodes().get(0));
		 
		 
		
		 
	/*	 GetResponse response = transportClient.prepareGet().execute().actionGet();
		 System.out.println(response.getId());
		 System.out.println(response.getVersion());
		 System.out.println(response.getType());*/
	
	}

}
