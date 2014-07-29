/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.swampbits.chaudiere;

/**
 * SocketCompletionObserver is an interface for being notified when a Socket
 * is no longer being used and is being closed.
 * @author paul
 */
public interface SocketCompletionObserver {
   
   /**
    * Notifies the observer that the specified socket is being closed
    * @param socket the socket that is being closed
    */
   public void notifySocketComplete(Socket socket);
   
}
