/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * The Socket class wraps a native Java socket and provides convenience
 * methods for using the socket.
 * @author paul
 */
public class Socket {
   
   private final String m_serverAddress;
   private java.net.Socket m_socket;
   private int m_userIndex;
   private final int m_port;
   private boolean m_isConnected;
   private BufferedReader m_reader;
   private BufferedWriter m_writer;
   
   
   /**
    * Opens a socket connection to the server on the specified port
    * @return boolean indicating whether the connection was established
    */
   protected boolean open() {
      if ((m_serverAddress != null) && (m_serverAddress.length() > 0) && (m_port > 0)) {
         try {
            m_socket = new java.net.Socket(m_serverAddress, m_port);
            init();
            m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            m_writer = new BufferedWriter(new OutputStreamWriter(m_socket.getOutputStream()));
            m_isConnected = true;
            return true;
         } catch (java.net.UnknownHostException uhe) {
            
         } catch (java.io.IOException ioe) {
            
         } catch (java.lang.SecurityException se) {
            
         } catch (java.lang.IllegalArgumentException iae) {
            
         }
      }
      
      return false;
   }
   
   /**
    * Initializes a new socket connection in preparation for use
    */
   protected void init() {
      setTcpNoDelay(true);
   }
   
   /**
    * Constructs a new Socket instance using the specified server address and port
    * @param address the IP address of the server
    * @param port the port number used by the server
    * @throws Exception 
    */
   public Socket(String address, int port) throws Exception {
      m_serverAddress = address;
      m_userIndex = -1;
      m_port = port;
      m_isConnected = false;
      
      //Logger.logInstanceCreate("Socket");

      if (!open()) {
         throw new Exception("Unable to open socket");
      }
   }
   
   /**
    * Writes a specific number of bytes to the socket from a buffer
    * @param sendBuffer the buffer whose contents will be sent to the socket
    * @param bufferLength the number of bytes to write
    * @return the number of bytes written
    */
   public int send(char[] sendBuffer, int bufferLength) {
      if ((m_socket == null) ||
          !m_isConnected ||
          (null == sendBuffer) ||
          (m_writer == null))
      {
         return -1;
      }

      try {
         m_writer.write(sendBuffer, 0, bufferLength);
         m_writer.flush();
         return bufferLength;
      } catch (java.io.IOException ioe) {
         return -1;
      }
   }
   
   /**
    * Writes a specific number of bytes to the socket from a buffer
    * @param buffer the buffer whose contents will be sent to the socket
    * @param bufsize the number of bytes to write
    * @return boolean indicating whether the write succeeded
    */
   public boolean write(char[] buffer, int bufsize) {
      if (send(buffer, bufsize) > 0) {
         return true;
      } else {
         return false;
      }
   }
   
   /**
    * Writes a string value to the socket
    * @param payload the string to write
    * @return boolean indicating whether the string was successfully written
    */
   public boolean write(String payload) {
      if ((m_socket == null) ||
          !m_isConnected ||
          (null == payload) ||
          (m_writer == null))
      {
         return false;
      }
      
      try {
         m_writer.write(payload, 0, payload.length());
         m_writer.flush();
         return true;
      } catch (java.io.IOException ioe) {
         return false;
      }
   }
   
   /**
    * Reads a specified number of bytes from the socket into a buffer
    * @param buffer the buffer to receive socket data
    * @param bytesToRead the number of bytes to read
    * @return boolean indicating whether the specified number of bytes were read
    */
   public boolean readSocket(char[] buffer, int bytesToRead) {
      int totalBytesReceived = 0;
      int currentBufferOffset = 0;
    
      do {
         int bytesReceived;
      
         try {
            bytesReceived = m_reader.read(buffer,
                                          currentBufferOffset,
                                          bytesToRead - totalBytesReceived);
            
            if (bytesReceived == 0) {
               close();
            }
         } catch (java.io.IOException ioe) {
            bytesReceived = -1;
         }
      
         //if (Logger.isLogging(Logger.LogLevel.Debug)) {
         //   Logger.debug("socket read, bytes received = " + bytesReceived);
         //}
        
         if (bytesReceived <= 0) {  // error or connection closed by peer?
            if (bytesReceived == 0) {
               // our connection has been closed by the other process. nothing
               // more we can do!!!
               //Logger.debug("connection reset by peer");
               close();
               return false;
            } else {
               //TODO: any possibility or test for EINTR?
            }
            
            return false;
         }
        
         totalBytesReceived += bytesReceived;
         currentBufferOffset += bytesReceived;
        
      } while (totalBytesReceived < bytesToRead);
    
      return true;   
   }
   
   /**
    * Closes any existing connection and releases any IO resources
    */
   public void close() {
      if (m_socket != null) {
         try {
            if (m_reader != null) {
               m_reader.close();
               m_reader = null;
            }
            
            if (m_writer != null) {
               m_writer.close();
               m_writer = null;
            }
            
            m_socket.close();
         } catch (java.io.IOException ioe) {
            
         }
         m_socket = null;
         m_isConnected = false;
      }
   }
   
