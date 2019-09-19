
public class SudokuSolver {

	
	public static void main(String[] args) {
		
		int[][] grid = new int[][]{ // 
			  { 0, 0, 9, 2, 0, 0, 0, 0, 3 },
			  { 0, 7, 0, 0, 8, 0, 0, 6, 0 },
			  { 3, 0, 0, 0, 0, 9, 7, 0, 0 },
			  { 5, 0, 0, 0, 0, 2, 4, 0, 0 },
			  { 0, 1, 0, 0, 3, 0, 0, 5, 0 },
			  { 0, 0, 3, 7, 0, 0, 0, 0, 2 },
			  { 0, 0, 5, 8, 0, 0, 0, 0, 1 },
			  { 0, 2, 0, 0, 7, 0, 0, 9, 0 },
			  { 6, 0, 0, 0, 0, 4, 3, 0, 0 },
		};


		//copy of original grid to do solution
		int[][] solution = new int[grid.length][grid[0].length]; 
		for(int i=0; i<grid.length; i++)
			  for(int j=0; j<grid[i].length; j++) 
				  solution[i][j]=grid[i][j]; 

		//variable stuff
		int count = 0;
		int countdir = 1; //this one determines direction to check (so if there is an error you start moving back and not forward
		int boardsize = grid.length;
		
		
		while(count<boardsize*boardsize) {
			
			if (count == -1) {
				System.out.println("not possible");
				break;
			}
			
			//determines position on board 
			int row = count/boardsize;
			int col = count%boardsize;
			
			if (grid[row][col] != 0 && countdir == 1) {
				count++;
			}
			else if (grid[row][col] != 0 && countdir == -1) {
				count--;
			}
			else {
				
				solution[row][col]++; //increase the value of empty space by one and perform check (remember starts at 0)
				
				if(solution[row][col] > boardsize){ // if the value is greater than boardsize (9) then one of the previous values must be wrong so...
					solution[row][col] = 0; // we reset it to 0...
					count--; // ...move one index back on the board (count determines index)...
					countdir = -1; // ... and we set countdir to -1 so we move backwards (and skip over preset values in the correct direction)
				}
				else if(!isError(solution, row, col)){ //method checks to see if the value is valid on the solution board
					count++; // if there is no error, then we can move onto the next index...
					countdir = 1; // and make sure countdir is 1 so we move in the right direction
				}   
			}
		}
		printSudoku(solution); //method that prints sudoku when its done
	}
	
	
	private static boolean isError(int [][] solution, int row, int col) {
		
		boolean [] unavailable = new boolean [solution[0].length+1]; 
		//create a boolean that determines which values are invalid for a given position
		
		for (int i=0;i<solution[0].length;i++) { //traverses the row of the given index (left right)
			if (unavailable[solution[row][i]] && solution[row][i]!=0) //while traversing, if there is a value that is repeated (other than 0)...
				return true; //return true (meaning that there is an error)
			unavailable[solution[row][i]] = true; //if the value is still available (meaning the if statement is false), make it unavailable
		}
		//what happens above is that if the value you put in solution[row][col] is repeated in any row, column, or square, it returns true (meaning that there is an error)
		
		unavailable = new boolean [solution[0].length+1]; //recreate the boolean to check columns
		
		for (int i=0; i<solution.length; i++) { //traverses the column of the given index (up down)
			if (unavailable[solution[i][col]] && solution[i][col]!=0) //if a non-zero value is repeated...
				return true; //return true (error)
			unavailable[solution[i][col]] = true;
		}
		
		unavailable = new boolean [solution[0].length+1]; //re-declare boolean array
		
		int topleftx = col/3*3; //clever integer division to always start at top left corner of 3x3 square
		int toplefty = row/3*3;
		for (int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) { //nested for to traverse the 3x3 square and repeat processes above
				if (unavailable[solution[topleftx+i][toplefty+j]] && solution[topleftx+i][toplefty+j]!=0)
					return true;
				unavailable[solution[topleftx+i][toplefty+j]] = true;
			}
		}
		
		return false; //if everything is satisfied and there is no error, return false.
	}
	
	
	
	private static void printSudoku(int[][] solution){ // prints the board
		for(int row=0; row<solution.length; row++){ // loop through rows of sudoku solutions board
			  if(row%3 == 0 && row!=0) // every third row there is a break, however we don't want a break on the first row
				  System.out.println();
			  for(int col=0; col<solution[row].length; col++){ // loop through columns of sudoku solutions board
				  if(col%3 == 0 && col!=0) // every third column there is a break, however we don't want a break on the first column
					  System.out.print(" ");
				  System.out.print(solution[row][col] + " "); // print value with space afterward, so that numbers are formatted correctly, and do not appear as though they are one larger number
			  }
			  System.out.println(); // the end of the line is reached, so we move to the next line.
		}
	}

}
