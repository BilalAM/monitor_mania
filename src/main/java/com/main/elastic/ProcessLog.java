package com.main.elastic;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessLog {
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

        String convertedString = s.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("(\\d+)([A-Za-z]+)(\\d+)(.*)((\\d+)(\\.)(\\d+))((\\d+)(\\.)(\\d+))((\\d{2})(\\:)(\\d{2})(\\:)(\\d{2}))");
        Matcher matcher = pattern.matcher(convertedString);
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
            getProcesses("ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e");
            System.out.println("hello");

            Thread.sleep(50);
        }
    }

}
