package com.main.two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author saifasif
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
//        Process p = Runtime.getRuntime().exec("ps -ef");
//        ProcessBuilder processBuilder = new ProcessBuilder("ps", "e").redirectOutput(new File("tmp.txt"));
//        Process p = processBuilder.start();

        // get list of processes quoted
        // ps aux | awk '{ for(i=1;i<=NF;i++) {if ( i >= 11 ) printf "\""$i"\""" "}; printf "\n" }'

        Process process = Runtime.getRuntime().exec("ps aux");
        PrintWriter printWriter = new PrintWriter("tmp.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                printWriter.write(line + "\n");
            }
        }
    }
}
