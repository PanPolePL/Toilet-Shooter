package com.twojstary.javagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class JavaGame extends ApplicationAdapter {
	SpriteBatch batch;
	Rectangle player;
	Texture playerImage;
	Rectangle ammo;
	Texture ammoImage;
	Rectangle enemy;
	Texture enemyImage;
	Rectangle explosion;
	Texture[] explosionImages = new Texture[26];
	Texture explosionImage;
	boolean gameInProgress;
	int i=0;

	public boolean collision_check(){
		if(ammo.x+ammo.width/2>=enemy.x && ammo.x<=enemy.x+enemy.width+ammo.width/2 && ammo.y+ammo.height>=enemy.y)
		{
			ammo.y=9999;
			explosion.x=enemy.x;
			explosion.y=enemy.y;
			i=0;
			return true;
		}
		return false;
	}

	public void fire(){
		ammo.x=player.x+50;
		ammo.y=player.y+player.height;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerImage = new Texture(Gdx.files.internal("player_r.png"));
		player = new Rectangle();
		player.x = 512;
		player.y = 0;
		player.width = 150;
		player.height = 125;

		ammoImage = new Texture(Gdx.files.internal("ammo.png"));
		ammo = new Rectangle();
		ammo.x = 512;
		ammo.y = 9999;
		ammo.width = 70;
		ammo.height = 70;

		enemyImage = new Texture(Gdx.files.internal("enemy.png"));
		enemy = new Rectangle();
		enemy.x = 512;
		enemy.y = 720+enemy.height;
		enemy.width = 70;
		enemy.height = 70;

		explosion = new Rectangle();
		explosion.x = 512;
		explosion.y = 9999;
		explosion.width = 200;
		explosion.height = 200;
		for(int i=0; i<=25; i++) {
			explosionImages[i] = new Texture(Gdx.files.internal("explosion/explosion" + i + ".png"));
		}
		explosionImage=explosionImages[0];

		gameInProgress = true;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(playerImage, player.x, player.y);
		batch.draw(ammoImage, ammo.x, ammo.y);
		batch.draw(enemyImage, enemy.x, enemy.y);
		batch.draw(explosionImage, explosion.x, explosion.y);
		batch.end();

		//user input
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.x -= 300 * Gdx.graphics.getDeltaTime();
			playerImage=new Texture(Gdx.files.internal("player_l.png"));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.x += 300 * Gdx.graphics.getDeltaTime();
			playerImage=new Texture(Gdx.files.internal("player_r.png"));
		}
		//if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.y += 300 * Gdx.graphics.getDeltaTime();
		//if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.y -= 300 * Gdx.graphics.getDeltaTime();

		//firing
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			if(ammo.y >= 720 + ammo.height) {
				fire();
			}
			else if(collision_check()) {
				fire();
			}
		}

		//borders
		if(player.x < 0) player.x = 0;
		if(player.x > 1280-player.width) player.x = 1280-player.width;
		//if(player.y < 0) player.y = 0;
		//if(player.y > 720-player.width) player.y = 720-player.width;

		if(gameInProgress) ammo.y+=500*Gdx.graphics.getDeltaTime();

		//enemy movement
		if(gameInProgress) enemy.y-=100*Gdx.graphics.getDeltaTime();
		if(collision_check()) {
			enemy.y=720+enemy.height;
			enemy.x=(int)(Math.random() * 1280-2*enemy.width) + enemy.width;
		}

		explosionImage=explosionImages[i];
		i++;
		if(i==26) {
			i=0;
		}
		if(explosionImage==explosionImages[25]){
			explosion.x=9999;
			explosion.y=9999;
		}

		collision_check();

		//game over
		if(enemy.y+enemy.height<=0) {
			gameInProgress=false;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		enemyImage.dispose();
		ammoImage.dispose();
		explosionImage.dispose();
	}
}
