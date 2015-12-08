package streamer;

import javazoom.jl.player.Player;
//import javazoom.jl.player.advanced.*;
//import java.io.File;
import java.io.FileInputStream;
//import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;
import javazoom.jl.player.advanced.AdvancedPlayer;
 
//import javax.sound.sampled.*;
 
public class Main {
	
	
	public static void main(String[] args) {
            ServerSocket MyService;
		try {
//                    MyService = new ServerSocket(3356);
			FileInputStream file = new FileInputStream("/Users/Shayanyousefian/Documents/Eclipse_workspace/Audio-Streamer-Java/still.mp3");
                        CustomPlayer myplayer = new CustomPlayer();
                        myplayer.setPath("/Users/Shayanyousefian/Documents/Eclipse_workspace/Audio-Streamer-Java/still.mp3");
                        myplayer.play(200000);

                        //TimeUnit.SECONDS.sleep(3);

                        //myplayer.pause();
                        //TimeUnit.SECONDS.sleep(3);

                        //myplayer.resume();
//			Player plyr = new Player(file);
//                      AdvancedPlayer plr = new AdvancedPlayer();
//			plyr.play();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
 
}