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
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(playerImage, player.x, player.y);
		batch.draw(ammoImage, ammo.x, ammo.y);
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
				ammo.x=player.x+50;
				ammo.y=player.y+player.height;
			}
		}

		//borders
		if(player.x < 0) player.x = 0;
		if(player.x > 1280-player.width) player.x = 1280-player.width;
		//if(player.y < 0) player.y = 0;
		//if(player.y > 720-player.width) player.y = 720-player.width;

		//ammo
		ammo.y+=10;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
	}
}
