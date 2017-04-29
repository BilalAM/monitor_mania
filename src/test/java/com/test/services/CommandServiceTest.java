package com.test.services;

import com.monitor.pojos.OSType;
import com.monitor.services.CommandService;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author saifasif
 */
public class CommandServiceTest {

    @Test
    public void test() throws IOException, InterruptedException {
        CommandService commandService = new CommandService(OSType.MAC_OSX);
        OutputStream outputStream = commandService.execute("pwd");
        String string = "";
        outputStream.write(string.getBytes(Charset.forName("UTF-8")));
        System.out.println(string);
    }

}
