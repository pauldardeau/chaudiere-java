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
 *
 * @author paul
 */
public class Socket {
   
   private static final String EMPTY_STRING = "";
   private static final String CRLF = "\r\n";
   private static final String LF = "\n";

   private static final int DEFAULT_BUFFER_SIZE = 1024;

   private SocketCompletionObserver m_completionObserver;
   private String m_lineInputBuffer;
   private final String m_serverAddress;
   private java.net.Socket m_socket;
   //private struct sockaddr_in m_serverAddr;
   //private int m_socketFD;
   private int m_userIndex;
   private final int m_port;
   private boolean m_isConnected;
   private boolean m_includeMessageSize;
   private StringBuilder m_inputBuffer;
   private int m_inBufferSize;
   private int m_lastReadSize;
   private BufferedReader m_reader;
   private BufferedWriter m_writer;
   
   
   /*
   protected boolean readMsg(int length) {
      if (!isOpen()) {
         //Logger.warning("unable to read message size, socket is closed");
         return false;
      }
    
      // do we need a bigger buffer?
      if (((length + 1) > m_inBufferSize) || !m_inputBuffer) {
         m_inBufferSize = length + 1;
         std::unique_ptr<char []> newBuffer(new char[m_inBufferSize]);
         m_inputBuffer = std::move(newBuffer);
      }
    
      if (readSocket(m_inputBuffer.get(), length)) {
         m_inputBuffer[length] = '\0';
         m_lastReadSize = length;
         return true;
      } else {
         //Logger.error("readSocket failed");
         return false;
      }   
   }
   */
   
   //bool readSocket(char* buffer, int bytesToRead) noexcept;
   protected boolean open() {
      if ((m_serverAddress != null) && (m_serverAddress.length() > 0) && (m_port > 0)) {
         try {
            m_socket = new java.net.Socket(m_serverAddress, m_port);
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
   
   protected void init() {
      setTcpNoDelay(true);
   }
   
   /*
   protected void setLineInputBuffer(String s) {
      
   }
   
   protected void appendLineInputBuffer(String s) {
      
   }
   */
   
   //public static int createSocket() {
      
   //}
   
   // throws BasicException
   public Socket(String address, int port) throws Exception {
      m_completionObserver = null;
      m_serverAddress = address;
      //m_socketFD = -1;
      m_userIndex = -1;
      m_port = port;
      m_isConnected = false;
      m_includeMessageSize = false;
      m_inputBuffer = null;
      m_inBufferSize = DEFAULT_BUFFER_SIZE;
      
      //Logger.logInstanceCreate("Socket");

      if (!open()) {
         throw new Exception("Unable to open socket");
      }
   }
   
   //public Socket(int socketFD) {
      
   //}
   
   //public Socket(SocketCompletionObserver completionObserver, int socketFD) {
  
   //}
    
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
   
   public boolean write(char[] buffer, int bufsize) {
      if (send(buffer, bufsize) > 0) {
         return true;
      } else {
         return false;
      }
   }
   
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
    
   public int receive(char[] receiveBuffer, int bufferLength) {
      if ((m_socket == null) ||
          !m_isConnected ||
          (m_reader == null) ||
          (receiveBuffer == null) ||
          (bufferLength < 1))
      {
         return -1;
      }
      
      int bytesReceived;
      
      try {
         bytesReceived = m_reader.read(receiveBuffer, 0, bufferLength);
         if (bytesReceived == 0) {
            close();
         }
      } catch (java.io.IOException ioe) {
         bytesReceived = -1;
      }
      
      return bytesReceived;
   }
   
   public boolean read(char[] buffer, int bufferLen) {
      return readSocket(buffer, bufferLen);
   }
   
   public boolean readSocket(char[] buffer, int bytesToRead) {
      int total_bytes_rcvd = 0;
      int currentBufferOffset = 0;
    
      do {
         int bytesReceived;
      
         try {
            bytesReceived = m_reader.read(buffer,
                                          currentBufferOffset,
                                          bytesToRead - total_bytes_rcvd);
            
            if (bytesReceived == 0) {
               close();
            }
         } catch (java.io.IOException ioe) {
            bytesReceived = -1;
         }
      
         //if (Logger::isLogging(Logger::LogLevel::Debug)) {
         //   char msg[128];
         //   std::snprintf(msg, 128, "recv, bytes from recv = %ld", bytes);
         //   Logger::debug(std::string(msg));
         //}
        
         if (bytesReceived <= 0) {  // error or connection closed by peer?
            if (bytesReceived == 0) {
               // our connection has been closed by the other process. nothing
               // more we can do!!!
               //Logger.debug("connection reset by peer");
               close();
               return false;
            } else {
               /*
               if (EINTR == errno) {  // interrupted?
                  // not really an error
                  continue;
               } else {
                  //Logger.warning("recv returned an error");
               }
               */
            }
            
            return false;
         }
        
         total_bytes_rcvd += bytesReceived;
         currentBufferOffset += bytesReceived;
        
      } while (total_bytes_rcvd < bytesToRead);
    
      return true;   
   }
    
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
   
   public boolean isOpen() {
      return (m_socket != null);
   }
   
   public boolean isConnected() {
      return m_isConnected;
   }
   
   public void closeConnection() {
      close();
   }
   
   //public int getFileDescriptor() {
      
   //}
   
   public void requestComplete() {
      //if (m_completionObserver != null) {
      //   m_completionObserver.notifySocketComplete(this);
      //}
   }
    
   public void setUserIndex(int userIndex) {
      m_userIndex = userIndex;
   }
   
   public int getUserIndex() {
      return m_userIndex;
   }
    
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
   
   public boolean getTcpNoDelay() {
      if (m_socket != null) {
         try {
            return m_socket.getTcpNoDelay();
         } catch (java.net.SocketException e) {
         }
      }
      
      return false;
   }
    
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
   
   public int getSendBufferSize() {
      if (m_socket != null) {
         try {
            return m_socket.getSendBufferSize();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return 0;
   }
    
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
   
   public int getReceiveBufferSize() {
      if (m_socket != null) {
         try {
            return m_socket.getReceiveBufferSize();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return 0;
   }
    
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
   
   public boolean getKeepAlive() {
      if (m_socket != null) {
         try {
            return m_socket.getKeepAlive();
         } catch (java.net.SocketException se) {
            
         }
      }
      
      return false;
   }
    
   public String readLine() {
      if ((m_socket != null) && (m_reader != null)) {
         try {
            return m_reader.readLine();
         } catch (java.io.IOException ioe) {
            
         }
      }
      
      return null;
   }
    
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
    
   public int getPort() {
      return m_port;
   }
    
   public void setIncludeMessageSize(boolean isSizeIncluded) {
      m_includeMessageSize = isSizeIncluded;
   }

}
