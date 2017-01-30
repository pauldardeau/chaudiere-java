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
public abstract class ThreadingFactory {
   
   private static ThreadingFactory threadingFactoryInstance = null;

   /**
    * Retrieves the class singleton instance
    * @return the class singleton instance
    */
   public static ThreadingFactory getThreadingFactory() {
      return threadingFactoryInstance;
   }
   
   /**
    * Sets the specified ThreadingFactory instance to be the class singleton
    * @param threadingFactory the instance to use for the new class singleton
    */
   public static void setThreadingFactory(ThreadingFactory threadingFactory) {
      threadingFactoryInstance = threadingFactory;
   }

   /**
    * Create a new Thread
    * @param name
    * @return pointer to newly created Thread
    * @see Thread()
    */
   public abstract Thread createThread(String name);
   
   /**
    * Creates a new Thread to run the specified Runnable
    * @param runnable the Runnable object for the thread to run
    * @param name
    * @return pointer to the newly created Thread
    * @see Thread()
    * @see Runnable()
    */
   public abstract Thread createThread(Runnable runnable, String name);
   
   /**
    * Creates a new ThreadPoolDispatcher
    * @param numberThreads the number of threads to initialize in the pool dispatcher
    * @param name
    * @return pointer to newly created ThreadPoolDispatcher
    */
   public abstract ThreadPoolDispatcher createThreadPoolDispatcher(int numberThreads,
            String name);
   

}
