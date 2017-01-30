/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swampbits.chaudiere;

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
public class StrUtilsTest {
   
   public StrUtilsTest() {
   }
   
   @BeforeClass
   public static void setUpClass() {
   }
   
   @AfterClass
   public static void tearDownClass() {
   }
   
   @Before
   public void setUp() {
   }
   
   @After
   public void tearDown() {
   }

   /**
    * Test of stripTrailing method, of class StrUtils.
    */
   @Test
   public void testStripTrailing() {
      System.out.println("stripTrailing");
      assertEquals("  23", StrUtils.stripTrailing("  23   ", ' '));
      assertEquals("23", StrUtils.stripTrailing("23   ", ' '));
      assertEquals("xyz", StrUtils.stripTrailing("xyz", ' '));
      assertEquals("xx123", StrUtils.stripTrailing("xx123zzz", 'z'));
      assertEquals("zzz123xx", StrUtils.stripTrailing("zzz123xx", 'z'));
      assertEquals("1234567", StrUtils.stripTrailing("1234567", '0'));
      assertEquals("", StrUtils.stripTrailing("", ' '));
   }

   /**
    * Test of padRight method, of class StrUtils.
    */
   @Test
   public void testPadRight() {
      System.out.println("padRight");
      assertEquals("   ", StrUtils.padRight("", ' ', 3));
      assertEquals("abc", StrUtils.padRight("abc", ' ', 2));
      assertEquals("abc", StrUtils.padRight("abc", ' ', 3));
      assertEquals("fred  ", StrUtils.padRight("fred", ' ', 6));
      assertEquals("catXXXX", StrUtils.padRight("cat", 'X', 7));
   }
   
}
