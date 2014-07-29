/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 * StrUtils is a utility class of convenience methods for working with strings.
 * @author paul
 */
public class StrUtils {

   /**
    * Strips characters from the end of the string
    * @param s the string whose end will be stripped
    * @param strip the character to strip
    * @return the stripped string
    */
   public static String stripTrailing(String s, char strip) {
      if ((s == null) || (s.length() == 0)) {
         return s;
      }
   
      final int stringLen = s.length();
    
      int newLength = (int) stringLen - 1;   // start at last character before NULL
    
      // Find out how many trailing characters we have
      while ((0 <= newLength) && (s.charAt(newLength) == strip)) {
         --newLength;
      }
    
      ++newLength;
    
      // Did we not have any characters to strip?
      if (newLength == stringLen) {
         return s;
      }
   
      return s.substring(0,newLength);
   }

   /**
    * Pads a string to the desired length using the pad char
    * @param s the string to pad
    * @param padChar the padding character
    * @param paddedLength the padded length
    * @return the padded string
    */
   public static String padRight(String s, char padChar, int paddedLength) {
      if (s.length() < paddedLength) {
         StringBuilder padded = new StringBuilder();
         padded.append(s);
         
         while (padded.length() < paddedLength) {
            padded.append(padChar);
         }
         
         return padded.toString();
      } else {
         return s;
      }
   }
}
