package com.assignment1dv.game;

public class Vector2D {
	float x;
	float y;
	
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getLength(){
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public float dotProduct(Vector2D other){
		return (this.x*other.x + this.y*other.y);
	}
	
	public Vector2D perp(){
		return new Vector2D(-y,x);
	}
	
	public Vector2D add(Vector2D other){
		return new Vector2D(this.x + other.x, this.y + other.y);
	}
	
	public Vector2D subtract(Vector2D other){
		return new Vector2D(this.x - other.x, this.y - other.y);
	}
	
	public Vector2D multiply(float plier){
		return new Vector2D(x*plier, y*plier);
	}
	
	public Vector2D normalize(){
		x = x / getLength();
		y = y / getLength();
		
		return this;
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
