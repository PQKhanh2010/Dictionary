package Code;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.event.*;

public class Controller extends DictionaryManagement {
    @FXML
    private ListView<Word> wordResult;

    @FXML
    private TextField wordSearch;

    @FXML
    private TextArea wordDetails;

    @FXML
    private Button searchButton;

    @FXML
    private Button infoButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Word> listOfWords = FXCollections.observableArrayList();
    public static ArrayList<Word> add = new ArrayList<>();
    public static ArrayList<String> deletedWord = new ArrayList<>();

    public void initialize() {
        insertFromFile();
        loadedData.sort(null);
        for (int i = 0; i < loadedData.size(); i++)
            listOfWords.add(loadedData.get(i));
        wordResult.setItems(listOfWords);
        handleSearchButton();
        showDetails();
        showSearchResult();
        handleAddButton();
        handleDeleteButton();
    }

    /**
     * Hiển thị phát âm và nghĩa
     */
    public void showDetails() {
        try {
            wordResult.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Word>() {
                @Override
                public void changed(ObservableValue<? extends Word> observable, Word oldValue, Word newValue) {
                    if (newValue != null) {
                        wordDetails.setText(newValue.getWord_target() + "\n" + newValue.getWord_pronounce() + "\n" + newValue.getWord_explain());
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * hiển thị danh sách từ gần giống
     */
    public void showSearchResult() {
        try {
            wordSearch.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<Word> searchResult = dictionarySearch(wordSearch.getText());
                    listOfWords = FXCollections.observableList(searchResult);
                    wordResult.setItems(listOfWords);
                }
            });
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * chọn từ đầu tiên
     */
    @FXML
    public void handleEnterButton(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER && !listOfWords.isEmpty()) {
            wordResult.getSelectionModel().clearAndSelect(0);
        }
    }

    /**
     * thiết lập button search giống như enter
     */
    public void handleSearchButton() {
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                wordResult.getSelectionModel().clearAndSelect(0);
            }
        });
    }

    /**
     * button thêm từ
     */
    public void handleAddButton() {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Stage s2 = (Stage) addButton.getScene().getWindow();
                    Parent root2 = FXMLLoader.load(getClass().getResource("/FXML/Add.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dictionary");
                    stage.setScene(new Scene(root2, 350, 220));
                    s2.close();
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    /**
     * button xóa từ
     */
    public void handleDeleteButton() {
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage s2 = (Stage) deleteButton.getScene().getWindow();
                    Parent root3 = FXMLLoader.load(getClass().getResource("/FXML/Delete.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Dictionary");
                    stage.setScene(new Scene(root3, 400, 70));
                    s2.close();
                    stage.show();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    public void deleteAddedWord() {
        for (int j = 0; j < deletedWord.size(); j++) {
            for (int i = 0; i < loadedData.size(); i++) {
                if (loadedData.get(i).getWord_target().equals(deletedWord.get(j))) {
                    loadedData.remove(i);
                    i--;
                }
            }
        }
    }

    public void refreshListView() {
        loadedData.addAll(add);
        deleteAddedWord();
        loadedData.sort(null);
        listOfWords = FXCollections.observableList(loadedData);
        wordResult.setItems(listOfWords);
        wordResult.refresh();
    }
}
