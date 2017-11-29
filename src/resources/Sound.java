package resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    private MediaPlayer mediaPlayer;
    public Sound(String path){
        Media sound = new Media(Sound.class.getResource(path).toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void play(){
        mediaPlayer.stop();
        mediaPlayer.play();
    }
}
