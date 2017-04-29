package com.monitor.services;

import com.monitor.beans.OSType;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author saifasif
 */
public class CommandService {

    private OSType osType;
    private ProcessBuilder processBuilder = new ProcessBuilder();

    public CommandService(OSType osType) {
        this.osType = osType;

    }

    public OutputStream execute(String... cmds) throws InterruptedException, IOException {
        int resultCode = -1;
        ProcessBuilder processBuilder = new ProcessBuilder().command(cmds);
        Process p = processBuilder.start();
        resultCode = p.waitFor();
        System.out.println(resultCode);
        return p.getOutputStream();
    }

}
