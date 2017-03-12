package com.main.elastic;

public class Program {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        // ps -o pid=,user=,vsz=,command=,%cpu=,%mem,time -e
        String cmd = "ps -o pid=,user=,vsz=,command=,%cpu=,%mem,time -e";
//        cmd = "ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e";
        ProcessLog.getProcesses(cmd);
//        ProcessLog.initSimulation();


        //ps o pid=,user=,vsz,cmd=,%cpu=,%mem,time -e

    }

}
