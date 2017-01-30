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
public class SocketRequest implements Runnable {
   
   private final Socket m_socket;
   private final SocketServiceHandler m_handler;

   
   /**
    * Constructs a SocketRequest with a Socket and SocketServiceHandler
    * @param socket the Socket associated with the request
    * @param handler the handler to use for processing with the Socket
    * @see Socket()
    * @see SocketServiceHandler()
    */
   public SocketRequest(Socket socket, SocketServiceHandler handler) {
      m_socket = socket;
      m_handler = handler;
   }
   
   /**
    * Services the socket using the specified handler
    */
   @Override
   public void run() {
      if (Logger.isLogging(Logger.LogLevel.Debug)) {
         //TODO: m_socket->getFileDescriptor());
         Logger.debug("request for socket fd=0");
      }

      if (null != m_handler) {
         try {
            m_handler.serviceSocket(this);
         } catch (Exception e) {
            Logger.error("exception in serviceSocket on handler: " + e.getMessage());
         } catch (Throwable t) {
            Logger.error("exception in serviceSocket on handler");
         }
      } else {
         Logger.error("no handler present in SocketRequest");
      }
    
      //m_socket->requestComplete();      
   }
   
   /**
    * Retrieves the file descriptor for the socket
    * @return socket file descriptor
    */
   public int getSocketFD() {
      return 0;
   }
   
   /**
    * Retrieves the Socket associated with the request
    * @see Socket()
    * @return the Socket associated with the request
    */
   public Socket getSocket() {
      return m_socket;
   }
   
   /**
    * Notifies the Socket that the request processing is complete
    */
   public void requestComplete() {
      if (null != m_socket) {
         m_socket.requestComplete();
      }
   }
   
}
