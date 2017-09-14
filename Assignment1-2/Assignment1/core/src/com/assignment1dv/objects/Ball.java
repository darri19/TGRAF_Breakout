package com.assignment1dv.objects;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Point2D> getPoints(){
		List<Point2D> points = new ArrayList<Point2D>();
		for(float i = 0; i < 2* Math.PI; i += 2*Math.PI/8){
			points.add(new Point2D((float)(pos.x + radius*Math.cos(i)), (float)(pos.y + radius * Math.sin(i))));
		}
		return points;
		//Höldum að þetta virki, ótestað
	}
	
	public void draw(){
		BallGraphic.draw();
	}
	
}
