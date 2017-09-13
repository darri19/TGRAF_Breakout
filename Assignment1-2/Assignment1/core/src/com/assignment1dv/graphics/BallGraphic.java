 package com.assignment1dv.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class BallGraphic {
	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;
	private static int verticesPerCircle;
	
	public static void create(int vertexPointer){
		BallGraphic.vertexPointer = vertexPointer;
		//VERTEX ARRAY IS FILLED HERE
		verticesPerCircle = 20;
		float[] array = new float[2*verticesPerCircle];
		
		int counter = 0;
		for(float i = 0.0f; i<2*Math.PI;i+= (2*Math.PI)/verticesPerCircle){
			array[counter] = (float)Math.sin(i);
			array[counter+1] = (float)Math.cos(i);
			counter+=2;
		}
		vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerCircle);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}
	
	public static void draw(){
		
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);

	}
}
