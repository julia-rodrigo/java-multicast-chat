package assignment4;
import java.io.*;
import java.net.*;

public class MulticastReceiver {
  public static void main(String[] args) {
    DatagramPacket inPacket = null;
    byte[] inBuf = new byte[256];
    try {
		// A multicast group is specified by a class D IP address and by a standard UDP port number. 
		// Class D IP addresses are in the range 224.0.0.0 to 239.255.255.255, inclusive.
		InetAddress mcastaddr = InetAddress.getByName("224.2.2.3");
		int multicastport = 8888;
		NetworkInterface netif = NetworkInterface.getByInetAddress(mcastaddr);

		//Prepare to join multicast group
		InetSocketAddress groupaddr = new InetSocketAddress(mcastaddr, multicastport);
        MulticastSocket socket = new MulticastSocket(multicastport); // create a multicast socket
		socket.joinGroup(groupaddr, netif); // Tell the router to associate the multicast socket 
											// with the chosen interface and multicast address
      while (true) {
        inPacket = new DatagramPacket(inBuf, inBuf.length);
        socket.receive(inPacket);  // receive a multicast
        String msg = new String(inBuf, 0, inPacket.getLength());
        System.out.println("From " + inPacket.getAddress() + " Msg : " + msg);
      }
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
  }
}
