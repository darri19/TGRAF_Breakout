package com.assignment1dv.objects;

import com.assignment1dv.game.Point2D;
import com.assignment1dv.graphics.PaddleGraphic;

public class Paddle {
	
	public Point2D pos;
	public float speed;
	public float width;
	public float height;
	
	public Paddle(Point2D pos, float height, float width){
		this.pos = pos;
		speed = 500.0f;
		this.height = height;
		this.width = width;
	}
	
	public void draw(){
		PaddleGraphic.draw();
	}
	
	public void moveRight(float deltaTime){
		pos.x += deltaTime * speed;
		
	}
	
	public void moveLeft(float deltaTime){
		pos.x -= deltaTime * speed;
		
	}
}
