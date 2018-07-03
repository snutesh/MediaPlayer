/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author sNutesh
 */
public class MusicPlayer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Music Player");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public Media media;
    MediaPlayer player;

    public void Play(String path) {
        media = new Media(path);
        player = new MediaPlayer(media);
        player.play();
    }

    public void Stop() {
        if (player != null) {
            player.stop();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
