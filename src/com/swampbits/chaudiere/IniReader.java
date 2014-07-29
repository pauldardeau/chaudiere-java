/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * IniReader is a utility class that knows how to read and parse a .ini
 * configuration file. Ini configuration files were popularized on Win32
 * platforms as a simple form of configuration files.
 * @author paul
 */
public class IniReader implements SectionedConfigDataSource {

   private static final String EOL_LF             = "\n";
   private static final String EOL_CR             = "\r";
   private static final String OPEN_BRACKET       = "[";
   private static final String CLOSE_BRACKET      = "]";
   private static final String COMMENT_IDENTIFIER = "#";

   private final String m_iniFile;
   private String m_fileContents;

    
   /**
    * Reads the file from the file system (file name/path specified in constructor)
    * @return boolean indicating if the file was read successfully
    */
   protected boolean readFile() {
       
      FileInputStream stream = null;
      Reader reader = null;
      
      try {
         stream = new FileInputStream(m_iniFile);
         reader = new BufferedReader(new InputStreamReader(stream));
         StringBuilder builder = new StringBuilder();
         char[] buffer = new char[8192];
         int read;
         while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
            builder.append(buffer, 0, read);
         }
         
         m_fileContents = builder.toString();
      } catch (java.io.FileNotFoundException fnfe) {
         return false;
      } catch (java.io.IOException ioe) {
         return false;
      } finally {
         try {
            if (stream != null) {
               stream.close();
            }
            
            if (reader != null) {
               reader.close();
            }
         } catch (java.io.IOException ignored) {
            
         }
      }
   
      // strip out any comments
      boolean strippingComments = true;
      int posCommentStart;
      int posCR;
      int posLF;
      int posEOL;
      int posCurrent = 0;
   
      while (strippingComments) {
         posCommentStart = m_fileContents.indexOf(COMMENT_IDENTIFIER, posCurrent);
         if (-1 == posCommentStart) {
            // not found
            strippingComments = false;
         } else {
            posCR = m_fileContents.indexOf(EOL_CR, posCommentStart + 1);
            posLF = m_fileContents.indexOf(EOL_LF, posCommentStart + 1);
            final boolean haveCR = (-1 != posCR);
            final boolean haveLF = (-1 != posLF);
         
            if (!haveCR && !haveLF) {
               // no end-of-line marker remaining
               // erase from start of comment to end of file
               m_fileContents = m_fileContents.substring(0, posCommentStart);
               strippingComments = false;
            } else {
               // at least one end-of-line marker was found
            
               // were both types found
               if (haveCR && haveLF) {
                  posEOL = posCR;
               
                  if (posLF < posEOL) {
                     posEOL = posLF;
                  }
               } else {
                  if (haveCR) {
                     // CR found
                     posEOL = posCR;
                  } else {
                     // LF found
                     posEOL = posLF;
                  }
               }
            
               final String beforeComment = m_fileContents.substring(0, posCommentStart);
               final String afterComment = m_fileContents.substring(posEOL);
               m_fileContents = beforeComment + afterComment;
               posCurrent = beforeComment.length();
            }
         }
      }
   
      return true;        
   }
   
   /**
    * Retrieves the string identifier in the INI file for the specified section name
    * @param sectionName the name of the section whose identifier is to be constructed
    * @return the section identifier for the specified section name
    */
   protected String bracketedSection(String sectionName) {
      return OPEN_BRACKET + sectionName.trim() + CLOSE_BRACKET;
   }
    
   /**
    * Constructs an IniReader using the specified file
    * @param iniFile the file name/path of the INI file to read
    * @throws Exception
    */
   public IniReader(String iniFile) throws Exception {
      m_iniFile = iniFile;
        
      if (!readFile()) {
         throw new Exception("unable to read configuration file: " + iniFile);
      }
   }
   
   /**
    * Reads the key/value pairs of the specified section
    * @param section the name of the section to read
    * @param mapSectionValues collection of key/value pairs to populate from section
    * @see KeyValuePairs()
    * @return boolean indicating whether the specified section could be read
    */
   @Override
   public boolean readSection(String section, KeyValuePairs mapSectionValues) {
      final String sectionId = bracketedSection(section);
      int posSection = m_fileContents.indexOf(sectionId);
    
      if (posSection == -1) {
         return false;
      }
    
      final int posEndSection = posSection + sectionId.length();
      final int startNextSection = m_fileContents.indexOf(OPEN_BRACKET, posEndSection);
    
      String sectionContents;
    
      // do we have another section?
      if (startNextSection != -1) {
         // yes, we have another section in the file -- read everything
         // up to the next section
         sectionContents = m_fileContents.substring(posEndSection,
                                                    startNextSection);
      } else {
         // no, this is the last section -- read everything left in
         // the file
         sectionContents = m_fileContents.substring(posEndSection);
      }
    
      int posEol;
      int  index = 0;
    
      // process each line of the section
      while ((posEol = sectionContents.indexOf(EOL_LF, index)) != -1) {
      
         String line = sectionContents.substring(index, posEol);
         if (line.length() > 0) {
            int posCR = line.indexOf('\r');
            if (posCR != -1) {
               line = line.substring(0, posCR);
            }
            
            final int posEqual = line.indexOf('=');
            
            if ((posEqual != -1) && (posEqual < line.length())) {
               final String key = line.substring(0, posEqual).trim();
                
               // if the line is not a comment
               if (!key.startsWith(COMMENT_IDENTIFIER)) {
                  mapSectionValues.addPair(key,
                                           line.substring(posEqual + 1).trim());
               }
            }
         }
        
         index = posEol + 1;
      }
    
      return true;        
   }

   /**
    * Retrieves the value associated with the specified key within the specified section
    * @param section the name of the section containing the key/value pair
    * @param key the name of the key whose value is requested
    * @param value will be assigned the key/value pair value (if it exists)
    * @return boolean indicating whether the value was retrieved
    */
   @Override
   public boolean getSectionKeyValue(String section, String key, StringBuilder value) {
      KeyValuePairs map = new KeyValuePairs();
    
      if (!readSection(section, map)) {
         //Logger.warning("IniReader readSection returned false");
         return false;
      }
    
      final String strippedKey = key.trim();
    
      if (!map.hasKey(strippedKey)) {
         return false;
      }
    
      value.setLength(0);
      value.append(map.getValue(key));
    
      return true;        
   }
   
   /**
    * Determines whether the specified section name exists in the INI file
    * @param section the name of the section whose existence is being tested
    * @return boolean indicating whether the specified section exists
    */
   @Override
   public boolean hasSection(String section) {
      return (-1 != m_fileContents.indexOf(bracketedSection(section)));
   }
}
