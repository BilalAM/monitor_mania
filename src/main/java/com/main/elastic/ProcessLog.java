package com.main.elastic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class ProcessLog {
	private static String convertedString;
	private static Matcher matcher;
	private static MongoClient myClient = new MongoClient("localhost:27017");
	private static MongoDatabase database = myClient.getDatabase("MonitorSet");
	private static MongoCollection<Document> collection = database.getCollection("testCollection");

	// TESTING LIST
	private static List<String> values = new ArrayList<>();

	public static void getProcesses(String command) throws Exception {		
		Process process = Runtime.getRuntime().exec(command);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				processString(line);
			}
		}
	}

	private static void processString(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");

		convertedString = s.replaceAll("\\s", "");
		Pattern pattern = Pattern.compile("((\\d+)(\\.)(\\d+))((.*))");
		matcher = pattern.matcher(convertedString);
		Document object = new Document();

		while (matcher.find()) {
			object.put("USER NAME", matcher.group(2));
			object.put("PROCESS NAME", matcher.group(4));
			object.put("CPU USAGE %", matcher.group(5));
			object.put("MEMORY USAGE %", matcher.group(9));
			object.put("VIRTUAL SIZE", matcher.group(3));
			object.put("UP TIME", matcher.group(13));

		}
		object.put("DATE-TIME", LocalDateTime.now().format(formatter));
		collection.insertOne(object);
	}

	public static void initSimulation() throws Exception {
		for (int i = 0; i < 1; i++) {
			getProcesses("s o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e");
			System.out.println("hello");

			Thread.sleep(50);
		}
	}

}
