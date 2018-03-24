import java.util.Arrays;

/**
 * This class represents a library, which hold a collection of books. 
 * Patrons can register at the library to be able to check out books, if a copy of the requested book is available.
 */
public class Library {
	
	/* The maximal number of books this library can hold. **/
	Book[] bookArray;
	
	/*The maximal number of books this library allows a single patron to borrow at the same time. **/
	int borrowMaxAmount;
	
	/*The maximal number of registered patrons this library can handle**/
	Patron[] patronArray;
	
	/* The index of the next free slot for books in the library **/
	int freeBookSlots;
	
	/* The index of next empty patronArray **/
	int patronIndex;
	
	/*----=  Constructors  =-----*/
	
	/**
	 * 
	 * Creates a new library with the given parameters.
     * @param maxBookCapacity - The maximal number of books this library can hold.
     * @param maxBorrowedBooks - The maximal number of books this library allows a single patron to borrow at the same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
	*/
	Library(int maxBookCapacity, int maxBorrowedBooks,int maxPatronCapacity){
		bookArray = new Book[maxBookCapacity];
		borrowMaxAmount = maxBorrowedBooks;
		patronArray = new Patron[maxPatronCapacity];
		freeBookSlots = maxBookCapacity;
		patronIndex = maxPatronCapacity;
		
	}
	
	/*----=  Instance Methods  =-----*/
	
	/**
	 * Adds the given book to this library, if there is place available,
	 *  and it isn't already in the library.
	 *  @return a non-negative id number for the book if there was a spot and the book was successfully added,
	 *   or if the book was already in the library; a negative number otherwise.
	 */
	
 	int addBookToLibrary(Book book) {
 		if((!Arrays.asList(bookArray).contains(book)) &
 				(freeBookSlots > -1 )){
 			freeBookSlots -= 1;
 			bookArray[freeBookSlots] = book;
 		}
 		return freeBookSlots;
 	}
	
	/**
	 * @param The id to check.
	 * @return true if the given number is an id of some book in the library, false otherwise.
	 */
 	boolean isBookIdValid(int bookId) {
 		if ((bookArray.length > bookId) & (bookId > freeBookSlots)) {
 			return true;
 		}
 		else {
 			return false;
 		}
 	}
 	
 	/**
 	 * @param The book for which to find the id number.
 	 * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
 	 */
 	int getBookId(Book book) {
 		for(int x = (bookArray.length -1); x > freeBookSlots; x= x -1) {
 			if(bookArray[x] == book) {
 				return x;
 			}
 		}
 		return -1;
 	}
 	
 	/**
 	 * @param bookId The id number of the book to check.
 	 * @return true if the book with the given id is available, false otherwise.
 	 */
 	boolean isBookAvailable(int bookId) {
 		if((isBookIdValid(bookId)) & (bookArray[bookId].currentBorrowerId < 0)) {
 				return true;
 			}
		return false;
 	}
 	
 	/**
 	 * Registers the given Patron to this library, if there is a spot available.
 	 * @param patron - The patron to register to this library
 	 * @return a non-negative id number for the patron if there was a 
 	 * spot and the patron was successfully registered, a negative number otherwise.
 	 */
 	int registerPatronToLibrary(Patron patron) {
 		if(patronIndex > -1) {
 			patronIndex -= 1;
 			patronArray[patronIndex] = patron;	
 		}
 		return patronIndex;
 	}
 	
 	/**
 	 * @param patronId - The id to check.
 	 * @return  true if the given number is an id of a patron in the library, false otherwise.
 	 */
 	boolean isPatronIdValid(int patronId) {
 		if ((patronArray.length > patronId) & (patronId > patronIndex)) {
 			return true;
 		}
 		else {
 			return false;
 		}
 	}
 	/**
 	 * @param patron - The patron for which to find the id number.
 	 * @return  the non-negative id number of the given patron if he is registered to this library, 
 	 * -1 otherwise.
 	 */
 	int getPatronId(Patron patron) {
 		for(int x = patronArray.length -1;x > patronIndex; x -= 1) {
 			if (patronArray[x] == patron) {
 				return x;
 			}
 		}
 		return -1;
 	}
 	
 	/**
 	 * Marks the book with the given id number as borrowed by the patron with the given patron id,
 	 *  if this book is available,
 	 *  the given patron isn't already borrowing the maximal number of books allowed, 
 	 *  and if the patron will enjoy this book.
 	 *  @param bookId - The id number of the book to borrow.
 	 *  @param patronId - The id number of the patron that will borrow the book.
 	 *  @return true if the book was borrowed successfully, false otherwise.
 	 */
 	boolean borrowBook(int bookId,int patronId) {
 		if (isBookIdValid(bookId) & isPatronIdValid(patronId) & 
 				// checks if the patron can borrow according to the library rules
 				(patronArray[patronId].getBorrowedBooks() < borrowMaxAmount) &
 				// checks if the patron will enjoy the book
 				patronArray[patronId].willEnjoyBook(bookArray[bookId]) &
 				// checks if book is not borrowed
 				(bookArray[bookId].currentBorrowerId == -1)){
 			patronArray[patronId].changeBorrowedBooks(false);
 			bookArray[bookId].currentBorrowerId = patronId;
 			return true;
 		}
 		return false;
 	}
 	
 	/**
 	 * returns the given book
 	 * @param bookId - The id number of the book to return.
 	 */
 	void returnBook(int bookId) {
 		if (isBookIdValid(bookId) & bookArray[bookId].currentBorrowerId != -1) {
 			patronArray[bookArray[bookId].currentBorrowerId].changeBorrowedBooks(true);
 			bookArray[bookId].returnBook();
 		}
 	}
 	/**
 	 * Suggest the patron with the given id the book he will enjoy the most, 
 	 * out of all available books he will enjoy, if any such exist.
 	 * @param patronId - The id number of the patron to suggest the book to.
 	 * @return The available book the patron with the given will enjoy the most. Null if no book is available.
 	 */
 	Book suggestBookToPatron(int patronId) {
 		if(isPatronIdValid(patronId)) {
 			Book bestSuggestion = null;
 			for(int x = bookArray.length -1; x > freeBookSlots; x -= 1) {
 				if((bookArray[x].currentBorrowerId == -1) & 
 					((bestSuggestion == null) ||
 					(patronArray[patronId].getBookScore(bookArray[x]) >
 					patronArray[patronId].getBookScore(bestSuggestion)))) {
 					bestSuggestion = bookArray[x];
 				}	
 			}
 			if(patronArray[patronId].willEnjoyBook(bestSuggestion)) {
 				return bestSuggestion;
 			}
 		}
 		return null;
 	}
 	
 	
 	
 	
}