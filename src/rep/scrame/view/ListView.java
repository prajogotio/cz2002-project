package rep.scrame.view;

import java.util.*;

import rep.scrame.controller.InformationManager;
import rep.scrame.model.Student;

public abstract class ListView implements ScrameView{
	private static final int PAGE_SIZE = 20;
	protected int currentPage, currentIndex = 0, numPages;
	private static final String NEXT_PAGE = "next", PREV_PAGE = "prev", END_VIEW = "end";
	private ArrayList<Collection> listElements;
	protected String header;
	protected String lineContents;
	
	public ListView(ArrayList a){
		currentPage = 1;
		listElements = a;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	
	public int getPageSize(){
		return PAGE_SIZE;
	}
	
	public int getCurrentIndex(){
		return currentIndex;
	}
	
	public ArrayList getList(){
		return listElements;
	}
	
	
	
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
	
	private String promptInput(){
		String input;
		Scanner sc = new Scanner(System.in);
		
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
	
	private void nextPage() {
		if (currentPage == numPages){
			System.out.println("Cannot advance to next page: This is currently the last page");
		}
		else 
			currentPage++;
		}
	
	private void prevPage() {
		if (currentPage == 1){
			System.out.println("Cannot move to previous page: This is currently the first page");
		}
		else 
			currentPage--;
		}

	
	private void printPage(){
		header = formatHeader();
		System.out.println(header);
		String line;
		 for (currentIndex = (currentPage-1)*PAGE_SIZE; (currentIndex < currentPage*PAGE_SIZE) && (currentIndex < listElements.size()); currentIndex++){
			line = formatLine();
	          System.out.println(line); 
	     
	        }
	}
	
	private int calculateNoOfPages(){
		if (listElements.size() < PAGE_SIZE)
			return 1;
		
		else {
		double pageSize = (double)PAGE_SIZE;
		return (int)Math.ceil (listElements.size()/pageSize);

		}
	}
	
	
	
	protected abstract String formatHeader();
	protected abstract String formatLine();
	
}
