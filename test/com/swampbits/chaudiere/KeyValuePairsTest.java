/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swampbits.chaudiere;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paul
 */
public class KeyValuePairsTest {
   
   private KeyValuePairs kvp;
   
   public KeyValuePairsTest() {
   }
   
   @BeforeClass
   public static void setUpClass() {
   }
   
   @AfterClass
   public static void tearDownClass() {
   }
   
   @Before
   public void setUp() {
      kvp = new KeyValuePairs();
      kvp.addPair("stooge1", "moe");
      kvp.addPair("stooge2", "larry");
      kvp.addPair("stooge3", "curly");
   }
   
   @After
   public void tearDown() {
      kvp = null;
   }

   /**
    * Test of getKeys method, of class KeyValuePairs.
    */
   @Test
   public void testGetKeys() {
      System.out.println("getKeys");
      List<String> keys = kvp.getKeys();
      assertNotNull(keys);
      assertTrue(keys.size() > 0);
   }

   /**
    * Test of hasKey method, of class KeyValuePairs.
    */
   @Test
   public void testHasKey() {
      System.out.println("hasKey");
      assertEquals(false, kvp.hasKey("stooge4"));
      assertEquals(true, kvp.hasKey("stooge2"));
   }

   /**
    * Test of getValue method, of class KeyValuePairs.
    */
   @Test
   public void testGetValue() {
      System.out.println("getValue");
      assertEquals("larry", kvp.getValue("stooge2"));
      assertNull(kvp.getValue("foo"));
   }

   /**
    * Test of addPair method, of class KeyValuePairs.
    */
   @Test
   public void testAddPair() {
      System.out.println("addPair");
      int beforeAdd = kvp.size();
      kvp.addPair("stooge4", "shemp");
      assertEquals(beforeAdd+1, kvp.size());
   }

   /**
    * Test of removePair method, of class KeyValuePairs.
    */
   @Test
   public void testRemovePair() {
      System.out.println("removePair");
      int beforeRemove = kvp.size();
      kvp.removePair("stooge2");
      assertEquals(beforeRemove-1, kvp.size());
   }

   /**
    * Test of clear method, of class KeyValuePairs.
    */
   @Test
   public void testClear() {
      System.out.println("clear");
      kvp.clear();
      assertEquals(0, kvp.size());
      kvp.addPair("stooge1", "moe");
      kvp.addPair("stooge2", "larry");
      assertTrue(kvp.size() > 0);
   }

   /**
    * Test of size method, of class KeyValuePairs.
    */
   @Test
   public void testSize() {
      System.out.println("size");
      assertEquals(3, kvp.size());
   }

   /**
    * Test of empty method, of class KeyValuePairs.
    */
   @Test
   public void testEmpty() {
      System.out.println("empty");
      assertFalse(kvp.empty());
      kvp.clear();
      assertTrue(kvp.empty());
      kvp.addPair("stooge1", "moe");
      assertFalse(kvp.empty());
   }
   
}
