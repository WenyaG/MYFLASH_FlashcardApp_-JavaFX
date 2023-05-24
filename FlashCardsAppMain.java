import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Program: Flashcard APP
 * Author: Wenya Guo
 * Date: Aug 10, 2022
 * Program Description: This Flashcard APP includes the basic flashcard function.
 * Besides that, you can add any card in to review list and review them later.
 * You can also create your own cards and save them to a file. It also supports 
 * the function of user choosing flashcard file to study.
 * Class Description: this is the main class of this application. 
 * 
 */


public class FlashCardsAppMain extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource
		("FlashCardsGUI.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("My Flashcards");
		primaryStage.show();
		
	}
}