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
   
   private String m_serverAddress = null;
   private java.net.Socket m_socket = null;
   private int m_userIndex = 0;
   private int m_port = 0;
   private boolean m_isConnected = false;
   private BufferedReader m_reader = null;
   private BufferedWriter m_writer = null;
   private SocketCompletionObserver m_completionObserver = null;
   private int m_socketFD = -1;
   
   
   /**
    * Opens a socket connection to the server on the specified port
    * @return boolean indicating whether the connection was established
    */
   public boolean open() {
      if ((m_serverAddress != null) && (m_serverAddress.length() > 0) && (m_port > 0)) {
         try {
            m_socket = new java.net.Socket(m_serverAddress, m_port);
            init();
            m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
            m_writer = new BufferedWriter(new OutputStreamWriter(m_socket.getOutputStream()));
            m_isConnected = true;
            return true;
         } catch (java.net.UnknownHostException uhe) {
            Logger.debug("Socket.open failed (UnknownHostException): " + uhe.getMessage());
         } catch (java.io.IOException ioe) {
            Logger.debug("Socket.open failed (IOException): " + ioe.getMessage());
         } catch (java.lang.SecurityException se) {
            Logger.debug("Socket.open failed (SecurityException): " + se.getMessage());
         } catch (java.lang.IllegalArgumentException iae) {
            Logger.debug("Socket.open failed (IllegalArgumentException): " + iae.getMessage());
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
      m_port = port;
   }
   
   public Socket(java.net.Socket socket) {
      m_socket = socket;
      if ((socket != null) && (socket.getPort() > 0)) {
         m_isConnected = true;
      }
   }
   
   public Socket(SocketCompletionObserver completionObserver, int socketFD) {
      m_completionObserver = completionObserver;
      m_socketFD = socketFD;
      m_userIndex = -1;
      m_isConnected = true;
      m_serverAddress = null;
      m_port = -1;
      //m_includeMessageSize = false;
      //m_inputBuffer(new char[DEFAULT_BUFFER_SIZE]);
      //m_inBufferSize = DEFAULT_BUFFER_SIZE;
      //Logger.logInstanceCreate("Socket");
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
         Logger.debug("Socket.send failed: " + ioe.getMessage());
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
         Logger.debug("Socket.write failed: " + ioe.getMessage());
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
            Logger.debug("Socket.readSocket failed: " + ioe.getMessage());
         }
      
         if (Logger.isLogging(Logger.LogLevel.Debug)) {
            Logger.debug("readSocket, bytes received = " + bytesReceived);
         }
        
         if (bytesReceived <= 0) {  // error or connection closed by peer?
            if (bytesReceived == 0) {
               // our connection has been closed by the other process. nothing
               // more we can do!!!
               Logger.debug("connection reset by peer");
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
            Logger.debug("Socket.close failed: " + ioe.getMessage());
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
         } catch (java.net.SocketException se) {
            Logger.debug("Socket.setTcpNoDelay: " + se.getMessage());
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
         } catch (java.net.SocketException se) {
            Logger.debug("Socket.getTcpNoDelay failed: " + se.getMessage());
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
            Logger.debug("Socket.setSendBufferSize failed: " + se.getMessage());
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
            Logger.debug("Socket.getSendBufferSize failed: " + se.getMessage());
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
            Logger.debug("Socket.setReceiveBufferSize failed: " + se.getMessage());
         } catch (java.lang.IllegalArgumentException iae) {
            Logger.debug("Socket.setReceiveBufferSize failed: " + iae.getMessage());
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
            Logger.debug("Socket.getReceiveBufferSize failed: " + se.getMessage());
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
            Logger.debug("Socket.setKeepAlive failed: " + se.getMessage());
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
            Logger.debug("Socket.getKeepAlive failed: " + se.getMessage());
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
            Logger.debug("Socket.readLine failed: " + ioe.getMessage());
         }
      }
      
      return null;
   }
    
   /**
    * Retrieves the peer socket IP address. This is useful in server
    * applications when the server needs to log IP addresses of all clients
    * that connect.
    * @return String client IP address or null if unavailable
    */
   public String getPeerIPAddress() {
      String ipAddress = null;
      if (m_socket != null) {
         java.net.SocketAddress address = m_socket.getRemoteSocketAddress();
         if (address != null) {
            if (address instanceof java.net.InetSocketAddress) {
               java.net.InetSocketAddress inetAddress = (java.net.InetSocketAddress) address;
               ipAddress = inetAddress.toString();
            }
         }
      }
      
      return ipAddress;
   }
   
   /**
    * Retrieve port number for socket
    * @return port number
    */
   public int getPort() {
      return m_port;
   }

   public void requestComplete() {
      if (null != m_completionObserver) {
         m_completionObserver.notifySocketComplete(this);
      }
   }
}
