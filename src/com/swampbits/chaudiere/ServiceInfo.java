/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 * ServiceInfo captures basic information for a network service such as the
 * service name, host that provides the service, and the port number.
 * @author paul
 */
public class ServiceInfo {
    
   private String m_serviceName;
   private String m_host;
   private short m_port;
    
   /**
    * Default constructor
    */
   public ServiceInfo() {
      m_port = 0;
   }
   
   /**
    * Initialize ServiceInfo instance with service name, host, and port number
    * @param serviceName the name of the service
    * @param host the host for the service
    * @param port the port number for the service
    */
   public ServiceInfo(String serviceName, String host, short port) {
      m_serviceName = serviceName;
      m_host = host;
      m_port = port;
   }
   
   /**
    * Retrieves the name of the service
    * @return the service name
    */
   public String serviceName() {
      return m_serviceName;
   }
   
   /**
    * Retrieves the host for the service
    * @return the service host
    */
   public String host() {
      return m_host;
   }
   
   /**
    * Retrieves the port number for the service
    * @return the service port number
    */
   public short port() {
      return m_port;
   }
   
   /**
    * Updates the name of the service
    * @param serviceName new name for the service
    */
   void setServiceName(String serviceName) {
      m_serviceName = serviceName;
   }
   
   /**
    * Updates the host for the service
    * @param host the new host for the service
    */
   public void setHost(String host) {
      m_host = host;
   }
   
   /**
    * Updates the port number for the service
    * @param port the new port number for the service
    */
   public void setPort(short port) {
      m_port = port;
   }

}
