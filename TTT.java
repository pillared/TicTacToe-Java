/**
 * Tic Tac Toe!
 * Homework 6 - Extra Cred
 * Created by Anthony Asilo
 * aasilo1@student.gsu.edu
 */
import java.util.*;

public class TTT {
	
	//instance vars
	private String[][] board; //{{"_","_","_"},{"_","_","_"},{"_","_","_"}};
	private int userWins;
	private int cpuWins;
	private int draws;
	private String userType;
	private String cpuType; 
	private boolean filled;
	private boolean isFirst;
	private int cpuMoveCount;
	String loc;
	String YoN;
	String again;
	
	//constructor
	public TTT() {
		board = new String[3][3];
		userWins = 0;
		cpuWins = 0;
		draws = 0;
		userType = "";
		cpuType = "";
		filled = false;
		isFirst = false;
		cpuMoveCount=0;
		YoN = "";
		again = "";
	}
	
	//reset the board values each game.
	public void reset() {
		userType = "";
		cpuType = "";
		filled = false;
		isFirst = false;
		cpuMoveCount=0;
		YoN = "";
		again = "";
	}

	//program runner!
	public void run() {
			do {
			reset();	
			setBoard();
			Scanner userInput = new Scanner(System.in); //scanner
			print();
			System.out.println("Would you like to be 'X' or 'O'?");				
			userType = userInput.nextLine();
			setUserType(userType);
			System.out.println("Would you like to go first? Please type 'yes' or 'no'");
			String YoN = userInput.nextLine();
			firstPlay(YoN);
			if(!isFirst) {
				for(int i=0; i<8; i++) {
					if(i == 0) {
						cpuFirstAdd();
					}
					else if(i % 2 == 0 && i > 0) {
						cpuAdd();
						System.out.println();
						System.out.println("Computer has placed it's "+ cpuType + " on the board.");
						System.out.println("It is now your turn.");
						System.out.println();
					}
					else if(i % 2 == 1) {
						System.out.println("Type a coordinate to add: ex. A1 or C2");
						String loc = userInput.nextLine();
						if(!locSearch(loc)) {
							System.out.println("Unidentified Coordinate: Please try again");
							i--;
							continue;
						}
						else if(locSearch(loc)) {
							if(isCellEmpty(loc)) {
								add(loc);
							}
							else if (!isCellEmpty(loc)){
								System.out.println("There is already a value set at " + loc + "!");
								i--;
								continue;
							}
						}
					}
					print();
					String str = checkIfWin();
					if(!str.equalsIgnoreCase("!F")) {
						i=9;
						if(str.equalsIgnoreCase(userType)) {
							printWinner(1);
							break;
						}
						else if (str.equalsIgnoreCase(cpuType)) {
							printWinner(0);
							break;
						}
						else {
							printWinner(2);
							break;
						}
					}
				}
				
				while(checkIfWin().equalsIgnoreCase("!F")) {
					cpuAdd();
					print();
					String str = checkIfWin();
					if(!str.equalsIgnoreCase("!F")) {
						if(str.equalsIgnoreCase(userType)) {
							printWinner(1);
						}
						else if (str.equalsIgnoreCase(cpuType)) {
							printWinner(0);
						}
						else
							printWinner(2);
					}
				}
				
			}
			else if(isFirst) {
				for(int i=0; i<8; i++) {
					if(i % 2 == 0) {
						System.out.println("Type a coordinate to add: ex. A1 or C2");
						String loc = userInput.nextLine();
						if(!locSearch(loc)) {
							System.out.println("Unidentified Coordinate: Please try again");
							i--;
							continue;
						}
						else if(locSearch(loc)) {
							if(isCellEmpty(loc)) {
								add(loc);
							}
							else if (!isCellEmpty(loc)){
								System.out.println("There is already a value set at " + loc + "!");
								i--;
								continue;
							}
						}
					}
					else if(i % 2 == 1) {
						cpuAdd();
						System.out.println();
						System.out.println("Computer has placed it's "+ cpuType + " on the board.");
						System.out.println("It is now your turn.");
						System.out.println();
					}
					print();
					String str = checkIfWin();
					if(!str.equalsIgnoreCase("!F")) {
						i=10;
						if(str.equalsIgnoreCase(userType)) {
							printWinner(1);
						}
						else if (str.equalsIgnoreCase(cpuType)) {
							printWinner(0);
						}
						else
							printWinner(2);
					}
					
				}
				while(checkIfWin().equalsIgnoreCase("!F")) {
					System.out.println("Type a coordinate to add: ex. A1 or C2");
					String loc = userInput.nextLine();
					if(!locSearch(loc)) {
						System.out.println("Unidentified Coordinate: Please try again");
						continue;
					}
					else if(locSearch(loc)) {
						if(isCellEmpty(loc)) {
							add(loc);
							print();
							String str = checkIfWin();
							if(!str.equalsIgnoreCase("!F")) {
								if(str.equalsIgnoreCase(userType)) {
									printWinner(1);
								}
								else if (str.equalsIgnoreCase(cpuType)) {
									printWinner(0);
								}
								else
									printWinner(2);
							}
						}
						else if (!isCellEmpty(loc)){
							System.out.println("There is already a value set at " + loc + "!");
							continue;
						}
					}
				}
				
			}
			System.out.println();
			System.out.println("Computer: " + cpuWins + " wins.");
			System.out.println("Player: " + userWins + " wins.");
			System.out.println("Draws: " + draws + " wins.");
			System.out.println();
			System.out.println("Would you like to play again?");
			again = userInput.nextLine();
		} while(again.equalsIgnoreCase("yes") || again.equalsIgnoreCase("y"));
	}
	
