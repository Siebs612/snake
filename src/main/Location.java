package main;

public class Location {
	int x; // row
	int y; // colum
	
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	Location(Location l) {
		x = l.getX();
		y = l.getY();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void setLocation(Location l) {
		x = l.getX();
		y = l.getY();
	}
	
	public boolean equals(Location l) {
		if (l.getX() == x && l.getY() == y) {
			return true;
		}
		return false;
	}
	
	public boolean equals(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		}
		return false;
	}
	
	public Location getPoint() {
		return this;
	}
}
