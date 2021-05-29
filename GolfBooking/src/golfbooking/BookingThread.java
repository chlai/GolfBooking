/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfbooking;

import com.sun.jna.Pointer;

/**
 *
 * @author User
 */
class BookingThread extends Thread {
   private Thread t;
   private String threadName;
   public Pointer wndptr ;
   
   BookingThread( String name, Pointer ptr) {
      threadName = name;
      System.out.println("Creating " +  threadName );
      wndptr = ptr;
   }
  
   @Override
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      } catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   @Override
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
   
    public static void main(String args[]) {
      BookingThread T1 = new BookingThread( "Thread-1",null);
      T1.start();
      
      BookingThread T2 = new BookingThread( "Thread-2",null);
      T2.start();
   }   
}
