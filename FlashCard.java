/*
 * Program: Flashcard APP
 * Author: Wenya Guo
 * Date: Aug 10, 2022
 * Class Description: this is the FlashCard class to define the flashcard and 
 * initiate the flashcard object. 
 * 
 */

public class FlashCard {

	private String question;
	private String answer;
	private boolean needReview;

	public FlashCard() {
		
	}

	public FlashCard (String question, String answer) {
		this.question = question;
		this.answer = answer;
		this.needReview = false;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public boolean getNeedReview() {
		return needReview;
	}

	public void setNeedReview(boolean needReview) {
		this.needReview = needReview;
	}

	@Override
	public String toString() {
		return this.question + "\n\n\n" + this.answer;
	}
	
}
