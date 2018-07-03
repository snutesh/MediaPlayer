/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author sNutesh
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField SEARCH_BOX;
    @FXML
    private Label NOW_PLAYING;
    @FXML
    private Label MY_MUSIC;
    @FXML
    private Label ADD_TO_PLAYLIST;
    @FXML
    private Label REMOVE_FROM_PLAYLIST;
    @FXML
    private Button PLAY_BUTTON;
    @FXML
    private Button NEXT_BUTTON;
    @FXML
    private Button REPEAT_BUTTON;
    @FXML
    private Button PREV_BUTTON;
    @FXML
    private ListView <String> CENTER_LIST_VIEW;
    @FXML
    private Label SONG_DISPLAY;
    @FXML
    private Button PAUSE_BUTTON;

    MusicPlayer MC;
    String song;
    public static int count;
    HashMap <String, String> hashmap;
    String currentSong;

    FileReader fileReader1;
    FileReader fileReader2;
    BufferedReader bufferedReader1;
    BufferedReader bufferedReader2;

    FileWriter fileWriter1;
    FileWriter fileWriter2;
    PrintWriter bufferedWriter1;
    PrintWriter bufferedWriter2;

    public FXMLDocumentController() {
        this.hashmap = new HashMap<>();
        this.MC = new MusicPlayer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fileReader1 = new FileReader("playlist.txt");
            fileReader2 = new FileReader("path.txt");
            if (fileReader1 != null && fileReader2 != null) {
                bufferedReader1 = new BufferedReader(fileReader1);
                bufferedReader2 = new BufferedReader(fileReader2);
                String s1 = bufferedReader1.readLine();
                String s2 = bufferedReader2.readLine();
                while (s1 != null && s2 != null) {
                    CENTER_LIST_VIEW.getItems().add(s1);
                    hashmap.put(s1, s2);
                    s1 = bufferedReader1.readLine();
                    s2 = bufferedReader2.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                fileReader1.close();
                fileReader2.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void SearchKeyReleased(KeyEvent event) {
    }

    @FXML
    private void NowPlayingClicked(MouseEvent event) {
        try {
            CENTER_LIST_VIEW.scrollTo(currentSong);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No Song is Currently Playing");
        }

    }

    @FXML
    private void MyMusicClicked(MouseEvent event) {
        CENTER_LIST_VIEW.scrollTo(0);
    }

    @FXML
    private void AddPlaylistClicked(MouseEvent event) {
        try {
            fileWriter1 = new FileWriter("playlist.txt",true);
            fileWriter2 = new FileWriter("path.txt",true);
            bufferedWriter1 = new PrintWriter(fileWriter1);
            bufferedWriter2 = new PrintWriter(fileWriter2);
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("C:\\Users\\sNutesh\\Music\\My Music\\"));
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3", "*.mpeg3"));
            List<File> selectedFiles = fc.showOpenMultipleDialog(null);
            if (selectedFiles != null) {
                for (int i = 0; i < selectedFiles.size(); i++) {
                    CENTER_LIST_VIEW.getItems().add(selectedFiles.get(i).getName());
                    hashmap.put(selectedFiles.get(i).getName(), selectedFiles.get(i).toURI().toString());
                    bufferedWriter1.println(selectedFiles.get(i).getName());
                    bufferedWriter2.println(selectedFiles.get(i).toURI().toString());
                }
            } else {
                JOptionPane.showMessageDialog(null, "No File Choosen");
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                fileWriter1.close();
                fileWriter2.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void RemovePlaylistClicked(MouseEvent event) {
        try {
            CENTER_LIST_VIEW.getItems().remove(CENTER_LIST_VIEW.getSelectionModel().getSelectedItem());
            hashmap.remove(CENTER_LIST_VIEW.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Please Selecet a Value");
        }

    }

    @FXML
    private void PlayButtonClicked(ActionEvent event) {
        song = hashmap.get(CENTER_LIST_VIEW.getSelectionModel().getSelectedItem());
        if (MC.player != null) {
            MC.Stop();
            MC.Play(song);
        } else {
            MC.Play(song);
        }
        SONG_DISPLAY.setText(CENTER_LIST_VIEW.getSelectionModel().getSelectedItem());
        currentSong = CENTER_LIST_VIEW.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void NextButtonClicked(ActionEvent event) {
        CENTER_LIST_VIEW.getSelectionModel().selectNext();
        PlayButtonClicked(event);
    }

    @FXML
    private void RepeatButtonClicked(ActionEvent event) {
        switch (count) {
            case 0:
                count = 1;
                break;
            case 1:
                count = 0;
                break;
        }
    }

    @FXML
    private void PrevButtonClicked(ActionEvent event) {
        CENTER_LIST_VIEW.getSelectionModel().selectPrevious();
        PlayButtonClicked(event);
    }

    @FXML
    private void PauseButtonClicked(ActionEvent event) {
        MC.Stop();
    }
}