	//initializes board to letter N instead of Null;
	private void setBoard() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				board[i][j] = "N";
			}
		}
	}

	//adds players type to specific location
	public void add(String l) {
		if(locSearch(l)) {
			if(isCellEmpty(l)) {
				String letter = l.substring(0, 1); //gets the letter from the location (ex. A1 gets A)
				letter = letter.toUpperCase();
				int parsedLetter = -1; // Converts A to 0, B to 1, and C to 2 
				int num = Integer.parseInt(l.substring(1)); //gets the number from the location (ex. A1 gets 1)
				if(letter.compareTo("A")==0) {
					parsedLetter = 0;
				}
				else if(letter.compareTo("B")==0) {
					parsedLetter = 1;
				}
				else if(letter.compareTo("C")==0) {
					parsedLetter = 2;
				}
				board[parsedLetter][num-1] = userType;
			}
		}
		else
			System.out.println("Add error. Try Again");
	}
	
	//adds cpu's type to specific location (based on cpu algorithm)
	public void cpuAdd() {
		if(cpuMoveCount == 0) {
			String filler = "";
			Random row = new Random();
			int r = row.nextInt(2);
			switch(r) {
				case 0:
					filler += "A";
					break;
				case 1:
					filler += "B";
					break;
				case 2:
					filler += "C";
					break;
			}
			Random column = new Random();
			int c = column.nextInt(2);
			c+=1;
			filler += c;
			if(isCellEmpty(filler)) {
				board[r][c] = cpuType;
				cpuMoveCount++;
			}
			else if (!isCellEmpty(filler)) {
				cpuAdd();
			}
		}
		//else if(!cpuOffense()) {
		//	boolean NOT_USED = cpuDefense();
		//}
		else {
			boolean NOT_USED = cpuOffense();
		}
	}
	
	//adds cpu's type to specific location on cpu's second turn, only if cpu went first or if . returns true c
	public boolean cpuOffense() {
		//if(cpuMoveCount >= 2) {
			for(int i=1; i<9; i++) {
				switch(i) {
					case 1: 
						//checks 1st row if there is 2 in a row of cpuType
						//if the above occurs, place cpuType accordingly to win
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[0][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A3")){
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[0][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A2")) {
							board[0][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][1].equalsIgnoreCase(cpuType)
						&& board[0][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 2: 
						//checks 2nd row if there is 2 in a row of cpuType
						//if the above occurs, place cpuType accordingly to win
						if(board[1][0].equalsIgnoreCase(cpuType)
						&& board[1][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B3")) {
							board[1][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][0].equalsIgnoreCase(cpuType)
						&& board[1][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][1].equalsIgnoreCase(cpuType)
						&& board[1][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B1")) {
							board[1][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 3: 
						//checks 3rd row if there is 2 in a row of cpuType
						//if the above occurs, place cpuType accordingly to win
						if(board[2][0].equalsIgnoreCase(cpuType)
						&& board[2][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[2][0].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C2")) {
							board[2][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[2][1].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 4: 
						//checks 1st column if there is 2 in a row of userType
						//if the above occurs, place cpuType accordingly to win
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[1][0].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[2][0].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B1")) {
							board[1][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][0].equalsIgnoreCase(cpuType)
						&& board[2][0].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 5: 
						//checks 2nd columnif there is 2 in a row of userType
						//if the above occurs, place cpuType accordingly to win
						if(board[0][1].equalsIgnoreCase(cpuType)
						&& board[1][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C2")) {
							board[2][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][1].equalsIgnoreCase(cpuType)
						&& board[2][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][1].equalsIgnoreCase(cpuType)
						&& board[2][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A2")) {
							board[0][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 6: 
						//checks 3rd column if there is 2 in a row of userType
						//if the above occurs, place cpuType accordingly to win
						if(board[0][2].equalsIgnoreCase(cpuType)
						&& board[1][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][2].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B3")) {
							board[1][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][2].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A3")) {
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 7:
						//checks Diagonal #1 [/] if there is 2 in a row of userType
						//if the above occurs, place cpuType accordingly to win
						if(board[2][0].equalsIgnoreCase(cpuType)
						&& board[1][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A3")) {
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[2][0].equalsIgnoreCase(cpuType)
						&& board[0][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][2].equalsIgnoreCase(cpuType)
						&& board[1][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
					case 8: 
						//checks Diagonal #2 [\] if there is 2 in a row of userType
						//if the above occurs, place cpuType accordingly to win
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[1][1].equalsIgnoreCase(cpuType)
						&& isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[0][0].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
						if(board[1][1].equalsIgnoreCase(cpuType)
						&& board[2][2].equalsIgnoreCase(cpuType)
						&& isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
						continue;
				}
				break;
			}	
		//}
		if(!cpuDefense()) {
			if(cpuMoveCount >= 1) {
				for(int i=1; i<9; i++) {
					switch(i) {
						case 1:
							//checks 1st row to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[0][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("A2")) {
									board[0][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[0][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("A3")) {
									board[0][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A1")) {
									board[0][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[0][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("A2")) {
									board[0][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 2:
							//checks 2nd row to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[1][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B3")) {
									board[1][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("B1")) {
									board[1][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 3:
							//checks 3rd row to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[2][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C2")) {
									board[2][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C3")) {
									board[2][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("C1")) {
									board[2][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C2")) {
									board[2][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 4:
							//checks 1st column to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[0][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B1")) {
									board[1][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C1")) {
									board[2][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A1")) {
									board[0][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B1")) {
									board[1][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 5:
							//checks 2nd column to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[0][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C2")) {
									board[2][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A2")) {
									board[0][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 6:
							//checks 3rd column to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[0][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B3")) {
									board[1][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C3")) {
									board[2][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A3")) {
									board[0][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B3")) {
									board[1][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 7:
							//checks Diagonal #1 [/] to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[2][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C1")) {
									board[2][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A3")) {
									board[0][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[0][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
						case 8:
							//checks Diagonal #1 [/] to see if there is one cpuType
							//if the above occurs, place cpuType accordingly to get 2 in a row
							if(board[0][0].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[1][1].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("C3")) {
									board[2][2] = cpuType;
									cpuMoveCount++;
									return true;
								}
								else if(isCellEmpty("A1")) {
									board[0][0] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							if(board[2][2].equalsIgnoreCase(cpuType)) {
								if(isCellEmpty("B2")) {
									board[1][1] = cpuType;
									cpuMoveCount++;
									return true;
								}
							}
							continue;
					}
				}
			}
		}
		return false;
	}
	
	//randomly picks a spot on the board and puts the CPU's type on the spot
	public void cpuFirstAdd() {
		Random row = new Random();
		int r = row.nextInt(2);
		Random column = new Random();
		int c = column.nextInt(2);
		board[r][c] = cpuType;
		cpuMoveCount++;
	}
	
	//algorithm to have the CPU find location to block player from winning, return true if placed value to block
	public boolean cpuDefense() {
		for(int i=1; i<9; i++) {
			switch(i) {
				case 1: //checks 1st row if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[0][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A3")) {
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[0][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A2")) {
							board[0][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][1].equalsIgnoreCase(userType)
					&& board[0][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 2: //checks 2nd row if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[1][0].equalsIgnoreCase(userType)
					&& board[1][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B2")) {
							board[1][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][0].equalsIgnoreCase(userType)
					&& board[1][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][1].equalsIgnoreCase(userType)
					&& board[1][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B1")) {
							board[1][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 3: //checks 3rd row if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[2][0].equalsIgnoreCase(userType)
					&& board[2][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[2][0].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C2")) {
							board[2][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[2][1].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 4: //checks 1st column if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[1][0].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[2][0].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B1")) {
							board[1][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][0].equalsIgnoreCase(userType)
					&& board[2][0].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 5: //checks 2nd column if there is 2 in a row of userType
					//if so, cpu places cpuType accordingly to block player
					if(board[0][1].equalsIgnoreCase(userType)
					&& board[1][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C2")) {
							board[2][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][1].equalsIgnoreCase(userType)
					&& board[2][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][1].equalsIgnoreCase(userType)
					&& board[2][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A2")) {
							board[0][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 6: //checks 3rd column if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[0][2].equalsIgnoreCase(userType)
					&& board[1][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][2].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B3")) {
							board[1][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][2].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A3")) {
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 7: //checks Diagonal #1 [/] if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[2][0].equalsIgnoreCase(userType)
					&& board[1][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A3")) {
							board[0][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[2][0].equalsIgnoreCase(userType)
					&& board[0][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][2].equalsIgnoreCase(userType)
					&& board[1][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C1")) {
							board[2][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
				case 8: //checks Diagonal #2 [\] if there is 2 in a row of userType
						//if so, cpu places cpuType accordingly to block player
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[1][1].equalsIgnoreCase(userType)) {
						if(isCellEmpty("C3")) {
							board[2][2] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[0][0].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("B2")) {
							board[1][1] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}
					if(board[1][1].equalsIgnoreCase(userType)
					&& board[2][2].equalsIgnoreCase(userType)) {
						if(isCellEmpty("A1")) {
							board[0][0] = cpuType;
							cpuMoveCount++;
							return true;
						}
					}	
					continue;
			}
		}
		return false;
	}
	
	//checks if location already is filled or not. 
	public boolean isCellEmpty(String l) {
		if(locSearch(l)) {
			String letter = l.substring(0, 1); //gets the letter from the location (ex. A1 gets A)
			letter = letter.toUpperCase();
			int parsedLetter = -1; // Converts A to 0, B to 1, and C to 2 
			int num = Integer.parseInt(l.substring(1)); //gets the number from the location (ex. A1 gets 1)
			switch(letter) {
				case "A":
					parsedLetter = 0;
					break;
				case "B":
					parsedLetter = 1;
					break;
				case "C":
					parsedLetter = 2;
					break;
			}
			try {
				 if(board[parsedLetter][num-1].equals("N")) {
						return true;
				 }
				 else {
					 return false;
				 }
			} catch (IndexOutOfBoundsException e) {
			  System.out.println("parsedLetter Error");
			}
		}
		return false;
	}
	
	//checks each row, column, and diag for a full row of the same type
	public String checkIfWin() {
		int checker=0;
		String type = "";
		for(int j=0; j<2; j++) {
			if(j == 0) {
				type = userType;
			}
			else if(j == 1) {
				type = cpuType;
			}
			if(isFilled()) {
				for(int i=1; i<9; i++) {
					switch(i) {
						case 1: 
							//checks 1st row if there is 3 of the same type 
							checker=0;
							for(int a=0; a<3; a++){
								if(board[0][a].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 2: 
							//checks 2nd row if there is 3 of the same type 
							checker=0;
							for(int a=0; a<3; a++){
								if(board[1][a].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 3: 
							//checks 3rd row if there is 3 of the same type
							checker=0;
							for(int a=0; a<3; a++){
								if(board[2][a].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 4: 
							//checks 1st column if there is 3 of the same type
							checker=0;
							for(int a=0; a<3; a++){
								if(board[a][0].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 5: 
							//checks 2nd column if there is 3 of the same type
							checker=0;
							for(int a=0; a<3; a++){
								if(board[a][1].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 6: 
							//checks 3rd column if there is 3 of the same type
							checker=0;
							for(int a=0; a<3; a++){
								if(board[a][2].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
						case 7: 
							//checks Diagonal #1 [/] if there is 3 of the same type 
							checker=0;
							int k = 0;
							for(int a=2; a >= 0; a--) {
								if(board[a][k].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
								k++;
								
							}
							continue;
						case 8: 
							//checks Diagonal #2 [\] if there is 3 of the same type 
							checker=0;
							for(int a=0; a<3; a++) {
								if(board[a][a].equalsIgnoreCase(type)) {
									checker++;
									if(checker == 3) {
										checker=0;
										return type;
									}
								}
							}
							continue;
					}
				}
				return "Draw!";
			}
			else if (!isFilled()) {
				if (board[0][0].equalsIgnoreCase(type) && board[0][1].equalsIgnoreCase(type)
						&& board[0][2].equals(type)) {
					return type;
				} else if (board[1][0].equalsIgnoreCase(type) && board[1][1].equalsIgnoreCase(type)
						&& board[1][2].equals(type)) {
					return type;
				} else if (board[2][0].equalsIgnoreCase(type) && board[2][1].equalsIgnoreCase(type)
						&& board[2][2].equals(type)) {
					return type;
				} else if (board[0][0].equalsIgnoreCase(type) && board[1][0].equalsIgnoreCase(type)
						&& board[2][0].equals(type)) {
					return type;
				} else if (board[0][1].equalsIgnoreCase(type) && board[1][1].equalsIgnoreCase(type)
						&& board[2][1].equals(type)) {
					return type;
				} else if (board[0][2].equalsIgnoreCase(type) && board[1][2].equalsIgnoreCase(type)
						&& board[2][2].equals(type)) {
					return type;
				} else if (board[2][0].equalsIgnoreCase(type) && board[1][1].equalsIgnoreCase(type)
						&& board[0][2].equals(type)) {
					return type;
				} else if (board[0][0].equalsIgnoreCase(type) && board[1][1].equalsIgnoreCase(type)
						&& board[2][2].equals(type)) {
					return type;
				}
			}
		}
		return "!F";
	}
	
	//prints the winner
	public void printWinner(int x) {
		if(x == 0) {
			System.out.println("The computer won! Better luck next time! ");
			cpuWins++;
		}
		else if(x == 1) {
			System.out.println("You won! Good job! ");
			userWins++;
		}
		else if(x == 2) {
			System.out.println("It's a draw!");
			draws++;
		}
	}
	
	//checks validity of the userInputs location
	public boolean locSearch(String s){
		if(s.length() != 2) // if location isnt in a 2 character format, it is not a valid location
			return false;
		else {
			String letter = s.substring(0, 1); //gets the letter from the location (ex. A1 gets A)
			
			String parse = s.substring(1); //gets the number from the location (ex. A1 gets 1)
			int num = Integer.parseInt(parse); 
			
			//checks if the Letter and Number are valid(A to C and 1 to 3)
			if(letter.equalsIgnoreCase("A")||letter.equalsIgnoreCase("B")||letter.equalsIgnoreCase("C")){
				if(num > 0 && num < 4) {
					return true;
				}
			}
		}
		return false;
	}
	
	//checks if the whole board is filled or not.
	public boolean isFilled() {
		int count = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j].equalsIgnoreCase("X")||board[i][j].equalsIgnoreCase("O"))
					count++;
			}
		}
		if(count == 9)
			return true;
		return false;
	}
	
	//picks whether CPU or Player goes first
	public void firstPlay(String s) {
		if(s.compareToIgnoreCase("yes") == 0) {
			isFirst = true; 
			System.out.println("Player will go first.");
		}
		else if(s.compareToIgnoreCase("no")==0) {
			isFirst = false;
			System.out.println("Computer will go first.");
		}
		else {
			isFirst = false;
			System.out.println("Unrecognizeable Word... Computer will go first as Default.");
		}
	}
	
	// sets User to X or O, and the CPU to the opposite
	public void setUserType(String s) {
		s = s.toUpperCase();
		if(s.compareTo("X") == 0) {
			userType = "X";
			cpuType = "O";
			System.out.println("Player has been set to 'X'; Computer has been set to 'O'.");
		}
		else if(s.compareTo("O") == 0) {
			userType = "O";
			cpuType = "X";
			System.out.println("Player has been set to 'O'; Computer has been set to 'X'.");
		}
		else {
			userType = "X";
			cpuType = "O";
			System.out.println("Unrecognizable Letter... Player has been reset to default: 'X'; Computer has been set to default: 'O'.");
		}
	}
	
	//prints the board, in matrix format
	public void print() {
		
		System.out.println("   1  2  3");
		for(int i = 0; i < 3; i++) {
			if(i == 0)
				System.out.print("A ");
			if(i == 1)
				System.out.print("B ");
			if(i == 2)	
				System.out.print("C ");
			for(int j = 0; j < 3; j++) {
				if(board[i][j].equalsIgnoreCase("X")||board[i][j].equalsIgnoreCase("O"))
					System.out.print("[" + board[i][j] + "]");
				else
					System.out.print("[N]");
			}
			System.out.println();
		}
	}
}