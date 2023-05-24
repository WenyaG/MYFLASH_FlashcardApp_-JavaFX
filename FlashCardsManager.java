import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Program: Flashcard APP
 * Author: Wenya Guo
 * Date: Aug 10, 2022
 * Class Description: this is the FlashCardsManager class to implement the logic
 * of managing the flashcards which will be used in controller class, includes 
 * loading files, geting specific card, getting list size, adding or removing cards from
 * related list, creating card and write it into file, clearing card list. 
 * 
 */

public class FlashCardsManager {


	private List<FlashCard> flashCardList = new ArrayList<FlashCard>();
	private List<FlashCard> reviewCards = new ArrayList<FlashCard>();
	private List<FlashCard> newCardsList = new ArrayList<FlashCard>();

	//method for load the file from hard disk
	public void loadFlashCards(File file) throws FileNotFoundException {

		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				String[] cardString = scan.nextLine().split("@@@");
				FlashCard flashCard = new FlashCard(cardString[0], cardString[1]);
				flashCardList.add(flashCard);	
			}
		} catch (FileSystemNotFoundException e) {
			System.out.println("Can't find flashcard file");
		}

	}


	public FlashCard getFlashCard(int index) {
		return flashCardList.get(index);
	}

	public int getFlashCardsListSize() {		
		return flashCardList.size();
	}


	public List<FlashCard> getReviewCards() {
		
		return reviewCards;
	}

	public List<FlashCard> getNewCardsList() {	
		return newCardsList;
	}

	public void clearCardList() {
		flashCardList.clear();
	}


	public void addReviewCard(FlashCard flashcard) {
		flashcard.setNeedReview(true);
		reviewCards.add(flashcard);
	}

	public void removeReviewCard(FlashCard flashcard) {
		reviewCards.remove(flashcard);
		flashcard.setNeedReview(false);
	}


	public void addNewCard (String question, String answer) {
		FlashCard newCard = new FlashCard(question, answer);
		newCardsList.add(newCard);
	}

	//method for writing created card to file
	public void createOwnCards (File file, List<FlashCard> newCardList) {
		try(PrintWriter printWriter = new PrintWriter(file);) {
			for (FlashCard newCard: newCardList) {
				printWriter.println(newCard.getQuestion() + "@@@" +
				newCard.getAnswer());
			}
		} catch (Exception e) {
			System.out.println("Error, file not saved");
		}
	}
	
}
