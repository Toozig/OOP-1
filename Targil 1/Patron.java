/*
 * This class represents a library patron that has a name and assigns values to different literary aspects of books.
 */
public class Patron {
	
	/** The Patron's first name*/
	final String firstName;
	
	/** The Patron's last name */
	final String lastName;
	
	/** The patron's comic tendency */
	int patronComicTendency;
	
	/** The patron's dramatic tendency */
	int patronDramaticTendency;
	
	/** The patron's educational tendency */
	int patronEducationalTendency;
	
	/** The patron's enjoyment threshold */
	int enjoymentThreshold;
	
	/** The number of books which the patron borrowed */
	int borrowedBooks = 0;
	
	
	 /*----=  Constructors  =-----*/
	   
	   /**
	    * Creates a new Patron with the given characteristic.
	    * @param patronFirstName The patron's first name.
	    * @param patronLastName The patron's last name.
	    * @param comicTendency The weight the patron assigns to the comic aspects of books
	    * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books.
	    * @param educationalTendency The weight the patron assigns to the educational aspects of books.
	    * @param patronEnjoymentThreshold he minimal literary value a book must have for this patron to enjoy it.
	    */
	Patron(String patronFirstName, String patronLastName, int comicTendency,int dramaticTendency,
			int educationalTendency, int patronEnjoymentThreshold) {
		firstName = patronFirstName;
		lastName = patronLastName;
		patronComicTendency = comicTendency;
		patronDramaticTendency = dramaticTendency;
		patronEducationalTendency = educationalTendency;
		enjoymentThreshold = patronEnjoymentThreshold;
		
	}
	
	/*----=  Instance Methods  =-----*/
	
	/**
	 * Returns a string representation of the patron,
	 *  which is a sequence of its first and last name, separated by a single white space.
	 *  For example, if the patron's first name is "Ricky" and his last name is "Bobby",
	 *   this method will return the String "Ricky Bobby".
	 *   @return the String representation of the patron.
	 */
	String stringRepresentation(){
		return firstName + " " + lastName; 
	}
	
	/**
	 * @return the literary value the patron assigns to a given book.
	 */
	int getBookScore(Book book) {
		return (book.comicValue * patronComicTendency) + (book.dramaticValue * patronDramaticTendency)
				+ (book.educationalValue * patronEducationalTendency);
	}
	
	/**
	 * @return true of this patron will enjoy the given book, false otherwise.
	 */
	boolean willEnjoyBook(Book book) {
		if(getBookScore(book) > enjoymentThreshold) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * increase or decrease the amount of borrowed books
	 * @param isReturning - Boolean, true if  patron return a book, false otherwise
	 */
	void changeBorrowedBooks(boolean isReturning) {
		if(isReturning) {
			borrowedBooks -=1;
		}
		else {
			borrowedBooks += 1;
		}
	}

	public int getBorrowedBooks() {
		return borrowedBooks;
	}
}