   /**
    * Determines if the socket connection is currently open
    * @return boolean indicating if the socket connection is currently open
    */
   public boolean isOpen() {
      return (m_socket != null);
   }
   
   /**
    * Determines if the socket is currently connected
    * @return boolean indicating if socket is currently connected
    */
   public boolean isConnected() {
      return m_isConnected;
   }
   
   /**
    * Closes any existing connections and releases resources
    */
   public void closeConnection() {
      close();
   }
   
   /**
    * Sets the user-defined index associated with the socket instance
    * @param userIndex the new value for the user-defined index
    */
   public void setUserIndex(int userIndex) {
      m_userIndex = userIndex;
   }
   
   /**
    * Retrieves the user-defined index associated with the socket instance
    * @return the user-defined index value, -1 if never set
    */
   public int getUserIndex() {
      return m_userIndex;
   }
   
   /**
    * Sets the 'TcpNoDelay' socket option
    * @param on the new value for the TcpNoDelay option
    * @return boolean indicating whether the option was set
    */
   public boolean setTcpNoDelay(boolean on) {
      if (m_socket != null) {
         try {
            m_socket.setTcpNoDelay(on);
            return true;
         } catch (java.net.SocketException e) {
         }
      }
      
      return false;
   }
   
   /**
    * Retrieves the 'TcpNoDelay' option for the socket
    * @return the TcpNoDelay option value, or false on error
    */
   public boolean getTcpNoDelay() {
      if (m_socket != null) {
         try {
            return m_socket.getTcpNoDelay();
         } catch (java.net.SocketException e) {
         }
      }
      
      return false;
   }
   
   /**
    * Sets the socket send buffer size
    * @param size the new size for the send buffer
    * @return boolean indicating whether the buffer size value was set
    */
   public boolean setSendBufferSize(int size) {
      if (m_socket != null) {
         try {
            m_socket.setSendBufferSize(size);
            return true;
         } catch (java.net.SocketException se) {
            
         } catch (java.lang.IllegalArgumentException iae) {
            
         }
      }
      
      return false;
   }
   
   /**
    * Retrieves the socket send buffer size
    * @return the socket send buffer size, or 0 on error
    */
   public int getSendBufferSize() {
      if (m_socket != null) {
         try {
            return m_socket.getSendBufferSize();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return 0;
   }
   
   /**
    * Sets the socket receive buffer size
    * @param size the new size for the receive buffer
    * @return boolean indicating whether the buffer size value was set
    */
   public boolean setReceiveBufferSize(int size) {
      if (m_socket != null) {
         try {
            m_socket.setReceiveBufferSize(size);
            return true;
         } catch (java.net.SocketException se) {
            
         } catch (java.lang.IllegalArgumentException iae) {
            
         }
      }
      
      return false;
   }
   
   /**
    * Retrieves the socket receive buffer size
    * @return the socket receive buffer size, or 0 on error
    */
   public int getReceiveBufferSize() {
      if (m_socket != null) {
         try {
            return m_socket.getReceiveBufferSize();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return 0;
   }
   
   /**
    * Sets the 'keep-alive' option for the socket
    * @param on new value for keep-alive option
    * @return boolean indicating whether the new value was set
    */
   public boolean setKeepAlive(boolean on) {
      if (m_socket != null) {
         try {
            m_socket.setKeepAlive(on);
            return true;
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return false;
   }
   
   /**
    * Retrieves the 'keep-alive' option for the socket
    * @return the boolean value for the keep-alive option, or false on error
    */
   public boolean getKeepAlive() {
      if (m_socket != null) {
         try {
            return m_socket.getKeepAlive();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return false;
   }
   
   /**
    * Retrieves a line of text from the socket
    * @return the line read from the socket, or null on error
    */
   public String readLine() {
      if ((m_socket != null) && (m_reader != null)) {
         try {
            return m_reader.readLine();
         } catch (java.io.IOException ioe) {
            
         }
      }
      
      return null;
   }
    
   /**
    * Retrieves the peer socket IP address. This is useful in server
    * applications when the server needs to log IP addresses of all clients
    * that connect.
    * @param ipAddress the string that will receive the IP address
    * @return boolean indicating whether the peer IP address could be retrieved
    */
   public boolean getPeerIPAddress(StringBuilder ipAddress) {
      if (m_socket != null) {
         java.net.SocketAddress address = m_socket.getRemoteSocketAddress();
         if (address != null) {
            if (address instanceof java.net.InetSocketAddress) {
               java.net.InetSocketAddress inetAddress = (java.net.InetSocketAddress) address;
               ipAddress.setLength(0);
               ipAddress.append(inetAddress.toString());
               return true;
            }
         }
      }
      
      return false;
   }
   
   /**
    * Retrieve port number for socket
    * @return port number
    */
   public int getPort() {
      return m_port;
   }

}
