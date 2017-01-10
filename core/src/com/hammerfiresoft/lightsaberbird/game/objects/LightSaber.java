package com.hammerfiresoft.lightsaberbird.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.hammerfiresoft.lightsaberbird.assets.Assets;
import com.hammerfiresoft.lightsaberbird.screens.GameScreen;

public class LightSaber {

	// CONSTANTES
	private static final float FLAP_VELOCITY = 140f; // velocidade a ser usada quando o passaro subir (flap)
	private static final Vector2 acceleration = new Vector2(0, -480f); // atuara como gravidade, por isso tera valor negativo
	
	// Atributos
	private float width;
	private float height;
	private Vector2 position;
	private Vector2 velocity;
	
	private float rotation;
	private Rectangle collisionBounds; // usado para detectar colisao
	private boolean alive = true; // se o passaro esta vivo

	public LightSaber(float x, float y, float width, float height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		collisionBounds = new Rectangle(x, y, width/2, height);

		Gdx.app.debug("BIRD_INFO", "X,Y("+ x + "," + y + ") | WxH(" + width + " x " + height + ")" );
	}

	public void update(float delta) {
		applyGravity(delta);
		applyRotation(delta);
	}

	private void applyGravity(float delta) {
		// y += aceleracao da gravidade * delta
		velocity.add(acceleration.cpy().scl(delta));
		position.add(velocity.cpy().scl(delta));

		if (position.y - height/2 <= Assets.groundSprite.getHeight()) {
			position.y = Assets.groundSprite.getHeight() - height/2;
		} else if (position.y > GameScreen.gameHeight) {
			position.y = GameScreen.gameHeight;
		}

		// atualiza posicao do circle para colisoes
		collisionBounds.x = position.x + width/2;
		collisionBounds.y = position.y;
	}

	private void applyRotation(float delta) {
		// se passarao caindo/subindo, rotaciona o passaro
		if (isFalling()) {
			rotation -= 280 * delta;
			if (rotation < -90)
				rotation = -90;
		} else {
			rotation += 600 * delta;
			if (rotation > 0)
				rotation = 0;
		}
	}

	public void doFlap() {
		if (alive) {
			Assets.soundFlap[MathUtils.random(0,2)].play();
			velocity.y = FLAP_VELOCITY; // da um "up" no bird (aumenta a velocidade em um tempo t)
		}
	}

	public void die() {
		alive = false;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}

	public Rectangle getCollisionBounds() {
		return collisionBounds;
	}

	public boolean isAlive() {
		return alive;
	}

	private boolean isFalling() {
		return (velocity.y < 0);
	}

	public boolean shouldntFlap() {
		return isFalling();
	}

}
