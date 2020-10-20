package Code;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddController {
    static int count = 0;

    @FXML
    private TextField Word;

    @FXML
    private TextField Pronounce;

    @FXML
    private TextArea Explain;

    @FXML
    private Label Notification;

    @FXML
    private Button Submit;

    public void initialize() {
        handleSubmitButton();
    }

    public void handleSubmitButton() {
        Submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (Word.getText().isEmpty() && Pronounce.getText().isEmpty() && Explain.getText().isEmpty()) {
                    try {
                        Stage stage = (Stage) Submit.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Sample.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Dictionary");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        stage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (Word.getText().isEmpty() || Pronounce.getText().isEmpty() || Explain.getText().isEmpty()) {
                    Notification.setText("Bạn nhập thiếu dữ liệu!");
                } else {
                    try {
                        Stage stage = (Stage) Submit.getScene().getWindow();
                        String word;
                        String pronounce;
                        String explain;
                        word = Word.getText();
                        pronounce = Pronounce.getText();
                        explain = Explain.getText();
                        explain += "\n(Bổ sung bởi người dùng)";
                        Word newWord = new Word(word, pronounce, explain);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Sample.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Controller controller = fxmlLoader.<Controller>getController();
                        controller.add.add(newWord);
                        controller.refreshListView();
                        Scene scene = new Scene(root);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("Dictionary");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        stage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
}
