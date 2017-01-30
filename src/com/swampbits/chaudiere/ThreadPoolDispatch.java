/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swampbits.chaudiere;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;


/**
 *
 * @author paul
 */
public class ThreadPoolDispatch implements ThreadPoolDispatcher, Executor {

   private boolean m_isRunning = false;
   
   /**
    * 
    * @return 
    */
   @Override
   public boolean start() {
      m_isRunning = true;
      return true;
   }
   
   /**
    *
    * @return
    */
   @Override
   public boolean stop() {
      m_isRunning = false;
      return true;
   }
   
   /**
    *
    * @param runnableRequest
    * @return
    * @see Runnable()
    */
   @Override
   public boolean addRequest(Runnable runnableRequest) {
      if (!m_isRunning || (null == runnableRequest)) {
         return false;
      }
      
      //TODO:
      return false;
   
      /*
   dispatch_async(queue, ^{
      try {
         runnableRequest.run();
      } catch (Exception e) {
         Logger.error("exception running request: " + e.getMessage());
      } catch (Throwable t) {
         Logger.error("unknown exception running request");
      }
      
      runnableRequest.notifyOnCompletion();
   });
   
   return true;
              */
   }
   
   @Override
   public void execute(Runnable runnable) {
      if (null == runnable) {
         throw new NullPointerException();
      }
      
      if (!m_isRunning) {
         throw new RejectedExecutionException();
      }
      
      addRequest(runnable);
   }

}
