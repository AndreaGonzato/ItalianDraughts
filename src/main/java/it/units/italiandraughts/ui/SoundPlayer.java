package it.units.italiandraughts.ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class SoundPlayer {

    private final MediaPlayer mediaPlayer;
    private static SoundPlayer soundPlayer;

    static SoundPlayer getSoundPlayer() {
        if (soundPlayer == null) {
            soundPlayer = new SoundPlayer();
        }
        return soundPlayer;
    }

    private SoundPlayer() {
        mediaPlayer = initMediaPlayer();
    }

    private MediaPlayer initMediaPlayer() {
        String path = "sounds" + File.separatorChar + "movePiece.mp3";
        URL resource = Objects.requireNonNull(getClass().getResource(path));
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
    }

    void playSound() {
        new Thread(() -> {
            mediaPlayer.play();
            mediaPlayer.seek(new Duration(0));
        }).start();
    }

}
