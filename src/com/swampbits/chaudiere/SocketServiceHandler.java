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
public interface SocketServiceHandler {
   void serviceSocket(SocketRequest socketRequest);
   String getName();
}
