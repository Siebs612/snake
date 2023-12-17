package main;
/***
 * 
 ** Board Class
 *
 * @author Paul	Siebenaler
 * @date 12/06/2023
 * 
 * @apiNote
 * Class is designed to hold and manipulate a 2D array. This 2D array is a representation of
 * the game snake. the array field holds the snake, bait and field. A blank spot on the field
 * denoted as a 0. The snake is denoted as a 1 and the bait is a -1.
 * 
 */
import java.util.Random;

public class Board {
	int[][] field;
	Location bait;
	Snake snake;
	boolean isGameOver;
	Random r;
	
	/***Constructor
	 * 
	 ** Board
	 * 
	 * @param int width - the width of the playing field
	 * @note 
	 * 		Constructor is used to initialize a game board for snake. This will set up
	 * the field in the staring position with the snake starting as 1 unit long and in the
	 * middle of the screen. The field is always a square which is why only on side is needed
	 * The bait is placed in a random location on the field
	*/
	
	public Board(int width) {
		if (width < 10) {
			throw new RuntimeException();
		}
		r = new Random();
		field = new int[width][width];
		bait = new Location(r.nextInt(width-1), r.nextInt(width-1));
		field[bait.getX()][bait.getY()] = -1;
		snake = new Snake(new Location((width/2)-1,(width/2)-1));
		field[snake.getHead().getX()][snake.getHead().getY()] = 1;
		isGameOver = false;
		
	}
	/*** Getter and Setters
	 * 
	 ** isGameOver
	 * @param none
	 * @return isGameOver
	 */
	public boolean isGameOver() {
		return isGameOver;
	}
	/** getBoard 
	 * 
	 * @param none
	 * @return field array
	 */
	public int[][] getBoard() {
		return field;
	}
	/**getScore
	 * 
	 * 
	 * @return
	 */
	public int getScore() {
		int res = snake.getSize();
		return res;
	}
	
	public void printBoard() {
		for (int i = 0; i< field.length ;i++) {
			for (int j = 0; j < field[i].length; j++) {
				System.out.print(String.format("%2d ", field[i][j]));
			}
			System.out.println();
		}
	}
	
	public void setBait() {
		bait = new Location(r.nextInt(field.length), r.nextInt(field[0].length));
		while( field[bait.getX()][bait.getX()] != 0) {
			bait = new Location(r.nextInt(field.length), r.nextInt(field[0].length));
		}
		field[bait.getX()][bait.getY()] = -1;
	}
	public void setBait(Location l) {
		field[bait.getX()][bait.getY()] = 0;
		bait = new Location(l);
		field[bait.getX()][bait.getY()] = -1;
	}
	
	
	public int evalPoint(Location l) {
		if (l.getX() < 0 || l.getY() < 0 || field.length == l.getX() || field[0].length == l.getY()) {
			return -1;
		}
		if (bait.getX() == l.getX() && bait.getY() == l.getY()) {
			return 1;
		}
		for (Location body: snake.getBody()) {
			if (body.equals(l)) {
				return -1;
			}
		}
		return 0;
	}
	
	public Board playFrame(String input) {
		Location tmp = snake.getHead();
		switch(input) {
			case "r":
				tmp = new Location(tmp.getX(),tmp.getY()+1);
				break;
			case "l":
				tmp = new Location(tmp.getX(),tmp.getY()-1); 
				break;
			case "u":
				tmp = new Location(tmp.getX()-1, tmp.getY());
				break;
			case "d":
				tmp  =new Location(tmp.getX()+1, tmp.getY());
				break;
		}
		
		switch(evalPoint(tmp)) {
			case -1:
				isGameOver = true;
				return this;
			case 1:
				snake.move(tmp, snake.getDirection(), true);
				field[tmp.getX()][tmp.getY()] = 1;
				setBait();
				break;
			case 0:
				snake.move(tmp, snake.getDirection(), false);
				Location tail = snake.getTail();
				field[tail.getX()][tail.getY()] = 0; 
				break;
		}
		field[tmp.getX()][tmp.getY()] = 1;
		return this;
	}
}
