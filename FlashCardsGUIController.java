import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/*
 * Program: Flashcard APP
 * Author: Wenya Guo
 * Date: Aug 10, 2022
 * Class Description: this is the FlashCardsGUIController class to handle all 
 * the events which need for operating the APP. 
 * 
 */

public class FlashCardsGUIController {

	FlashCardsManager flashCardsManager = new FlashCardsManager();
	

	private FlashCard currentCard = null;
	private int currentCardIndex = 0;
    private int count = 0;

	@FXML
    private Tab studyTab;

	@FXML
    private Tab reviewTab;

	@FXML
    private Button nextButton;

	@FXML
    private Button addButton;

	@FXML
    private Button saveButton;

	@FXML
    private Button startButton;

	@FXML
    private CheckBox needReviewCheck;
	
    @FXML
    private Label answerLabel;

    @FXML
    private Label questionLabel;

	@FXML
    private Label reviewLabel;

	@FXML
    private Label countLabel;

	@FXML
    private TabPane tp1;

	@FXML
    private TextArea enterAnswerText;

    @FXML
    private TextArea enterQuestionText;

	@FXML
    private ListView<String> reviewList;

	@FXML
    private ListView<String> createList;

	


	//initialize the nextbutton to be disabled
	public void initialize() {
		nextButton.setDisable(true);
		needReviewCheck.setDisable(true);
	}

	//event handler for start button clicked

	public void startButtonClicked() {
		openFileChooser();
		resetNewSession();
	}

	//event handler for next button clicked
	public void nextButtonClicked() {	
		if (count == flashCardsManager.getFlashCardsListSize()) {
			endConfirmationAlert();
		} else {
			currentCardIndex ++;
			setQuestionPage();
			count ++;
			updateCountLabel();
		}	
	}

	//event handler for check answer lable clicked
	public void checkAnswerLabelClicked() {
		answerLabel.setText(currentCard.getAnswer());
	}

	//handle the review checkbox and create review card list
	
    public void needReviewChecked(ActionEvent event) {
		if (needReviewCheck.isSelected()) {
			flashCardsManager.addReviewCard(currentCard);

		} else {
			flashCardsManager.removeReviewCard(currentCard);
		}

    }

	//when review tab clicked, question list will be loaded
    public void reviewTabClicked() {
		reviewList.getItems().clear();
		for (int i = 0; i < flashCardsManager.getReviewCards().size(); i++) {
			reviewList.getItems().add("Question" + (i + 1));
		}

    }

	//when click the item in the quesion list, show content in the review label
	public void listItemClicked() {
		int index = reviewList.getSelectionModel().getSelectedIndex();
		reviewLabel.setText(flashCardsManager.getReviewCards().get(index)
		.toString());
	}

	//add the created flash card to the list view
	public void addButtonClicked() {
		

		if ("".equals(enterQuestionText.getText()) ||
				"".equals(enterAnswerText.getText())) {
			displayInputRequiredAlert();
		} else {
			flashCardsManager.addNewCard(enterQuestionText.getText(), 
			enterAnswerText.getText());
			createList.getItems().add("New Card" + (flashCardsManager.getNewCardsList().size()));
			enterQuestionText.setText("");
			enterAnswerText.setText("");	
		}

	}

	//save the created card list to a file
	public void saveButtonClicked() {
		FileChooser chooser = new FileChooser();
		File file = chooser.showSaveDialog(saveButton.getScene().getWindow());
		flashCardsManager.createOwnCards(file, flashCardsManager.getNewCardsList());
		createList.getItems().clear();
		
    }

	//method for open the file chooser window when click start button
	private void openFileChooser() {
		FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(startButton.getScene().getWindow());
		try {
			flashCardsManager.loadFlashCards(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	//method of setting question page for start and next button
	private void setQuestionPage() {

			currentCard = flashCardsManager.getFlashCard(currentCardIndex);
			questionLabel.setText(currentCard.getQuestion());
			answerLabel.setText("Click here to check answer!");
			needReviewCheck.setSelected(currentCard.getNeedReview());
		
	}

	//method of updating question count label
	private void updateCountLabel() {
		countLabel.setText(count + "/" + flashCardsManager.getFlashCardsListSize());
	}

	//method of showing ending confirmation alert after one round end
	private void endConfirmationAlert() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("End of this session");
			alert.setHeaderText("Congratulations! This round completed!");
			alert.setContentText("Do you want to retry or Exit");
			
			ButtonType button1 = new ButtonType("Retry");
			ButtonType button2 = new ButtonType("Exit");
			ButtonType button3 = new ButtonType("Reset");
			

			alert.getButtonTypes().setAll(button1, button2, button3);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == button1) {
				resetNewSession();
			} else if (result.get() == button2) {
				System.exit(0);
			} else {
				alert.close();
				flashCardsManager.clearCardList();
				openFileChooser();
				resetNewSession();
			}
	}

	//method for new session start
	private void resetNewSession() {
		currentCardIndex = 0;
		setQuestionPage();
		count = 1;
		updateCountLabel();
		startButton.setDisable(true);
		nextButton.setDisable(false);
		needReviewCheck.setDisable(false);
	}

	//method of showing alert when question/answer input is emplty
	private void displayInputRequiredAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Required");
        alert.setHeaderText("Question or answer should not be empty");
        alert.setContentText("Try again! ");
		alert.showAndWait();


	}

  


}
