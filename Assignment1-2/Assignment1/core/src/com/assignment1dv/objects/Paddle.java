package com.assignment1dv.objects;

import com.assignment1dv.game.Point2D;
import com.assignment1dv.graphics.PaddleGraphic;
import com.badlogic.gdx.Gdx;

public class Paddle {
	
	public Point2D position;
	public float speed;
	
	public Paddle(Point2D pos){
		position = pos;
		speed = 500.0f;
	}
	
	public void draw(){
		PaddleGraphic.draw();
	}
	
	public void moveRight(float deltaTime){
		position.x += deltaTime * speed;
		
	}
	
	public void moveLeft(float deltaTime){
		position.x -= deltaTime * speed;
		
	}
}
