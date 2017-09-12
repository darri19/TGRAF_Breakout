package com.assignment1dv.game;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class Paddle {
	public Point2D pos;
	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;
	public float speed;
	
	public static void create(int vertexPointer){
		Paddle.vertexPointer = vertexPointer;
		//VERTEX ARRAY IS FILLED HERE
		float[] array = {-1.5f, -0.2f,
		-1.5f, 0.2f,
		1.5f, 0.2f,
		1.5f, -0.2f};
		vertexBuffer = BufferUtils.newFloatBuffer(8);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
	}
	
	public static void draw(){
		
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT,false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, 4);

	}
	
	
}
