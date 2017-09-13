package com.assignment1dv.objects;

import com.assignment1dv.game.Point2D;
import com.assignment1dv.game.Vector2D;
import com.assignment1dv.graphics.BallGraphic;

public class Ball {
	public Point2D pos;
	public Vector2D dir;
	public float speed;
	public float radius;
	
	public Ball(Point2D pos,float radius){
		this.pos = pos;
		speed = 500f;
		this.radius = radius;
		pos.y+= radius;
		dir = new Vector2D(1,1);
	
	}
	
	public void draw(){
		BallGraphic.draw();
	}
	
}
