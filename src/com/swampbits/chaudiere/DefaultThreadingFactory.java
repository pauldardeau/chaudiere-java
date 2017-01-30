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
public class DefaultThreadingFactory extends ThreadingFactory {

   /**
    * Constructs a DefaultThreadingFactory instance
    */
   public DefaultThreadingFactory() {
      
   }
   
  /**
   * Create a new Thread
   * @return pointer to newly created Thread
   * @see Thread()
   */
   @Override
  public Thread createThread(String name) {
     return new Thread();
  }
  
  /**
   * Creates a new Thread to run the specified Runnable
   * @param runnable the Runnable object for the thread to run
   * @return pointer to the newly created Thread
   * @see Thread()
   * @see Runnable()
   */
   @Override
  public Thread createThread(Runnable runnable, String name) {
     return new Thread(runnable);
  }
  
  /**
   * Creates a new ThreadPool
   * @param numberThreads the number of threads to initialize in the pool dispatcher
   * @return pointer to newly created ThreadPoolDispatcher
   */
   @Override
  public ThreadPoolDispatcher createThreadPoolDispatcher(int numberThreads,
                                                         String name) {
     return new ThreadPoolDispatch();
  }
   
}
