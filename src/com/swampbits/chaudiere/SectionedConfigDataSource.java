/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 * SectionedConfigDataSource is an interface for working with configuration
 * mechanisms based on sections, keys, and values.
 * @author paul
 */
public interface SectionedConfigDataSource {
    
   /**
    * Determines whether the specified section name exists in the configuration
    * @param sectionName the name of the section whose existence is being tested
    * @return boolean indicating whether the specified section exists
    */
   public boolean hasSection(String sectionName);
   
   /**
    * Reads the key/value pairs of the specified section
    * @param sectionName the name of the section to read
    * @param settings collection of key/value pairs to populate from section
    * @see KeyValuePairs()
    * @return boolean indicating whether the specified section could be read
    */
   public boolean readSection(String sectionName, KeyValuePairs settings);
   
   /**
    * Retrieves the value associated with the specified key within the specified section
    * @param section the name of the section containing the key/value pair
    * @param key the name of the key whose value is requested
    * @param value will be assigned the key/value pair value (if it exists)
    * @return boolean indicating whether the value was retrieved
    */
   public boolean getSectionKeyValue(String section,
                                     String key,
                                     StringBuilder value);
}
