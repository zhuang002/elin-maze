import java.io.File;
import java.util.Random;

public class Maze {
	private char[][] grid = null;
	Random rand = new Random();
	Coordinate start = null;
	Coordinate exit = null;
	final int MAX_PATHS = 10;
	final int MAX_SEGS = 10;
	
	public void generateMaze() {
		// TODO Auto-generated method stub
		//random size
		int width = rand.nextInt(18)+3;
		int height = rand.nextInt(18)+3;
		
		System.out.println("size:"+height+","+width);
		//initialize grid;
		grid = new char[height][width];
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				grid[i][j]='B';
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
		
		this.grid[startRow][startCol]='S';
		this.grid[exit.row][exit.col]='X';
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
		System.out.print("maxsteps:"+maxSteps+",dir:"+dir+",");
		int steps = rand.nextInt(maxSteps)+1;
		System.out.println("segment:"+segStart.row+","+segStart.col);
		int currentRow=segStart.row;
		int currentCol=segStart.col;
		for (int i=1;i<=steps;i++) {
			switch (dir) {
			case 0:
				currentRow--;
				grid[currentRow][currentCol] = 'O';
				break;
			case 1:
				currentCol--;
				grid[currentRow][currentCol] = 'O';
				break;
			case 2:
				currentRow++;
				grid[currentRow][currentCol] = 'O';
				break;
			case 3:
				currentCol++;
				grid[currentRow][currentCol] = 'O';
				break;
			}
		}
		return new Coordinate(currentRow,currentCol);
		
	}

	public char[][] getGrid() {
		// TODO Auto-generated method stub
		return this.grid;
	}

	public void loadFile(File file) {
		// TODO Auto-generated method stub
		
	}

	public void findPath() {
		// TODO Auto-generated method stub
		
	}

}
