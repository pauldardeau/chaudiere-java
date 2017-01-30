/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

import java.util.HashMap;
import java.util.List;


/**
 * KeyValuePairs is a collection of key/value pairs where the key and the
 * value are both strings.
 * @author paul
 */
public class KeyValuePairs {
    
   private final HashMap<String,String> m_keyValues;

   /**
    * Default constructor
    */
   public KeyValuePairs() {
      m_keyValues = new HashMap<>();
   }
    
   /**
    * Copy constructor
    * @param kvpCopy the source of the copy
    */
   public KeyValuePairs(KeyValuePairs kvpCopy) {
      m_keyValues = new HashMap<>(kvpCopy.m_keyValues);
   }
   
   /**
    * Retrieves the keys contained within the collection
    * @param keys the vector to be populated with collection keys
    */
   public void getKeys(List<String> keys) {
      for (String key: m_keyValues.keySet()) {
         keys.add(key);
      }
   }

   /**
    * Determines if the specified key exists within the collection.
    * @param key the key whose existing is being tested
    * @return boolean indicating whether the specified key exists
    */
   public boolean hasKey(String key) {
      return m_keyValues.containsKey(key);
   }

   /**
    * Retrieves the value associated with the specified key
    * @param key the key whose value is requested
    * @throw InvalidKeyException
    * @return the value associated with the specified key
    */
   public String getValue(String key) {
      return m_keyValues.get(key);
   }

   /**
    * Adds a key/value pair to the collection
    * @param key the key of the pair being added
    * @param value the value of the pair being added
    */
   public void addPair(String key, String value) {
      m_keyValues.put(key, value);
   }

   /**
    * Removes a pair identified by the specified key
    * @param key the key of the pair to be removed
    * @return boolean indicating whether a pair was removed
    */
   public boolean removePair(String key) {
      if (hasKey(key)) {
         m_keyValues.remove(key);
         return true;
      } else {
         return false;
      }
   }

   /**
    * Removes all key/value pairs within the collection
    */
   public void clear() {
      m_keyValues.clear();
   }
   
   /**
    * Retrieves the number of pairs contained within the collection
    * @return the number of key/value pairs contained
    */
   public int size() {
      return m_keyValues.size();
   }
   
   /**
    * Determines if the collection is empty
    * @return boolean indicating whether the collection is empty
    */
   public boolean empty() {
      return m_keyValues.isEmpty();
   }

}
