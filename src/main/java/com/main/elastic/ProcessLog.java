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
	private static List<String> values2 = new ArrayList<>();

	public static void getProcesses(String command) throws Exception {		
		Process process = Runtime.getRuntime().exec(command);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				processString2(line);
				//values.add(line.replaceAll("\\s", ""));
			}
			for(String s : values2){
				System.out.println(s);
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
			object.put("USAGE", matcher.group(1));
			object.put("NAME", matcher.group(5));
		}
		object.put("DATE-TIME", LocalDateTime.now().format(formatter));
		collection.insertOne(object);
	}
	private static void processString2(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");

		convertedString = s.replaceAll("\\s", "");
		Pattern pattern = Pattern.compile("(\\d+)([A-Za-z]+)(\\d+)(.*)((\\d+)(\\.)(\\d+))((\\d+)(\\.)(\\d+))((\\d{2})(\\:)(\\d{2})(\\:)(\\d{2}))");
		matcher = pattern.matcher(convertedString);
		Document object = new Document();
		
		//userName group(2)
		//size in kilo (group 3)
		//processName group(4)
		//cpuUsage group(5)
		//mem usage group(9)
		while (matcher.find()) {
			values2.add(matcher.group(13));
		}
		//collection.insertOne(object);
	}

	public static void initSimulation() throws Exception {
		for (int i = 0; i < 1000; i++) {
			getProcesses("ps -eo %cpu,cmd");
			System.out.println("hello");

			Thread.sleep(50);
		}
	}

}
