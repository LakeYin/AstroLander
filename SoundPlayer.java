import javax.swing.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;   

public class SoundPlayer{
  
  private JFXPanel fxPanel = new JFXPanel();
  private String url; 
  private Media media;
  private MediaPlayer mediaPlayer;
  
  public SoundPlayer(String url){
    this.url = url;
  }
  
  public void playSound(boolean loop){
    SoundPlayer soundPlayer = new SoundPlayer(url);
    Class player = soundPlayer.getClass();
    
    // player is the ClassLoader for the current class, ie. CurrentClass.class.getClassLoader();
    URL file = player.getResource(url);
    media = new Media(file.toString());
    mediaPlayer = new MediaPlayer(media);
    
    mediaPlayer.play();
    if(loop)
      mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
  }
  
  public void pause(){
    mediaPlayer.pause();
  }
  
  public void stop(){
    mediaPlayer.stop();
  }
}