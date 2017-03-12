package com.main.elastic;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

import org.bson.Document;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class Program {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ProcessLog.getProcesses("ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e");
		//ProcessLog.initSimulation();
		
		
		//ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e
	
	}

}
