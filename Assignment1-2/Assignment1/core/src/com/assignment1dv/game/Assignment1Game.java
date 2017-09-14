package com.assignment1dv.game;


import com.assignment1dv.graphics.BallGraphic;
import com.assignment1dv.graphics.BoxGraphic;
import com.assignment1dv.graphics.PaddleGraphic;
import com.assignment1dv.graphics.Text;
import com.assignment1dv.objects.Ball;
import com.assignment1dv.objects.Box;
import com.assignment1dv.objects.Paddle;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.utils.BufferUtils;



public class Assignment1Game extends ApplicationAdapter {

	private FloatBuffer modelMatrix;
	private FloatBuffer projectionMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;
	


	private Paddle p;
	private Ball b;
	private int rows;
	private int cols;
	private Box[][] boxes;
	
	private int height;
	private int width;
	
	private boolean gameStarted = false;

	float colorHue;
	int lives = 3;
	boolean dead;
	

	@Override
	public void create () {
		colorHue = 0.0f;
		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);

		float[] pm = new float[16];

		pm[0] = 2.0f / Gdx.graphics.getWidth(); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -1.0f;
		pm[1] = 0.0f; pm[5] = 2.0f / Gdx.graphics.getHeight(); pm[9] = 0.0f; pm[13] = -1.0f;
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		projectionMatrix = BufferUtils.newFloatBuffer(16);
		projectionMatrix.put(pm);
		projectionMatrix.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);


		float[] mm = new float[16];

		mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
		mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
		mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
		mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

		modelMatrix = BufferUtils.newFloatBuffer(16);
		modelMatrix.put(mm);
		modelMatrix.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		p = new Paddle(new Point2D(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/20), height/100,width/20);
		b = new Ball(new Point2D(p.pos.x, p.pos.y+p.height),height/50);
		System.out.println(p.pos.y+p.height);
		
		BallGraphic.create(positionLoc); 
		PaddleGraphic.create(positionLoc); 
		BoxGraphic.create(positionLoc);
		rows = 6;
		cols = 8;
		placeBricks();
	}
	@Override
	public void render () {

		
		update();

		display();
		
	}


	private void update() {
		
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		
		if(b.pos.y < -200){
			lives--;
			gameStarted = false;
			if(lives > 0){
				b.pos.y = p.pos.y+p.height+b.radius;
			}
		}
		
		checkAllCollisions(deltaTime);

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && p.pos.x-p.width > 0){
			p.moveLeft(deltaTime);

		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && p.pos.x+p.width < width){
			p.moveRight(deltaTime);
		}
		
		if(!gameStarted){
			b.pos.x = p.pos.x;
		}
		else{
			b.pos.x += b.dir.x * b.speed * deltaTime;
			b.pos.y += b.dir.y * b.speed * deltaTime;		
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			gameStarted = true; 
		}
		
		
		// Cheat
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
			if(Gdx.input.isKeyPressed(Input.Keys.UP) && p.pos.x > 0){
				p.pos.y += deltaTime * p.speed;

			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && p.pos.x < width){
				p.pos.y -= deltaTime * p.speed;
			}
		}
		
		
		// Change background hue
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			if(colorHue < 1)
				colorHue+= 0.01; 
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			if(colorHue >0)
				colorHue-= 0.01; 
		}
	}

	private void checkAllCollisions(float deltaTime) {
		for(Point2D point : b.getPoints()){
			checkCollisions(point,deltaTime);
		}
	}
	private void display() {

		Gdx.gl.glClearColor(0,0,colorHue , 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		setModelMatrixTranslation(b.pos.x,b.pos.y);
		setModelMatrixScale(b.radius,b.radius);
		Gdx.gl.glUniform4f(colorLoc, (b.pos.y/height), 1-(b.pos.y/height), 1f, 1);
		b.draw();
		
		setModelMatrixTranslation(p.pos.x,p.pos.y);
		setModelMatrixScale(p.width,p.height);
		Gdx.gl.glUniform4f(colorLoc, 1f, 0.1f, 0.6f, 1);

		p.draw();
		drawBricks();
		
		if(!gameStarted){
			if(lives > 0){
				Text text = new Text("Press space to play\nRemaining lives: " + lives,new Point2D(width/20,height-(height*(3/5.0f))), Color.WHITE,40 );
				text.draw(positionLoc,renderingProgramID);
			}else{
				Text text = new Text("Game over",new Point2D(width/20,height-(height*(3/5.0f))), Color.WHITE,40 );
				text.draw(positionLoc,renderingProgramID);
			}
		}
		

	}
	
	private void placeBricks(){
		boxes = new Box[rows][cols];

		int startX = width/10;
		int endX = width - startX;
		int startY = height - (int)(height*(2/5.0f));
		int endY = height - height/15;
		
		int xStep = (endX-startX)/(cols-1);
		int yStep = (endY-startY)/(rows-1);
		

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				boxes[i][j] = new Box(new Point2D(startX+j*xStep,startY + i*yStep),width/20,height/50);
			}
		}
		
	}
	
	private void drawBricks(){
		
		Gdx.gl.glUniform4f(colorLoc, 1f, 1f, 0f, 1);

		for(int i = 0; i < rows; i++){
			Gdx.gl.glUniform4f(colorLoc, 1f, 0.8f/(i+0.1f), 0f, 1);

			for(int j = 0; j < cols; j++){
				if(boxes[i][j] != null){
					Box box = boxes[i][j];
					setModelMatrixTranslation(box.pos.x,box.pos.y);
					setModelMatrixScale(box.width,box.height);
					box.draw();
				}
			}
		}
	}
	
	private void checkCollisions(Point2D ballPoint, float deltaTime){
		
		// Borders
		checkCollisionOnLine(ballPoint, new Point2D(0,0),new Point2D(0,height),deltaTime);
		checkCollisionOnLine(ballPoint, new Point2D(width,0),new Point2D(width,height),deltaTime);
		checkCollisionOnLine(ballPoint, new Point2D(0,height),new Point2D(width,height),deltaTime);

		// Paddle
		checkCollisionOnPaddle(ballPoint, new Point2D(p.pos.x-p.width,p.pos.y + p.height/2),new Point2D(p.pos.x+p.width,p.pos.y + p.height/2),deltaTime);

		// Boxes
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				if(boxes[i][j] != null){
					dead = false;
					checkCollisionOnBox(ballPoint, boxes[i][j],deltaTime);
					if(dead){
						boxes[i][j] = null;
					}
				}
			}
		}
	}
	
	private void checkCollisionOnPaddle(Point2D ballPoint, Point2D p1, Point2D p2, float deltaTime) {
		Point2D A = ballPoint;
		Vector2D c = b.dir.multiply(b.speed);
		Point2D B = p1;
		
		Vector2D v = new Vector2D(B.x - p2.x, B.y - p2.y);
		Vector2D n = v.perp();
		
		Vector2D bminusa = new Vector2D(B.x-A.x,B.y-A.y);
		
		float tHit = n.dotProduct(bminusa)/n.dotProduct(c);
		
		Point2D pHit = new Point2D(A.x + tHit*c.x,A.y + tHit*c.y);
		
		if(pHit.isBetween(p1,p2) && tHit > 0 && tHit < deltaTime){
			Vector2D r = c.subtract(n.multiply(2*(c.dotProduct(n)/n.dotProduct(n))));
			
			// Depending on where we hit the paddle, we want to rotate the reflection vector
			float hitPos = (pHit.x - p1.x - p.width)/p.width;
			float angle = -(45 * hitPos);
			float theta = degToRad(angle);
			float ct = (float)Math.cos(theta);
			float st = (float)Math.sin(theta);
			b.dir = new Vector2D(r.x*ct - r.y*st, r.x*st + r.y*ct).normalize();
			dead = true;
		}		
	}
	
	public float degToRad(float angle){
		return (float)(angle * Math.PI)/180;
	}
	private void checkCollisionOnLine(Point2D ballPoint, Point2D p1, Point2D p2, float deltaTime){
		Point2D A = ballPoint;
		Vector2D c = b.dir.multiply(b.speed);
		Point2D B = p1;
		
		Vector2D v = new Vector2D(B.x - p2.x, B.y - p2.y);
		Vector2D n = v.perp();
		
		Vector2D bminusa = new Vector2D(B.x-A.x,B.y-A.y);
		
		float tHit = n.dotProduct(bminusa)/n.dotProduct(c);
		
		Point2D pHit = new Point2D(A.x + tHit*c.x,A.y + tHit*c.y);
		
		if(pHit.isBetween(p1,p2) && tHit > 0 && tHit < deltaTime){
			Vector2D r = c.subtract(n.multiply(2*(c.dotProduct(n)/n.dotProduct(n))));
			b.dir = r.normalize();
			dead = true;
		}

	}
	
	private void checkCollisionOnBox(Point2D ballPoint, Box box, float deltaTime){
		Point2D a = new Point2D(box.pos.x-box.width, box.pos.y-box.height);
		Point2D b = new Point2D(box.pos.x-box.width, box.pos.y+box.height);
		Point2D c = new Point2D(box.pos.x+box.width, box.pos.y+box.height);
		Point2D d = new Point2D(box.pos.x+box.width, box.pos.y-box.height);
		
		checkCollisionOnLine(ballPoint, a,b,deltaTime);
		checkCollisionOnLine(ballPoint, b,c,deltaTime);
		checkCollisionOnLine(ballPoint, c,d,deltaTime);
		checkCollisionOnLine(ballPoint, d,a,deltaTime);


	}


	private void clearModelMatrix()
	{
		modelMatrix.put(0, 1.0f);
		modelMatrix.put(1, 0.0f);
		modelMatrix.put(2, 0.0f);
		modelMatrix.put(3, 0.0f);
		modelMatrix.put(4, 0.0f);
		modelMatrix.put(5, 1.0f);
		modelMatrix.put(6, 0.0f);
		modelMatrix.put(7, 0.0f);
		modelMatrix.put(8, 0.0f);
		modelMatrix.put(9, 0.0f);
		modelMatrix.put(10, 1.0f);
		modelMatrix.put(11, 0.0f);
		modelMatrix.put(12, 0.0f);
		modelMatrix.put(13, 0.0f);
		modelMatrix.put(14, 0.0f);
		modelMatrix.put(15, 1.0f);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
	private void setModelMatrixTranslation(float xTranslate, float yTranslate)
	{
		modelMatrix.put(12, xTranslate);
		modelMatrix.put(13, yTranslate);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
	private void setModelMatrixScale(float xScale, float yScale)
	{
		modelMatrix.put(0, xScale);
		modelMatrix.put(5, yScale);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
}