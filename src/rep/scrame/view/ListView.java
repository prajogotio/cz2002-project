package rep.scrame.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import rep.scrame.controller.SystemScannerAdapter;

/**
 * The list view representation of a list of informations.
 */
public abstract class ListView implements ScrameView{
	/**
	 * The page size of each table.
	 */
	private static final int PAGE_SIZE = 20;
	
	/**
	 * The constant for next page.
	 */
	private static final String NEXT_PAGE = "next";
	
	/**
	 * The constant for previous page.
	 */
	private static final String PREV_PAGE = "prev";
	
	/**
	 * The constant for end of view.
	 */
	private static final String END_VIEW = "end";
	
	/**
	 * The current page information.
	 */
	protected int currentPage;
	
	/**
	 * The current page index.
	 */
	protected int currentIndex = 0;
	
	/**
	 * The number of pages.
	 */
	protected int numPages;
	
	/**
	 * The list of elements.
	 */
	private ArrayList<Collection> listElements;
	
	/**
	 * the header string.
	 */
	protected String header;
	
	/**
	 * The line contents.
	 */
	protected String lineContents;
	
	/**
	 * The constructor of the ListView.
	 * @param a The list of elements.
	 */
	public ListView(ArrayList a){
		currentPage = 1;
		listElements = a;
	}
	
	/**
	 * Gets the current page.
	 * @return	Current page.
	 */
	public int getCurrentPage(){
		return currentPage;
	}
	
	/**
	 * Gets the page size.
	 * @return	The page size.
	 */
	public int getPageSize(){
		return PAGE_SIZE;
	}
	
	/**
	 * Gets the current index.
	 * @return	The current index.
	 */
	public int getCurrentIndex(){
		return currentIndex;
	}
	
	/**
	 * Gets the list of elements.
	 * @return	List of elements.
	 */
	public ArrayList getList(){
		return listElements;
	}
	
	
	@Override
	public void display(){
		System.out.println();
		numPages = calculateNoOfPages();
		currentPage = 1;
		
		if (numPages > 1){
		do {
			printPage();
			System.out.printf("\nDisplaying: Page %d (of %d) with %d elements per page.\n", currentPage,numPages,PAGE_SIZE);
		}
		while (!hasEnded());
		}
		else
			printPage();


	}
	
	/**
	 * Prompts user input.
	 * @return	User input.
	 */
	private String promptInput(){
		String input;
		Scanner sc = SystemScannerAdapter.getInstance();
		
		//if currently in middle of pages
		if (currentPage != 1 && currentPage != numPages) {
			System.out.printf("'%s' to display next page, '%s' to display previous page, '%s' to end list view\n", NEXT_PAGE, PREV_PAGE, END_VIEW);
		
			while (true) {
				input = sc.nextLine();
				if (! (input.matches(NEXT_PAGE) || input.matches(PREV_PAGE) || input.matches(END_VIEW) ) )
					System.out.printf("Invalid command! Available: '%s' (next page), '%s' (previous page), '%s' (end list view)\n", NEXT_PAGE, PREV_PAGE, END_VIEW);
				
				else {
					return input;
				}
			}
		}
		
		//else, if at first page
		else if (currentPage == 1){
			
				System.out.printf("'%s' to display next page, '%s' to end list view\n", NEXT_PAGE, END_VIEW);
				
				while (true) {
				input = sc.nextLine();
				if (! (input.matches(NEXT_PAGE) || input.matches(END_VIEW) ) )
					System.out.printf("Invalid command! Available: '%s' (next page), '%s' (end list view)\n", NEXT_PAGE, END_VIEW);
				
				else {
					return input;
				}
			}
		}
		
		//else if at last page
		else if (currentPage == numPages){
			
			System.out.printf("'%s' to display previous page, '%s' to end list view\n", PREV_PAGE, END_VIEW);
			
			while (true) {
				input = sc.nextLine();
				if (! (input.matches(PREV_PAGE) || input.matches(END_VIEW) ) )
					System.out.printf("Invalid command! Available: '%s' (previous page), '%s' (end list view)\n", PREV_PAGE, END_VIEW);
			
				else {	
					return input;
				}
			}
		}

		return "0";
	}

	/**
	 * Checks if the the activity has reached the end.
	 * @return	True if it is ended, false otherwise.
	 */
	private boolean hasEnded(){
		
		//only ask for page options if there exists more than 1 page
		if (numPages > 1){
			String input;
			input = promptInput();
		
			switch (input){
				case NEXT_PAGE:
					nextPage();
					return false;
				case PREV_PAGE:
					prevPage();
					return false;
				case END_VIEW:
					return true;
				default:
					return true;
			}
		}
		
		else
			return true;
				
	}
	
	/**
	 * Goes to the next page.
	 */
	private void nextPage() {
		if (currentPage == numPages){
			System.out.println("Cannot advance to next page: This is currently the last page");
		}
		else 
			currentPage++;
		}
	
	/**
	 * Goes to the previous page.
	 */
	private void prevPage() {
		if (currentPage == 1){
			System.out.println("Cannot move to previous page: This is currently the first page");
		}
		else 
			currentPage--;
		}

	
	/**
	 * Prints the current page.
	 */
	private void printPage(){
		header = formatHeader();
		System.out.println(header);
		String line;
		 for (currentIndex = (currentPage-1)*PAGE_SIZE; (currentIndex < currentPage*PAGE_SIZE) && (currentIndex < listElements.size()); currentIndex++){
			line = formatLine();
	          System.out.println(line); 
	     
	        }
	}
	
	/**
	 * Calculates the number of pages.
	 * @return Number of pages.
	 */
	private int calculateNoOfPages(){
		if (listElements.size() < PAGE_SIZE)
			return 1;
		
		else {
			double pageSize = (double)PAGE_SIZE;
			return (int)Math.ceil (listElements.size()/pageSize);
			
		}
	}
	
	
	/**
	 * Formats the header of the table.
	 * @return	String representation of the table.
	 */
	protected abstract String formatHeader();
	
	/**
	 * Formats the line of the table.
	 * @return	String representation of the table lines.
	 */
	protected abstract String formatLine();
	
}
