package com.assignment1dv.objects;

import com.assignment1dv.game.Point2D;
import com.assignment1dv.graphics.BoxGraphic;

public class Box{
	public Point2D pos;
	public float width;
	public float height;
	
	public Box(){
		
	}
	public Box(Point2D pos, float width, float height){
		this.pos = pos;
		this.width = width;
		this.height = height;
	}
	
	public void draw(){
		BoxGraphic.draw();
	}
}
