package com.assignment1dv.game;

public class Point2D{
	public float x;
	public float y;
	public float marginOfError = 0.1f;
	public Point2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
		
	}
	
	public float getDistBetween(Point2D other){
		return (float)Math.sqrt(Math.pow((other.x - this.x),2) + Math.pow((other.y - this.y),2));
	}
	
	public boolean isBetween(Point2D a, Point2D b){
		return(Math.abs(a.getDistBetween(b) - (this.getDistBetween(a) + this.getDistBetween(b))) < marginOfError);
	}
}
