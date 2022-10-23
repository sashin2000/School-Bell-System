package BellSystem;

import BellSystem.sounds.PlaySound;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class AppController implements Initializable {

    ObservableList<BellRecord> records = FXCollections.observableArrayList();
    TreeItem<BellRecord> root;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTreeTableView bellTable;

    @FXML
    private Label lblMainPanelBackground;

    @FXML
    private Label lblTitle;

    @FXML
    public Label lblCurrentTime;

    @FXML
    private JFXTimePicker txtTime;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtIndex;

    @FXML
    private Label lblRemainingTime;

    @FXML
    private Label lblErrorMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        TreeTableColumn indexCol = new TreeTableColumn("Index");
        indexCol.setPrefWidth(100);
        TreeTableColumn bellNameCol = new TreeTableColumn("Bell Name");
        bellNameCol.setPrefWidth(250);
        TreeTableColumn bellTimeCol = new TreeTableColumn("Bell Time");
        bellTimeCol.setPrefWidth(150);

        bellTable.getColumns().addAll(indexCol,bellNameCol,bellTimeCol);



        records.add(new BellRecord("01","School Start", "02:26:25 AM"));
        records.add(new BellRecord( "02","Period 1","02:27:00 AM"));
        records.add(new BellRecord( "03","Period 2","02:27:12 AM"));
        records.add(new BellRecord( "04","Period 3","02:27:24 AM"));
        records.add(new BellRecord( "05","Period 4","02:27:32 AM"));
        records.add(new BellRecord( "06","Interval","02:27:48 AM"));
        records.add(new BellRecord( "07","Period 5","02:28:00 AM"));
        records.add(new BellRecord( "08","Period 6","02:28:12 AM"));
        records.add(new BellRecord( "09","Period 7","02:28:24 AM"));
        records.add(new BellRecord( "10","Period 8","02:28:32 AM"));
        records.add(new BellRecord( "11","School Over","02:28:48 AM"));
        records.add(new BellRecord( "12","Recording","02:29:10 AM"));

        indexCol.setCellValueFactory(new TreeItemPropertyValueFactory<BellRecord, String>("index"));
        bellNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<BellRecord, String>("bellName"));
        bellTimeCol.setCellValueFactory(new TreeItemPropertyValueFactory<BellRecord, String>("bellTime"));

        // build tree
        root = new RecursiveTreeItem<BellRecord>(records, RecursiveTreeObject::getChildren);

        bellTable.setRoot(root);
        bellTable.setShowRoot(false);

        initClock();


    }

    // Adding records for a period
    @FXML
    void onAdd(ActionEvent event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        if(txtIndex.getText().equals("")){
            lblErrorMsg.setText("Please insert an index");
            System.out.println("Please insert an index");
        }else if(txtName.getText().equals("")){
            lblErrorMsg.setText("Please insert a bell name");
            System.out.println("Please insert a bell name");
        }else if(txtTime.getValue() == null){
            lblErrorMsg.setText("Please insert a time");
            System.out.println("Please insert a time");
        }else{
            lblErrorMsg.setText("");
            records.add(new BellRecord( txtIndex.getText(),txtName.getText(),txtTime.getValue().format(DateTimeFormatter.ofPattern("hh:mm:ss a"))));
            Collections.sort(records);
            root = new RecursiveTreeItem<BellRecord>(records, RecursiveTreeObject::getChildren);
            bellTable.setRoot(root);
            bellTable.setShowRoot(false);
            /*System.out.println(txtIndex.getText());
            System.out.println(txtName.getText());
            System.out.println(txtTime.getValue().toString());*/
        }

        //txtIndex.getText();
        //txtName.getText();
        //txtTime.getValue().toString();

    }

    @FXML
    void onDelete(ActionEvent event) {
        try {
            lblErrorMsg.setText("");
            records.remove(bellTable.getSelectionModel().getSelectedIndex());
        }catch (ArrayIndexOutOfBoundsException e) {
            lblErrorMsg.setText("Please select a record");
        }catch (IndexOutOfBoundsException e){
            lblErrorMsg.setText("There are no more records");
        }
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
            Date date = new Date();
            //LocalTime currentTime = LocalTime.now();
            //lblCurrentTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            lblCurrentTime.setText(dateFormat.format(date));
            lblRemainingTime.setText("Remaining Time...\n");
            //System.out.println(Integer.parseInt(records.get(0).getIndex()) + Integer.parseInt(records.get(1).getIndex()));
            Collections.sort(records);


            try {
                String time1 = dateFormat.format(date);
                String time2 = records.get(0).getBellTime();

                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = format.parse(time1);
                    date2 = format.parse(time2);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                long difference = date2.getTime() - date1.getTime();

                //System.out.println("Live Time : "+date2.getTime() +" - "+ " Stable Time : "+ date1.getTime());


                long millis = difference;
                String hms = String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                //System.out.println(hms);
                lblRemainingTime.setText(lblRemainingTime.getText() + hms);


                if(millis < 1){

                    PlaySound ps = new PlaySound();
                    try {
                        if(records.get(0).getBellName().equals("School Start")){
                            ps.playTrack("src/BellSystem/sounds/Morning_Gatha.wav");
                        }else if(records.get(0).getBellName().equals("Recording")){
                            ps.playTrack("src/BellSystem/sounds/Chattamanawaka.wav");
                        }else{
                            ps.playTrack("src/BellSystem/sounds/Bell_Sound.wav");
                        }

                        records.remove(0);
                        Collections.sort(records);

                    } catch (IOException ioException) {
                        //ioException.printStackTrace();
                    } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                        //unsupportedAudioFileException.printStackTrace();
                    } catch (LineUnavailableException lineUnavailableException) {
                        //lineUnavailableException.printStackTrace();
                    } catch (IndexOutOfBoundsException f){

                    }
                }
            }catch (IndexOutOfBoundsException g){

            }




        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

}
