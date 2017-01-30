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
/**
 * Wrapper/utility class around uname function
 */
public class SystemInfo {
   
   private String m_sysName;
   private String m_nodeName;
   private String m_release;
   private String m_version;
   private String m_machine;
   private boolean m_retrievedSystemInfo = false;

   /**
    * Default constructor and retrieves the system information
    */
   public SystemInfo() {
      //struct utsname sysinfo;
      //if (0 == ::uname(&sysinfo)) {
      //   m_sysName = sysinfo.sysname;
      //   m_nodeName = sysinfo.nodename;
      //   m_release = sysinfo.release;
      //   m_version = sysinfo.version;
      //   m_machine = sysinfo.machine;
      //   m_retrievedSystemInfo = true;
      //}
   }
   
   /**
    * Retrieves the system name (sysname)
    * @return the system name
    */
   public String sysName() {
      return m_sysName;
   }
   
   /**
    * Retrieves the node name (nodename)
    * @return the node name
    */
   public String nodeName() {
      return m_nodeName;
   }
   
   /**
    * Retrieves the release (release)
    * @return the release
    */
   public String release() {
      return m_release;
   }
   
   /**
    * Retrieves the version (version)
    * @return the version
    */
   public String version() {
      return m_version;
   }
   
   /**
    * Retrieves the machine (machine)
    * @return the machine
    */
   public String machine() {
      return m_machine;
   }
   
   /**
    * Determines if the system information was able to be retrieved
    * @return boolean indicating whether the system information was successfully read
    */
   public boolean retrievedSystemInfo() {
      return m_retrievedSystemInfo;
   }

}
