package assignment4;

import java.io.*;
import java.net.*;
import java.util.Scanner;


/*
i used code in CS402 shown in folder sections by the same name of these classes

Implement a simple Java chat client which transmits user text messages entered at the
keyboard to a multicast address and also can simultaneously receive messages sent from
other clients on other machines sent to the same multicast address. The chat client should
initially ask for a user name and prepend that username to all outgoing multicasts so users can
see who the messages are from.
When implementing the chat client, as a minimum, you will need to implement the
transmitter code and the listener code as separate threads so that live messages from
other sources may be received and displayed while the user is also typing a new message to
be sent. There is no need for a server, implementations should not use a server. This is a
standalone application which can talk with other standalone instances of it.

find folder in directory:
{
	cd C:\Users\NOOT NOOT\eclipse-workspace\ParallelDistributionAssignment\src\assignment4
}

open commandprompt
run MulticastReceiver.java first
{
	java MulticastReceiver.java
}

// open a new command prompt and others and watch the receiver pick up connection

run MulticastSender.java
{
	java MulticastSender.java
}

to leave chat, text 'quit'
{
	quit
}

look at MulticastReceiver terminal

*/


public class MulticastSender { // MulticastSender
	  public static void main(String[] args) {
	    DatagramSocket socket = null;
	    DatagramPacket outPacket = null;
	    
	    byte[] outBuf;
	    final int PORT = 8888;
	 
	    try {
			  socket = new DatagramSocket();
			  Scanner hello = new Scanner (System.in);
			  System.out.println("Enter username: ");
					
		      String username = hello.nextLine();
		      String user_enters = username + " has entered the chat!";
		  	
		      String msg;
		      String user_and_message;
		      
			  InetAddress address = InetAddress.getByName("224.2.2.3");
		      
			  outBuf = (user_enters).getBytes();
			  outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
		      socket.send(outPacket);
		      
		      boolean in_chat = true;
		 
		      while (in_chat) {
			        msg = hello.nextLine();
			        
			        if (msg.toLowerCase().equals("quit")){
			        	outBuf = (username + " has left the chat.").getBytes();
						outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
						socket.send(outPacket);
						break;
			        }
			        
			        user_and_message = username + ": " + msg;
			        
					outBuf = (user_and_message).getBytes();
					 
			        //Send to multicast IP address and port
			        outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
			        socket.send(outPacket);
			        
					try {
						Thread.sleep(1000); // loop every 1 second forever
					} catch (InterruptedException ie) {
						System.out.println("ERROR 1 occured");
					}
		      }
		      
		} catch (IOException ioe) {
		  System.out.println(ioe);
		  System.out.println("ERROR 2 occured");
		}
	}
}
	
