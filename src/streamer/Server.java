package streamer;

import java.lang.*;
import java.io.*;
import java.net.*;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;

class Server {
    
    public String path = "/Users/Shayanyousefian/Downloads/Ali-Zand-Vakili-Be-Sooye-To-@Otaghe8Bot.mp3";
    
    public Server() {
        
    }
   public static void main(String args[]) {
       streamMetaData();
       streamAudio();
       
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
        
   }
   public static void streamMetaData(){
       try{
            File file = new File("/Users/Shayanyousefian/Downloads/Ali-Zand-Vakili-Be-Sooye-To-@Otaghe8Bot.mp3");
            AudioFile f = AudioFileIO.read(file);
            //Tag tag = f.getTag();
            AudioHeader ah = f.getAudioHeader();
            int lengthSec = ah.getTrackLength();
            
            System.out.println(lengthSec);
            
            String data = String.format("%02d", lengthSec*1000);
            
            ServerSocket srvr = new ServerSocket(1236);
            Socket skt = srvr.accept();
            System.out.print("Meta Server has connected!\n");
            PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
            System.out.print("Sending Meta string: '" + data + "'\n");
            out.print(data);
            out.close();
            skt.close();
            srvr.close();
        }
      catch(Exception e) {
         System.out.print("Whoops! It didn't work!\n");
      }
   }
   public static void streamAudio(){
       try (ServerSocket serverSocker = new ServerSocket(1234); 
            FileInputStream in = new FileInputStream("/Users/Shayanyousefian/Downloads/Ali-Zand-Vakili-Be-Sooye-To-@Otaghe8Bot.mp3")) {
//            FileInputStream in = new FileInputStream("/Users/Shayanyousefian/Music/iTunes/iTunes Media/Music/Anathema/Alternative 4/01 Shroud of False.m4a")) {
            in.skip(1016096);
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