package com.twojstary.javagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	boolean gamePaused;
	boolean gameOver;
	int i=0;
	Sound fireSound;
	Sound hitSound;
	Sound gameOverSound;
	boolean soundPlayed;
	BitmapFont font;
	int score;

	public boolean collision_check(){
		if(ammo.x+ammo.width/2>=enemy.x && ammo.x<=enemy.x+enemy.width+ammo.width/2 && ammo.y+ammo.height>=enemy.y) {
			score++;
			hitSound.play(1.0f);
			ammo.x=9999;
			ammo.y=9999;
			i=0;
			explosion.x=enemy.x;
			explosion.y=enemy.y;
			enemy.y=720+enemy.height;
			enemy.x=(int)(Math.random() * 1280-2*enemy.width-50) + enemy.width+50;
			return true;
		}
		else {
			return false;
		}
	}

	public void fire(){
		fireSound.play(1.0f);
		ammo.x=player.x+50;
		ammo.y=player.y+player.height;
	}

	public void set_position() {
		player.x = 512;
		player.y = 0;

		ammo.x = 9999;
		ammo.y = 9999;

		enemy.x = 512;
		enemy.y = 720+enemy.height;

		explosion.x = 512;
		explosion.y = 9999;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerImage = new Texture(Gdx.files.internal("player_r.png"));
		player = new Rectangle();
		player.width = 150;
		player.height = 125;

		ammoImage = new Texture(Gdx.files.internal("ammo.png"));
		ammo = new Rectangle();
		ammo.width = 70;
		ammo.height = 70;

		enemyImage = new Texture(Gdx.files.internal("enemy.png"));
		enemy = new Rectangle();
		enemy.width = 70;
		enemy.height = 70;

		explosion = new Rectangle();
		explosion.width = 200;
		explosion.height = 200;

		set_position();

		for(int i=0; i<=25; i++) {
			explosionImages[i] = new Texture(Gdx.files.internal("explosion/explosion" + i + ".png"));
		}
		explosionImage=explosionImages[0];

		fireSound = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.mp3"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameOver.mp3"));

		score=0;

		font = new BitmapFont();
		font.setColor(255,255,255,255);

		gamePaused = false;
		gameOver = false;

		soundPlayed=false;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(playerImage, player.x, player.y);
		batch.draw(ammoImage, ammo.x, ammo.y);
		batch.draw(enemyImage, enemy.x, enemy.y);
		batch.draw(explosionImage, explosion.x, explosion.y);
		font.draw(batch, "Score: " + score, 10, 20);
		if(gameOver) {
			font.draw(batch, "GAME OVER", 560, 360);
			font.draw(batch, "PRESS R TO TRY AGAIN", 560, 340);
			if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				set_position();
				gameOver = false;
				score = 0;
				soundPlayed=false;
			}
		}
		else if(gamePaused) {
			font.draw(batch, "PAUSED", 560, 360);
		}
		batch.end();

		//player movement
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !gamePaused && !gameOver) {}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !gamePaused && !gameOver) {
			player.x -= 300 * Gdx.graphics.getDeltaTime();
			playerImage=new Texture(Gdx.files.internal("player_l.png"));
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !gamePaused && !gameOver) {
			player.x += 300 * Gdx.graphics.getDeltaTime();
			playerImage=new Texture(Gdx.files.internal("player_r.png"));
		}

		//pause
		if(Gdx.input.isKeyJustPressed(Input.Keys.P) && !gameOver) {
			gamePaused = !gamePaused;
		}

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

		if(!gamePaused && !gameOver) ammo.y+=500*Gdx.graphics.getDeltaTime();

		if(!gamePaused && !gameOver) enemy.y-=100*Gdx.graphics.getDeltaTime();

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
		if(enemy.y<=0) {
			if(!soundPlayed){
				gameOverSound.play(1.0f);
				soundPlayed=!soundPlayed;
			}
			gameOver=true;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
		enemyImage.dispose();
		ammoImage.dispose();
		explosionImage.dispose();
		font.dispose();
	}
}
