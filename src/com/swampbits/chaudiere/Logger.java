/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 *
 * @author paul
 */
public abstract class Logger {
   
   private static Logger loggerInstance = null;

   public enum LogLevel
   {
      Critical(0), Error(1), Warning(2), Info(3), Debug(4), Verbose(5);
      private final int value;
              
      private LogLevel(int value) {
         this.value = value;
      }
      
      public int value() {
         return this.value;
      }
   }
   
   /**
    * Retrieves the current log level
    * @return the current log level value
    */
   public abstract LogLevel getLogLevel();
   
   /**
    * Sets the current log level
    * @param logLevel the new log level
    */
   public abstract void setLogLevel(LogLevel logLevel);
   
   /**
    *
    * @param logLevel
    * @param logMessage
    */
   public abstract void logMessage(LogLevel logLevel, String logMessage);
   
   /**
    *
    * @param logLevel
    * @return
    */
   public abstract boolean isLoggingLevel(LogLevel logLevel);
   
   /**
    *
    * @param occurrenceType
    * @param occurrenceName
    */
   public abstract void logOccurrence(String occurrenceType,
                              String occurrenceName);
   
   /**
    *
    * @param logger
    */
   public static void setLogger(Logger logger) {
      loggerInstance = logger;
   }
   
   /**
    *
    * @return
    */
   public static Logger getLogger() {
      return loggerInstance;
   }
   
   /**
    *
    * @param logLevel
    * @param logMessage
    */
   public static void log(LogLevel logLevel, String logMessage) {
      if (loggerInstance != null) {
         loggerInstance.logMessage(logLevel, logMessage);
      }
   }
   
   /**
    *
    * @param logMessage
    */
   public static void critical(String logMessage) {
      log(LogLevel.Critical, logMessage);
   }
   
   /**
    *
    * @param logMessage
    */
   public static void error(String logMessage) {
      log(LogLevel.Error, logMessage);
   }
   
   /**
    *
    * @param logMessage
    */
   public static void warning(String logMessage) {
      log(LogLevel.Warning, logMessage);
   }
   
   /**
    *
    * @param logMessage
    */
   public static void info(String logMessage) {
      log(LogLevel.Info, logMessage);
   }
   
   /**
    *
    * @param logMessage
    */
   public static void debug(String logMessage) {
      log(LogLevel.Debug, logMessage);
   }
   
   /**
    *
    * @param logMessage
    */
   public static void verbose(String logMessage) {
      log(LogLevel.Verbose, logMessage);
   }
   
   /**
    *
    * @param logLevel
    * @return
    */
   public static boolean isLogging(LogLevel logLevel) {
      if (loggerInstance != null) {
         return loggerInstance.isLoggingLevel(logLevel);
      }
      
      return false;
   }

   /**
    *
    * @param occurrenceType
    * @param occurrenceName
    */
   public static void countOccurrence(String occurrenceType,
                               String occurrenceName) {
      if (loggerInstance != null) {
         loggerInstance.logOccurrence(occurrenceType, occurrenceName);
      }
   }
   
}
