package com.main.elastic;

import com.monitor.services.ESClientService;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessLog {
    private static List<String> values = new ArrayList<>();


    public static void getProcesses(String command) throws Exception {
        while (true) {
            Process process = Runtime.getRuntime().exec(command);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processStringv2(line);
                }
            }
            Thread.sleep(2000);
        }
    }

    /*
     * COMMITS
     *
     * TIME 1:33 PM
     * LOCATION : University Central Library Park In Front Of
     * Discussion Hall
     * DATE : 17th April
     *
     * -- Parsing CPU% , MEMORY% as Double type
     * -- Kibana assigns them as default string type , should be of type double
     */
    private static void processString(String s) {

        String convertedString = s.replaceAll("\\s", "");
        Pattern pattern = Pattern.compile("(\\d+)([A-Za-z]+)(\\d+)(.*)((\\d+)(\\.)(\\d+))((\\d+)(\\.)(\\d+))((\\d{2})(\\:)(\\d{2})(\\:)(\\d{2}))");
        Matcher matcher = pattern.matcher(convertedString);
        Document object = new Document();

        while (matcher.find()) {
            object.put("userName", matcher.group(2));
            object.put("processName", matcher.group(4));
            object.put("cpuUsagePercentage", Double.parseDouble(matcher.group(5)));
            object.put("memoryUsagePercentage", Double.parseDouble(matcher.group(9)));
            object.put("VirtualSize", Long.parseLong(matcher.group(3)));
            object.put("upTime", Double.parseDouble(matcher.group(13)));

        }
        object.put("timeOfEvent", LocalDateTime.now().toString());
        ESClientService.indexDocument("testindex1", "testtype", object);
    }

    public static void initSimulation() throws Exception {
        for (int i = 0; i < 100000; i++) {
            getProcesses("ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e");
//            Thread.sleep(50);
        }
    }

    private static void processStringv2(String token) {
        String re1 = ".*?";    // Non-greedy match on filler
        String re2 = "(1)";    // Integer Number 1
        String re3 = ".*?";    // Non-greedy match on filler
        String re4 = "((?:[a-z][a-z]+))";    // Word 1
        String re5 = ".*?";    // Non-greedy match on filler
        String re6 = "(\\d+)";    // Integer Number 2
        String re7 = ".*?";    // Non-greedy match on filler
        String re8 = "((?:\\/[\\w\\.\\-]+)+)";    // Unix Path 1
        String re9 = ".*?";    // Non-greedy match on filler
        String re10 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 1
        String re11 = ".*?";    // Non-greedy match on filler
        String re12 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 2
        String re13 = ".*?";    // Non-greedy match on filler
        String re14 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 3

        Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11 + re12 + re13 + re14, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = p.matcher(token);
        if (matcher.find()) {
            String int1 = matcher.group(1);
            String word1 = matcher.group(2);
            String int2 = matcher.group(3);
            String unixpath1 = matcher.group(4);
            String float1 = matcher.group(5);
            String float2 = matcher.group(6);
            String float3 = matcher.group(7);

            Map object = new HashMap();
            object.put("userName", word1);
            object.put("VirtualSize", int2);
            object.put("processName", unixpath1);
            object.put("cpuUsagePercentage", float1);
            object.put("memoryUsagePercentage", float2);
            object.put("upTime", float3);

//            System.out.print("(" + int1.toString() + ")" + "(" + word1.toString() + ")" + "(" + int2.toString() + ")" + "(" + unixpath1.toString() + ")" + "(" + float1.toString() + ")" + "(" + float2.toString() + ")" + "(" + float3.toString() + ")" + "\n");

            object.put("timeOfEvent", LocalDateTime.now().toString());
            ESClientService.indexDocument("testindex1", "testtype", object);

        }
    }
}


