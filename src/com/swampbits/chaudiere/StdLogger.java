/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 * StdLogger is the default logger and logs to the console.
 * @author paul
 */
public class StdLogger extends Logger
{
   private static final String PREFIX_CRITICAL = "Critical:";
   private static final String PREFIX_ERROR    = "Error:";
   private static final String PREFIX_WARNING  = "Warning:";
   private static final String PREFIX_INFO     = "Info:";
   private static final String PREFIX_DEBUG    = "Debug:";
   private static final String PREFIX_VERBOSE  = "Verbose:";

   private LogLevel m_logLevel;
   
   /**
    * Retrieves the prefix to use for a log level
    * @param level the log level whose prefix is needed
    * @return the prefix for the log level
    */
   public String logLevelPrefix(LogLevel level) {
      switch(level) {
         case Critical:
            return PREFIX_CRITICAL;
         case Error:
            return PREFIX_ERROR;
         case Warning:
            return PREFIX_WARNING;
         case Info:
            return PREFIX_INFO;
         case Debug:
            return PREFIX_DEBUG;
         case Verbose:
            return PREFIX_VERBOSE;
      }
      
      return PREFIX_DEBUG;
   }
   
   /**
    * Default constructor
    */
   public StdLogger() {
      m_logLevel = Logger.LogLevel.Debug;
   }
   
   /**
    * Construct a StdLogger with the specified log level
    * @param logLevel the log level to set
    */
   public StdLogger(LogLevel logLevel) {
      m_logLevel = logLevel;
   }
   
   /**
    * Retrieves the current log level
    * @return the current log level value
    */
   @Override
   public LogLevel getLogLevel() {
      return m_logLevel;
   }
   
   /**
    * Sets the current log level
    * @param logLevel the new log level
    */
   @Override
   public void setLogLevel(LogLevel logLevel) {
      m_logLevel = logLevel;
   }
   
   /**
    * Logs a message if the current log level allows it
    * @param logLevel the log level for the message
    * @param logMessage the message to log
    */
   @Override
   public void logMessage(LogLevel logLevel, String logMessage) {
      if (isLoggingLevel(logLevel)) {
         System.out.println(logLevelPrefix(logLevel) + " " + logMessage);
      }
   }
   
   /**
    * Determines if the specified log level is currently being logged
    * @param logLevel the log level to check
    * @return boolean indicating if specified log level is being logged
    */
   @Override
   public boolean isLoggingLevel(LogLevel logLevel) {
      return (logLevel.value() <= m_logLevel.value());
   }
   
   /**
    * TBD
    * @param occurrenceType
    * @param occurrenceName
    */
   @Override
   public void logOccurrence(String occurrenceType,
                              String occurrenceName) {
      //TODO: add implementation for logOccurrence
   }
   
}

