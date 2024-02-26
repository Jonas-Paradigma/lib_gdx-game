package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.actors.Feind;
import com.mygdx.game.actors.Spieler;
import com.mygdx.game.helper.imageHelper;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Texture img;
	Texture img1;
	Texture img2;

	ArrayList<Feind> fList;

	Spieler spieler;

	private AssetManager assetManager;
	private Sound soundEffect;
	private Music backgroundMusic;





	//
	private TextureAtlas atlas;
	private Animation<TextureRegion> animation;
	float elapsedTime = 0.1f;

	@Override
	public void create() {
		// Darstellung
		batch = new SpriteBatch();

		//Sprite Sheet animation
		atlas = new TextureAtlas(Gdx.files.internal("animation/santa_idle.atlas"));
		Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("Armature_Idle");
		animation = new Animation<>(0.01f, frames, Animation.PlayMode.LOOP);


		atlas = new TextureAtlas(Gdx.files.internal("animation/santa_idle.atlas"));

		font = new BitmapFont(Gdx.files.internal("fonts/calibri_blue_30.fnt"), Gdx.files.internal("fonts/calibri_blue_30.png"), false);
		//font = new BitmapFont(Gdx.files.internal("fonts/calibri_green_30.fnt"), Gdx.files.internal("fonts/calibri_green_30.png"), false);


		img1 = new Texture("images/download.png");
		imageHelper ih = new imageHelper();

		fList = new ArrayList<Feind>();
		for (int i = 0;i<5;i++)
			fList.add(new Feind(500, 55, ih.changeImgSize(150, 150, "images/cd3-removebg-preview.png"), 5,fList));
		spieler = new Spieler(500, 35, ih.changeImgSize(150, 150, "images/crusher.png"));


		assetManager = new AssetManager();
		assetManager.load("sounds/headshot.mp3", Sound.class);
		assetManager.load("music/Chipi chapa cat.mp3", Music.class);
		assetManager.finishLoading();

		soundEffect = assetManager.get("sounds/headshot.mp3", Sound.class);
		backgroundMusic = assetManager.get("music/Chipi chapa cat.mp3", Music.class);

		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}


	int hits = 0;
	int missed = 0;



	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		spieler.act(delta);

		if(Gdx.input.isKeyPressed(Input.Keys.A) && spieler.getX()>0) {
			//Gdx.app.log("input","left key pressed");
			spieler.move(1);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)&& spieler.getX()<1070) spieler.move(0);


		// check for collision
		for (Feind f :fList){
			if (spieler.collideRectangle(f.getBoundary())){
				soundEffect.play();
				f.setRandomPosition();
				hits++;
			}
		}

		// Update
		for (Feind feind : fList) {
			feind.act(delta);
		}

		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img1, 0, 0);

		elapsedTime += delta;


		for (Feind feind : fList) {
			feind.draw(batch, delta);
			if(feind.getY() < 3){
				missed++;
			}
		}
		spieler.draw(batch, 1);

		font.draw(batch, "Treffer: "+hits,0,700);
		font.draw(batch, "Missed: "+missed,0,660);


		TextureRegion currentFrame = animation.getKeyFrame(elapsedTime);
		batch.draw(currentFrame, 100, 100);


		// Your game logic goes here

		// Play sound effect when space key is pressed
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			soundEffect.play();
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		// Dispose of resources when the application is closed
		assetManager.dispose();
		soundEffect.dispose();
		backgroundMusic.dispose();
		atlas.dispose();
	}
}
