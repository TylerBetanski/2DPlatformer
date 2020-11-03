package entity.creatures.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gfx.AnimatedTexture;
import gfx.Camera;
import gfx.Texture;
import input.Keys;
import utils.Utils;

public class Player extends Creature {
	private static Texture idleTexture = new Texture(Assets.PLAYER_IDLE);
	private static Texture crouchTexture = new Texture(Assets.PLAYER_CROUCH);
	private static AnimatedTexture walkingTexture = new AnimatedTexture(Assets.PLAYER_WALK);
	private static Texture jumpingTexture = new Texture(Assets.PLAYER_JUMP);
	private static Texture fallingTexture = new Texture(Assets.PLAYER_FALL);
	private static AnimatedTexture deathTexture = new AnimatedTexture(Assets.PLAYER_DIE);
	private static AnimatedTexture attackTexture = new AnimatedTexture(Assets.PLAYER_WALK_ATTACK);
	private static AnimatedTexture jumpAttackTexture = new AnimatedTexture(Assets.PLAYER_JUMP_ATTACK);
	private static AnimatedTexture fallAttackTexture = new AnimatedTexture(Assets.PLAYER_FALL_ATTACK);

	private static Rectangle standingBounds = new Rectangle(0, 3, 15, 28);
	private static Rectangle crouchingBounds = new Rectangle(0, 9, 15, 22);

	private boolean facingRight = true;
	private boolean crouched = false;

	public Player(GameStateManager gsm, int x, int y) {
		super(gsm, idleTexture, x, y, standingBounds, true);
		maxHealth = 5;
		health = maxHealth;
		speed = 1.0;
		affectedByGravity = true;
		localGravityScale = 1;
		jumpPower = 6;
	}

	@Override
	public void update() {
		handleInput();

		gravity();

		if(jumping || falling) {
			bounds = crouchingBounds;
			tex = crouchTexture;
		} else
			bounds = standingBounds;

		tex.update();
		if(health <= 0)
			dead = true;
	}

	@Override
	public void handleInput() {
		if(Keys.isHeld(Keys.LEFT)) {
			facingRight = false;
			tex = walkingTexture;
			moveX(Entity.Direction.LEFT, speed);
		}
		if(Keys.isHeld(Keys.RIGHT)) {
			facingRight = true;
			tex = walkingTexture;
			moveX(Entity.Direction.RIGHT, speed);
		}
		if(Keys.isPressed(Keys.UP)) {
			jump();
		}

		if(!Keys.isHeld(Keys.LEFT) && !Keys.isHeld(Keys.RIGHT) && !jumping && !falling) {
			walkingTexture.reset();

			if(!crouched)
				tex = idleTexture;
			else
				tex = crouchTexture;
		}
	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			if(facingRight)
				tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());
			else {
				Utils.flipTexture(tex).draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());
			}

			//g.setColor(Color.red);
			//g.drawRect((int)x + (int)bounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY()- (int)camera.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
			//System.out.println("X & Y: "+ x + ", " + y);
			//System.out.println("W & H: "+ bounds.getWidth() + ", " + bounds.getHeight());
		}
	}
}
