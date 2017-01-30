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
public interface ThreadPoolDispatcher {

   /**
    * 
    * @return 
    */
   boolean start();
   
   /**
    *
    * @return
    */
   boolean stop();
   
   /**
    *
    * @param runnableRequest
    * @return
    * @see Runnable()
    */
   boolean addRequest(Runnable runnableRequest);
}
