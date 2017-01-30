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
//public class RequstHandler implements Runnable {
public class RequestHandler {
   
   private final Socket m_socket;
   private final SocketRequest m_socketRequest;
   private boolean m_isThreadPooling;
   private String m_threadWorker;

   
   /**
    * Constructs a RequestHandler using a SocketRequest for use by a KernelEventServer
    * @param socketRequest the SocketRequest for processing using KernelEventServer
    * @see SocketRequest()
    */
   public RequestHandler(SocketRequest socketRequest) {
      m_socket = null;
      m_socketRequest = socketRequest;
      m_isThreadPooling = false;
      m_threadWorker = "";
   }
   
   /**
    * Constructs a RequestHandler using a Socket
    * @param socket the Socket for handling the request
    * @see Socket()
    */
   public RequestHandler(Socket socket) {
      m_socket = socket;
      m_socketRequest = null;
      m_isThreadPooling = false;
      m_threadWorker = "";
   }
   
   /**
    * Sets boolean indicating whether request is being run on thread pool
    * @param isThreadPooling boolean indicating if request is being run on thread pool
    */
   public void setThreadPooling(boolean isThreadPooling) {
      m_isThreadPooling = isThreadPooling;
   }
   
   /**
    *
    * @return
    */
   public boolean isThreadPooling() {
      return m_isThreadPooling;
   }
   
   public void setThreadWorker(String threadWorker) {
      m_threadWorker = threadWorker;
   }
   
   public String getThreadWorker() {
      return m_threadWorker;
   }
   
   /**
    *
    * @return
    * @see Socket()
    */
   public Socket getSocket() {
      Socket socket = null;
      
      if (m_socket != null) {
         socket = m_socket;
      } else if (m_socketRequest != null) {
         socket = m_socketRequest.getSocket();
      }
      
      return socket;
   }
   
   public String getRunByThreadWorkerId() {
      return m_threadWorker;
   }
}
