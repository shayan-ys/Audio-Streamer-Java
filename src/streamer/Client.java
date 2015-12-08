package streamer;

import java.lang.*;
import java.io.*;
import java.net.*;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

class Client {
   public static void main(String args[]) {
      try {
         Socket skt = new Socket("localhost", 1234);
//         InputStreamReader in = new InputStreamReader(skt.getInputStream());
         System.out.print("Received string: '");
         Player plyr = new Player(skt.getInputStream());
         System.out.println("1");
         plyr.play();
         System.out.println("2");
         plyr.wait(500000,5000);
         System.out.println("3");
      }
      catch(Exception e) {
         System.out.print("Clinet: Whoops! It didn't work!\n");
      }
   }
}