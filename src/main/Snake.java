package main;

import java.util.ArrayDeque;
import java.util.Queue;

public class Snake {
	
	Location head;
	Queue<Location> tail;
	int size;
	String direction;
	
	Snake(Location head) {
		this.head = new Location(head);
		tail = new ArrayDeque<Location>();
		tail.add(new Location(head.getPoint()));
		size = 0;
		direction = "r";
	}
	
	public void setHead(Location l) {
		head = new Location(l);
		tail = new ArrayDeque<Location>();
		tail.add(new Location(l));
		size = 1;
		direction = "r"; 
	}
	
	public Location getHead() {
		return head;
	}
	
	public void setDirection(String s) {
		switch(s) {
			case "r":
				direction = s;
				return;
			case "l":
				direction = s;
				return;
			case "u":
				direction = s;
				return;
			case "d":
				direction = s;
				return;
			default:
				throw new RuntimeException();
		}
	}
	
	public Queue<Location> getBody() {
		return tail;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void move(Location l, String direction, boolean eat) {
		if (eat) {
			size++;
		}
		this.direction = direction;
		tail.add(new Location(l.getPoint()));
		head.setLocation(l.getPoint());
	}
	
	public int getSize() {
		return size;
	}
	
	public Location getTail() {
		return tail.poll();
	}
	
	
}
