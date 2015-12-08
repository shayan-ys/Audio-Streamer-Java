package streamer;

import java.lang.*;
import java.io.*;
import java.net.*;

class Server {
   public static void main(String args[]) {
      String data = "Toobie ornaught toobie";
//      try {
//         ServerSocket srvr = new ServerSocket(1234);
//         Socket skt = srvr.accept();
//         System.out.print("Server has connected!\n");
//         PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
//         System.out.print("Sending string: '" + data + "'\n");
//         out.print(data);
//         out.close();
//         skt.close();
//         srvr.close();
//      }
        try (ServerSocket serverSocker = new ServerSocket(1234); 
            FileInputStream in = new FileInputStream("/Users/Shayanyousefian/Documents/Eclipse_workspace/Audio-Streamer-Java/still.mp3")) {
            if (serverSocker.isBound()) {
                Socket client = serverSocker.accept();
                OutputStream out = client.getOutputStream();

                byte buffer[] = new byte[2048];
                int count;
                for (int i=0; (count = in.read(buffer)) != -1 ; i++)
                    out.write(buffer, 0, count);
              /* System.out.println("ta inja umadam");
                for (int i=0; (count = in.read(buffer)) != -1 ; i++)
                    out.write(buffer, 200, count);*/
            }
        }
      catch(Exception e) {
         System.out.print("Whoops! It didn't work!\n");
      }
   }
}