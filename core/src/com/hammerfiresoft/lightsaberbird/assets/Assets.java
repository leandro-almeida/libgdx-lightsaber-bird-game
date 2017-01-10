package com.hammerfiresoft.lightsaberbird.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

	private static AssetManager manager;

	static {
		manager = new AssetManager();
	}

	// Fonts
	public static BitmapFont font;

	// Sprites
	public static TextureAtlas textureAtlas;

	public static Sprite[] backgrounds;
	public static Sprite[] lsaber;
//	public static Sprite upperTubeSprite01, lowerTubeSprite01;
//	public static Sprite upperTubeSprite02, lowerTubeSprite02;
//	public static Sprite upperTubeSprite03, lowerTubeSprite03;
	public static Sprite tubeSprite;
	public static Sprite groundSprite;

	// Sons
	public static Sound[] soundFlap;
	public static Sound	soundHit, soundPoint, soundStart;

	public static void loadAllAssets() {
		loadFonts();
		loadTextureAtlas();
		loadSounds();
		manager.finishLoading();

		assignAssets();
		createSprites();
	}

	private static void loadFonts() {
		font = new BitmapFont();
	}

	private static void loadTextureAtlas() {
		manager.load("data/lightsabersprite.pack", TextureAtlas.class);
	}

	private static void loadSounds() {
		manager.load("data/swing01.wav", Sound.class);
		manager.load("data/swing02.wav", Sound.class);
		manager.load("data/swing03.wav", Sound.class);
		manager.load("data/hit.wav", Sound.class);
		manager.load("data/start.wav", Sound.class);
		manager.load("data/point.wav", Sound.class);
	}

	private static void assignAssets() {
		textureAtlas = manager.get("data/lightsabersprite.pack", TextureAtlas.class);

		soundFlap = new Sound[3];
		soundFlap[0] = manager.get("data/swing01.wav", Sound.class);
		soundFlap[1] = manager.get("data/swing02.wav", Sound.class);
		soundFlap[2] = manager.get("data/swing03.wav", Sound.class);
		soundHit = manager.get("data/hit.wav", Sound.class);
		soundPoint = manager.get("data/point.wav", Sound.class);
		soundStart = manager.get("data/start.wav", Sound.class);
	}

	private static void createSprites() {
		backgrounds = new Sprite[1];
		backgrounds[0] = textureAtlas.createSprite("background");
//		backgrounds[1] = textureAtlas.createSprite("bg2");
		groundSprite = textureAtlas.createSprite("ground");

		tubeSprite = textureAtlas.createSprite("tube");
		
		/*upperTubeSprite01 = textureAtlas.createSprite("tube_0_blue");
		upperTubeSprite02 = textureAtlas.createSprite("tube_0_green");
		upperTubeSprite03 = textureAtlas.createSprite("tube_0_red");
		lowerTubeSprite01 = textureAtlas.createSprite("tube_1_blue");
		lowerTubeSprite02 = textureAtlas.createSprite("tube_1_green");
		lowerTubeSprite03 = textureAtlas.createSprite("tube_1_red");*/

		lsaber = new Sprite[1];
		lsaber[0] = textureAtlas.createSprite("lightsaber");
//		lsaber[1] = textureAtlas.createSprite("lightsaber_green");
//		lsaber[2] = textureAtlas.createSprite("lightsaber_red");
//		lsaber[3] = textureAtlas.createSprite("lightsaber_pink");
	}

	public static void dispose() {
		manager.dispose();
	}
}
