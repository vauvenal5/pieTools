package org.pieTools.pieUtilities.pieLoggerTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pieTools.pieUtilities.services.pieIngredientsStore.LoggerPropertiesDAO;
import org.pieTools.pieUtilities.services.pieLogger.PieLogger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.IOUtils;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Richard on 09.01.14.
 */
public class PieLoggerTest {

    private final String logFilePath = "testLog/log.out";

    @Before
    public void startUp() {
        String propName = "LoggerProperties_InFile";
        final AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.registerShutdownHook();
        LoggerPropertiesDAO loggerPropertiesDAO = context.getBean(LoggerPropertiesDAO.class);

        Properties props = new Properties();

        props.setProperty("log4j.rootLogger", "DEBUG, FILE");
        props.setProperty("log4j.appender.FILE", "org.apache.log4j.FileAppender");
        props.setProperty("log4j.appender.FILE.File", logFilePath);
        props.setProperty("log4j.appender.FILE.ImmediateFlush", "true");
        props.setProperty("log4j.appender.FILE.Threshold", "debug");
        props.setProperty("log4j.appender.FILE.Append", "false");
        props.setProperty("log4j.appender.FILE.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.FILE.layout.conversionPattern", "%m%n");

        loggerPropertiesDAO.setNewProperty(props, propName);
    }

    @After
    public void cleanUp() {

    }

    @Test
    public void test_pieLogger()
    {
        final String logMessage = "This is a Test log message " + DateFormat.getDateInstance().format(new Date());
        PieLogger.debug(this.getClass(), logMessage);

        FileInputStream inputStream = null;
        String everything = "";
        try {
            inputStream = new FileInputStream(logFilePath);
            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            for(String line = br.readLine(); line != null; line = br.readLine())
                out.append(line);
            br.close();
            everything = out.toString();

        } catch (FileNotFoundException e) {
            Assert.fail();
        }
        catch (IOException e) {
            Assert.fail();
        }

        Assert.assertTrue(everything.contains(logMessage));
    }

}