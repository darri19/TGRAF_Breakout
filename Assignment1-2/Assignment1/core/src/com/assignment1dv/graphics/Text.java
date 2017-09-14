package com.assignment1dv.graphics;

import java.awt.GraphicsEnvironment;

import com.assignment1dv.game.Point2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Text {
    private SpriteBatch batch;
    private BitmapFont font;
    private String text;
    private Point2D position;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
   
    public Text(String text, Point2D position, Color color, int fontSize) {
        this.text = text;
        this.position = position;
        batch = new SpriteBatch();
   
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cabin-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
       
        parameter.size = fontSize;
        parameter.color = color;
        font = generator.generateFont(parameter);
        generator.dispose();
    }
 
    public void draw(int pointer,int id) {
        batch.begin();
        font.draw(batch, text, position.x, position.y);
        batch.end();
        Gdx.gl.glUseProgram(id);
        Gdx.gl.glEnableVertexAttribArray(pointer);
    }
}