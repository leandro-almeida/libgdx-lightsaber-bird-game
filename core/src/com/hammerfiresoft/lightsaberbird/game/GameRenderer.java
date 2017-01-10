package com.hammerfiresoft.lightsaberbird.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hammerfiresoft.lightsaberbird.assets.Assets;
import com.hammerfiresoft.lightsaberbird.game.objects.LightSaber;
import com.hammerfiresoft.lightsaberbird.game.objects.Pipe;
import com.hammerfiresoft.lightsaberbird.screens.GameScreen;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	public static float minY, maxY;

	// Referencias para os objetos
	private LightSaber bird;
	private Pipe pipe1, pipe2, pipe3;
	
	private Sprite background;
	private Sprite lightsaber;

	public GameRenderer(GameWorld world) {
		myWorld = world;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameScreen.gameWidth, GameScreen.gameHeight);
		Gdx.app.debug("GAME_INFO", "Camera Viewport -> " + camera.viewportWidth + " x " + camera.viewportHeight);

		camera.update();
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		updateMinMaxY();
		initGameObjects();
	}

	private void initGameObjects() {
		background = Assets.backgrounds[0];
		lightsaber = Assets.lsaber[0];

//		background = Assets.backgrounds[MathUtils.random(0,1)];
//		lightsaber = Assets.lsaber[MathUtils.random(0,3)];
		
		
		// unica saida rapida (POG): pipes estao mt dependentes de viewport Width/Height
		myWorld.createBird(7, 32);
		myWorld.createPipes();

		bird = myWorld.getBird();
		pipe1 = myWorld.getPipe1();
		pipe2 = myWorld.getPipe2();
		pipe3 = myWorld.getPipe3();
		
	}

	public void render(float runtime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// atualiza projecao para a mesma utilizada pela camera (caso a tela sofra resize)
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		
		// Desenha o Background
		batch.disableBlending();
		batch.draw(background, 0, 0, GameScreen.gameWidth, GameScreen.gameHeight);
		batch.enableBlending();

		// Desenha os Tubos de Cima
		batch.draw(Assets.tubeSprite, pipe1.getX(), pipe1.getUpper().y, pipe1.getUpper().getWidth(), pipe1.getUpper().getHeight());
		batch.draw(Assets.tubeSprite, pipe2.getX(), pipe2.getUpper().y, pipe2.getUpper().getWidth(), pipe1.getUpper().getHeight());
		batch.draw(Assets.tubeSprite, pipe3.getX(), pipe3.getUpper().y, pipe3.getUpper().getWidth(), pipe1.getUpper().getHeight());

		// Desenha os Tubos de Cima
		batch.draw(Assets.tubeSprite, pipe1.getX(), pipe1.getLower().y, pipe1.getLower().getWidth(), pipe1.getLower().getHeight());
		batch.draw(Assets.tubeSprite, pipe2.getX(), pipe2.getLower().y, pipe2.getLower().getWidth(), pipe1.getLower().getHeight());
		batch.draw(Assets.tubeSprite, pipe3.getX(), pipe3.getLower().y, pipe3.getLower().getWidth(), pipe1.getLower().getHeight());

		// Desenha o Ground
		batch.disableBlending();
		batch.draw(Assets.groundSprite, 0, 0, GameScreen.gameWidth, Assets.groundSprite.getHeight());
		batch.enableBlending();

		// Desenha o Passaro
		batch.draw(lightsaber, bird.getX(), bird.getY(), bird.getWidth() / 2,
				bird.getHeight() / 2, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

		// Draw Points
		Assets.font.setColor(Color.WHITE);
		Assets.font.setScale(1f);
		Assets.font.setScale( GameScreen.gameWidth / GameScreen.gameHeight );
		Assets.font.draw(batch, String.valueOf(myWorld.getPoints()), camera.viewportWidth / 2, camera.viewportHeight - 10);

		// Caso em DEBUG, renderiza o FPS
		if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
			Assets.font.setColor(Color.WHITE);
			Assets.font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5, 10);
		}
		
//		if (!bird.isAlive()) {
//			Assets.font.setColor(Color.WHITE);
//			Assets.font.draw(batch, "Aperte R para Recomeçar", 5, camera.viewportHeight/2 + 20);
//			Assets.font.draw(batch, "Aperte ESC para Sair", 5, camera.viewportHeight/2);
//		}

		batch.end();

		// Caso em DEBUG, desenha os bounds dos objetos
		if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			
			shapeRenderer.rect(bird.getCollisionBounds().x, bird.getCollisionBounds().y, bird.getCollisionBounds().width, bird.getCollisionBounds().height);
			
			shapeRenderer.rect(pipe1.getUpper().x, pipe1.getUpper().y, pipe1.getUpper().width, pipe1.getUpper().height);
			shapeRenderer.rect(pipe1.getLower().x, pipe1.getLower().y, pipe1.getLower().width, pipe1.getLower().height);
			shapeRenderer.rect(pipe2.getUpper().x, pipe2.getUpper().y, pipe2.getUpper().width, pipe2.getUpper().height);
			shapeRenderer.rect(pipe2.getLower().x, pipe2.getLower().y, pipe2.getLower().width, pipe2.getLower().height);
			shapeRenderer.rect(pipe3.getUpper().x, pipe3.getUpper().y, pipe3.getUpper().width, pipe3.getUpper().height);
			shapeRenderer.rect(pipe3.getLower().x, pipe3.getLower().y, pipe3.getLower().width, pipe3.getLower().height);
			shapeRenderer.end();
		}
	}

	public void updateMinMaxY() {
		minY = camera.viewportHeight - GameWorld.upperTubeH - Pipe.VERTICAL_GAP / 2 + 5;// valor baseado no maior tubo (134px vs 117px)
		maxY = Assets.groundSprite.getHeight() + GameWorld.lowerTubeH + Pipe.VERTICAL_GAP / 2;
		Gdx.app.debug("GAME_INFO", "MinY -> " + minY + " | MaxY -> " + maxY);
	}

	public void screenResized(float gameWidth, float gameHeight) {
		camera.setToOrtho(false, gameWidth, gameHeight);
		camera.update();
		updateMinMaxY();
	}

	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
