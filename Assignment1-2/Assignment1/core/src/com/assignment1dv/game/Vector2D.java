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
}
