/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swampbits.chaudiere.mock;

import com.swampbits.chaudiere.Socket;
import com.swampbits.chaudiere.SocketCompletionObserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * MockSocket is a fake socket to be used in unit testing
 * @author paul
 */
public class MockSocket extends Socket {

   private boolean keepAlive = false;
   private boolean tcpNoDelay = true;
   private int sendBufferSize = 8192;
   private int recvBufferSize = 8192;
   private String peerIPAddress = "";
   
   private boolean failOnOpen = false;
   
   private StringWriter stringWriter = null;
   private String dataToRead = "";

   /**
    * Constructs a new Socket instance using the specified server address and port
    * @param address the IP address of the server
    * @param port the port number used by the server
    * @throws Exception 
    */
   public MockSocket(String address, int port) throws Exception {
      super(address, port);
   }
   
   public MockSocket(SocketCompletionObserver completionObserver, int socketFD) {
      super(completionObserver, socketFD);
   }
   
   public void setFailOnOpen() {
      failOnOpen = true;
   }
   
   public boolean getFailOnOpen() {
      return failOnOpen;
   }

   @Override
   public boolean open() {
      String serverAddress = getServerAddress();
      int port = getPort();
      if ((serverAddress != null) && (serverAddress.length() > 0) && (port > 0)) {
         if (!failOnOpen) {
            setConnected(true);
            init();
            stringWriter = new StringWriter();
            setReader(new BufferedReader(new StringReader(dataToRead)));
            setWriter(new BufferedWriter(stringWriter));
            return true;
         }
      }
      
      return false;
   }
   
   /**
    * Closes any existing connection and releases any IO resources
    */
   @Override
   public void close() {
      setConnected(false);
   }
   
   /**
    * Determines if the socket connection is currently open
    * @return boolean indicating if the socket connection is currently open
    */
   @Override
   public boolean isOpen() {
      return isConnected();
   }
   
   /**
    * Closes any existing connections and releases resources
    */
   @Override
   public void closeConnection() {
      setConnected(false);
   }
   
   /**
    * Sets the 'TcpNoDelay' socket option
    * @param on the new value for the TcpNoDelay option
    * @return boolean indicating whether the option was set
    */
   @Override
   public boolean setTcpNoDelay(boolean on) {
      tcpNoDelay = on;
      return true;
   }
   
   /**
    * Retrieves the 'TcpNoDelay' option for the socket
    * @return the TcpNoDelay option value, or false on error
    */
   @Override
   public boolean getTcpNoDelay() {
      return tcpNoDelay;
   }
   
   /**
    * Sets the socket send buffer size
    * @param size the new size for the send buffer
    * @return boolean indicating whether the buffer size value was set
    */
   @Override
   public boolean setSendBufferSize(int size) {
      sendBufferSize = size;
      return true;
   }
   
   /**
    * Retrieves the socket send buffer size
    * @return the socket send buffer size, or 0 on error
    */
   @Override
   public int getSendBufferSize() {
      return sendBufferSize;
   }
   
   /**
    * Sets the socket receive buffer size
    * @param size the new size for the receive buffer
    * @return boolean indicating whether the buffer size value was set
    */
   @Override
   public boolean setReceiveBufferSize(int size) {
      recvBufferSize = size;
      return true;
   }
   
   /**
    * Retrieves the socket receive buffer size
    * @return the socket receive buffer size, or 0 on error
    */
   @Override
   public int getReceiveBufferSize() {
      return recvBufferSize;
   }
   
   /**
    * Sets the 'keep-alive' option for the socket
    * @param on new value for keep-alive option
    * @return boolean indicating whether the new value was set
    */
   @Override
   public boolean setKeepAlive(boolean on) {
      keepAlive = on;
      return true;
   }
   
   /**
    * Retrieves the 'keep-alive' option for the socket
    * @return the boolean value for the keep-alive option, or false on error
    */
   @Override
   public boolean getKeepAlive() {
      return keepAlive;
   }
   
   /**
    * Retrieves the peer socket IP address. This is useful in server
    * applications when the server needs to log IP addresses of all clients
    * that connect.
    * @return String client IP address or null if unavailable
    */
   @Override
   public String getPeerIPAddress() {
      return peerIPAddress;
   }
   
   /**
    * 
    * @param peerIPAddress 
    */
   public void setPeerIPAddress(String peerIPAddress) {
      this.peerIPAddress = peerIPAddress;
   }
   
   public String getWritten() {
      return stringWriter.toString();
   }
   
   public void setDataToRead(String dataToRead) {
      this.dataToRead = dataToRead;
   }
}
