import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {
	private char[][] grid = null;
	private int width = 20;
	private int height = 20;
	Random rand = new Random();
	Coordinate start = null;
	Coordinate exit = null;
	char block='B';
	char open = 'O';
	char exitChar = 'X';
	char startChar ='S';
	
	final int MAX_PATHS = 10;
	final int MAX_SEGS = 10;
	
	public void generateMaze() {
		// TODO Auto-generated method stub
		block = 'B';
		open = 'O';
		exitChar = 'X';
		startChar = 'S';
		
		//random size
		width = rand.nextInt(18)+3;
		height = rand.nextInt(18)+3;
		
		//initialize grid;
		grid = new char[height][width];
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				grid[i][j]=block;
			}
		}
		
		// random start
		int startRow = rand.nextInt(height-2)+1;
		int startCol = rand.nextInt(width-2)+1;
		start = new Coordinate(startRow, startCol);
		
		
		// random exit
		int edge = rand.nextInt(4);
		switch (edge) {
		case 0:
			exit = new Coordinate(0, rand.nextInt(width-2)+1);
			break;
		case 1:
			exit = new Coordinate(rand.nextInt(height-2)+1,0);
			break;
		case 2:
			exit = new Coordinate(height-1, rand.nextInt(width-2)+1);
			break;
		case 3:
			exit = new Coordinate(rand.nextInt(height-2)+1, width-1);
			break;
		}
		
		
		// random paths. not necessary to reach the exit;
		int noPaths = rand.nextInt(MAX_PATHS);
		for (int i=0;i<noPaths;i++) {
			if (i==0) {
				generatePath(start);
			} else {
				generatePath(new Coordinate(rand.nextInt(height-2)+1, rand.nextInt(width-2)+1));
			}
		}
		
		this.grid[startRow][startCol]=startChar;
		this.grid[exit.row][exit.col]=exitChar;
	}

	private void generatePath(Coordinate coord) {
		// TODO Auto-generated method stub
		int segments = rand.nextInt(MAX_SEGS);
		Coordinate segStart = coord;
		for (int i=0;i<segments;i++) {
			segStart = generateSegment(segStart);
		}
	}

	private Coordinate generateSegment(Coordinate segStart) {
		// TODO Auto-generated method stub
		int dir = rand.nextInt(4);
		int maxSteps = 0;
		
		switch(dir) {
		case 0:
			maxSteps = segStart.row-1;
			break;
		case 1:
			maxSteps = segStart.col-1;
			break;
		case 2:
			maxSteps = grid.length-segStart.row-2;
			break;
		case 3: 
			maxSteps = grid[0].length - segStart.col-2;
			break;
		}
		if (maxSteps<1) return segStart;
		int steps = rand.nextInt(maxSteps)+1;
		int currentRow=segStart.row;
		int currentCol=segStart.col;
		for (int i=1;i<=steps;i++) {
			switch (dir) {
			case 0:
				currentRow--;
				grid[currentRow][currentCol] = open;
				break;
			case 1:
				currentCol--;
				grid[currentRow][currentCol] = open;
				break;
			case 2:
				currentRow++;
				grid[currentRow][currentCol] = open;
				break;
			case 3:
				currentCol++;
				grid[currentRow][currentCol] = open;
				break;
			}
		}
		return new Coordinate(currentRow,currentCol);
		
	}

	public char[][] getGrid() {
		// TODO Auto-generated method stub
		return this.grid;
	}

	public void loadFile(File file) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(file);
		height = in.nextInt();
		width = in.nextInt();
		in.nextLine();
		block = in.nextLine().charAt(0);
		open = in.nextLine().charAt(0);
		startChar = in.nextLine().charAt(0);
		exitChar = in.nextLine().charAt(0);
		
		grid = new char[height][width];
		for (int i=0;i<height;i++) {
			String row = in.nextLine();
			for (int j=0;j<width;j++) {
				grid[i][j]=row.charAt(j);
				if (grid[i][j]==startChar) {
					start = new Coordinate(i,j);
				} else if (grid[i][j]==exitChar) {
					exit = new Coordinate(i,j);
				}
					
			}
		}
		
	}

	public void findPath() {
		// TODO Auto-generated method stub
		ArrayList<Coordinate> touchedCoordinates = new ArrayList<Coordinate>();
		
		walkFrom(start, touchedCoordinates);
		
		grid[start.row][start.col] = startChar;
	}

	private void walkFrom(Coordinate coord, ArrayList<Coordinate> touchedCoordinates) {
		// TODO Auto-generated method stub
		char c = grid[coord.row][coord.col];
		if (c==exitChar || c=='+') {
			return;
		}
		touchedCoordinates.add(coord);
		ArrayList<Coordinate> neighbours = getNeighbours(coord, touchedCoordinates);
		for (Coordinate crd:neighbours) {
			walkFrom(crd, touchedCoordinates);
			c = grid[crd.row][crd.col];
			if (c=='+' || c==exitChar) {
				grid[coord.row][coord.col]='+';
			}
		}
		touchedCoordinates.remove(coord);
	}

	private ArrayList<Coordinate> getNeighbours(Coordinate coord, ArrayList<Coordinate> touchedCoordinates) {
		// TODO Auto-generated method stub
		ArrayList<Coordinate> neighbours = new ArrayList<>();
		Coordinate crd = new Coordinate(coord.row-1,coord.col);
		if (isValidNeighbour(crd,touchedCoordinates)) {
			neighbours.add(crd);
		}
		
		crd = new Coordinate(coord.row+1,coord.col);
		if (isValidNeighbour(crd,touchedCoordinates)) {
			neighbours.add(crd);
		}
		
		crd = new Coordinate(coord.row,coord.col-1);
		if (isValidNeighbour(crd,touchedCoordinates)) {
			neighbours.add(crd);
		}
		
		crd = new Coordinate(coord.row,coord.col+1);
		if (isValidNeighbour(crd,touchedCoordinates)) {
			neighbours.add(crd);
		}
		return neighbours;
		
	}

	private boolean isValidNeighbour(Coordinate crd, ArrayList<Coordinate> touchedCoordinates) {
		// TODO Auto-generated method stub
		if (crd.row<0 || crd.row>height-1 || crd.col<0 || crd.col>width-1) {
			return false;
		}
		if (touchedCoordinates.contains(crd))
			return false;
		char c = grid[crd.row][crd.col];
		return c=='+' || c==open || c==exitChar;
		
	}

}
