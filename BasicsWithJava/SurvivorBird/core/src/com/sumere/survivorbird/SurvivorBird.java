package com.sumere.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	private int score = 0;
	private SpriteBatch batch;
	private Texture textureBackground;
	private Texture textureBird;
	private Texture textureWood1;
	private Texture textureWood2;
	private Texture textureWood3;
	private  Texture textureWood4;
	private float birdX;
	private float birdY;
	private float birdWidth;
	private float birdHeight;
	private int gameState = 0;
	private float velocity = 1;
	private float gravity = 0.5f;
	private int numberOfEnemies = 4;
	private float[] enemyX = new float[numberOfEnemies];
	private float[] enemyOffset1 = new float[numberOfEnemies];
	private float[] enemyOffset2 = new float[numberOfEnemies];
	private float[] enemyOffset3 = new float[numberOfEnemies];
	private float[] enemyOffset4 = new float[numberOfEnemies];
	private float enemyWidth;
	private float enemyHeight;
	private Random random;
	private float distance = 0;
	private float enemyVelocity = 8;
	private Circle birdCircle;
	private Circle[] enemyCircles1;
	private Circle[] enemyCircles2;
	private Circle[] enemyCircles3;
	private Circle[] enemyCircles4;
	private BitmapFont font;
	private int scoredEnemy = 0;
	@Override
	public void create () {
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		random = new Random(0);
		batch = new SpriteBatch();
		textureBackground = new Texture("background.png");
		textureBird = new Texture("bird.png");
		textureWood1 = new Texture("wood.png");
		textureWood2 = new Texture("wood.png");
		textureWood3 = new Texture("wood.png");
		textureWood4 = new Texture("wood.png");
		birdX = Gdx.graphics.getWidth()/4;
		birdY = Gdx.graphics.getHeight()/2;
		birdWidth = Gdx.graphics.getWidth()/16;
		birdHeight = Gdx.graphics.getHeight()/12;
		enemyWidth = Gdx.graphics.getWidth() / 12;
		enemyHeight = Gdx.graphics.getHeight() / 6;
		distance = Gdx.graphics.getWidth()/2;
		birdCircle = new Circle();
		enemyCircles1 = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		enemyCircles4 = new Circle[numberOfEnemies];
		for(int i = 0;i<numberOfEnemies;i++){
			enemyX[i] = Gdx.graphics.getWidth() - textureWood1.getWidth() / 2 + i * distance;
			enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
			enemyCircles4[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(textureBackground,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(textureBird,birdX,birdY,birdWidth,birdHeight);
		if(gameState == 1){
			if(enemyX[scoredEnemy] < birdX){
				score += 1;
				if(scoredEnemy < 3)
					scoredEnemy++;
				else
					scoredEnemy = 0;
			}
			for(int i = 0;i<numberOfEnemies;i++) {
				if(enemyX[i] < -textureWood1.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;
					enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				}
				enemyX[i] -= enemyVelocity;
				batch.draw(textureWood1, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset1[i], enemyWidth, enemyHeight);
				batch.draw(textureWood2, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset2[i], enemyWidth, enemyHeight);
				batch.draw(textureWood3, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset3[i], enemyWidth, enemyHeight);
				batch.draw(textureWood4, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffset4[i], enemyWidth, enemyHeight);
				enemyCircles1[i] = new Circle(enemyX[i] + enemyWidth/2,Gdx.graphics.getHeight()/2 + enemyOffset1[i] + enemyHeight/2,enemyWidth/2);
				enemyCircles2[i] = new Circle(enemyX[i] + enemyWidth/2,Gdx.graphics.getHeight()/2 + enemyOffset2[i] + enemyHeight/2,enemyWidth/2);
				enemyCircles3[i] = new Circle(enemyX[i] + enemyWidth/2,Gdx.graphics.getHeight()/2 + enemyOffset3[i] + enemyHeight/2,enemyWidth/2);
				enemyCircles4[i] = new Circle(enemyX[i] + enemyWidth/2,Gdx.graphics.getHeight()/2 + enemyOffset4[i] + enemyHeight/2,enemyWidth/2);

			}
			if(birdY > 0 && birdY < Gdx.graphics.getHeight()){
				if(Gdx.input.justTouched())
					velocity -= 10;
				velocity += gravity;
				birdY = birdY - velocity;
			} else {
				gameState = 2;
			}
		} else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		} else{
			font.getData().setScale(6);
			font.draw(batch,String.valueOf(score),Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
			if(Gdx.input.justTouched())
				gameState = 1;
			birdY = Gdx.graphics.getHeight()/2;
			velocity = 0;
			score = 0;
			scoredEnemy = 0;
			for(int i = 0;i<numberOfEnemies;i++){
				enemyX[i] = Gdx.graphics.getWidth() - textureWood1.getWidth() / 2 + i * distance;
				enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffset4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyCircles1[i] = new Circle();
				enemyCircles2[i] = new Circle();
				enemyCircles3[i] = new Circle();
				enemyCircles4[i] = new Circle();
			}
		}
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
		birdCircle.set(birdX +birdWidth/2,birdY + birdHeight/2,birdWidth/2);
		for(int i = 0;i<numberOfEnemies;i++){
			if(Intersector.overlaps(birdCircle,enemyCircles1[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i]) || Intersector.overlaps(birdCircle,enemyCircles4[i]))
			{
				gameState = 2;
			}
		}
	}
	
	@Override
	public void dispose () {

	}
}
