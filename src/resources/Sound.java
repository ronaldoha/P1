package resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    public static void play(String path){
        Media sound = new Media(Sound.class.getResource(path).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.stop();
        mediaPlayer.play();
    }
}
