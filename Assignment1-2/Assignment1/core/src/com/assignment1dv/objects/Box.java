package com.assignment1dv.objects;

import com.assignment1dv.game.Point2D;
import com.assignment1dv.graphics.BoxGraphic;

public class Box{
	public Point2D a;
	public Point2D b;
	public Point2D c;
	public Point2D d;
	
	public Box(){
		
	}
	public Box(Point2D a, Point2D b, Point2D c, Point2D d){
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
	}
	
	public void draw(){
		BoxGraphic.draw();
	}
}
