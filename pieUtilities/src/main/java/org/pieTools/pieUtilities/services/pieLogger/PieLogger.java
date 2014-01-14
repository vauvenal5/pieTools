/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pieTools.pieUtilities.services.pieLogger;

import org.apache.log4j.PropertyConfigurator;
import org.pieTools.pieUtilities.services.pieIngredientsStore.LoggerPropertiesDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class PieLogger {

	private static Logger mainLogger = LoggerFactory.getLogger(PieLogger.class);
	private static Properties properties = null;

	public static void setProperties() {

		if (properties == null) {
			final AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			context.registerShutdownHook();
			LoggerPropertiesDAO loggerPropertiesReader = context.getBean(LoggerPropertiesDAO.class);
			properties = loggerPropertiesReader.getPropertyByName("LoggerProperties_InFile");
			PropertyConfigurator.configure(properties);
		}
	}

	public static void debug(Class clazz, String message) {
		setProperties();
		if (mainLogger.isDebugEnabled()) {
			Logger logger = LoggerFactory.getLogger(clazz);
			logger.debug(message);
		}
	}

	public static void error(Class clazz, String message) {
		setProperties();
		if (mainLogger.isErrorEnabled()) {
			Logger logger = LoggerFactory.getLogger(clazz);
			logger.debug(message);
		}
	}

	public static void info(Class clazz, String message) {
		setProperties();
		if (mainLogger.isInfoEnabled()) {
			Logger logger = LoggerFactory.getLogger(clazz);
			logger.info(message);
		}
	}
}