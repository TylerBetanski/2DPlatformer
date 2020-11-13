package entity.creatures.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gfx.AnimatedTexture;
import gfx.Camera;
import gfx.Texture;
import input.Keys;
import main.GamePanel;

public class Player extends Creature {

	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	private static Texture idleTexture = new Texture(Assets.PLAYER_IDLE);
	private static AnimatedTexture walkingTexture = new AnimatedTexture(Assets.PLAYER_WALK);
	private static Texture jumpTexture = new Texture(Assets.PLAYER_JUMP);
	private static AnimatedTexture deathTexture = new AnimatedTexture(Assets.PLAYER_DIE);
	private static AnimatedTexture attackTexture = new AnimatedTexture(Assets.PLAYER_ATTACK);
	private static AnimatedTexture jumpAttackTexture = new AnimatedTexture(Assets.PLAYER_JUMP_ATTACK);
	private static  Rectangle standingBounds = new Rectangle(0, 2, 15, 29);
	private static Rectangle crouchingBounds = new Rectangle(0, 9, 15, 22);
	private static Rectangle attackBoundsRight = new Rectangle(16, 8, 48, 8);
	private static Rectangle attackBoundsLeft = new Rectangle(-49, 8, 48, 8);
	private static Rectangle attackBounds = attackBoundsRight;

	private boolean facingRight = true;
	private boolean attacking = false;
	private boolean crouching = false;
	private boolean moving = false;

	public Player(GameStateManager gsm, int x, int y) {
		super(gsm, idleTexture, x, y, standingBounds, true);

		textures.add(idleTexture);
		textures.add(walkingTexture);
		textures.add(jumpTexture);
		textures.add(deathTexture);
		textures.add(attackTexture);
		attackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {0, 0, 0});
		textures.add(jumpAttackTexture);
		jumpAttackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {-7, -7, -7});

		maxHealth = 5;
		health = maxHealth;
		speed = 1;
		affectedByGravity = true;
		localGravityScale = 1;
		jumpPower = 6;
	}

	private void attack() {
		if(!attacking) {
			attacking = true;
			if(facingRight)
				attackBounds = attackBoundsRight;
			else
				attackBounds = attackBoundsLeft;
		} else {
			if(tex.animationEnded()) {
				attacking = false;
			}
		}
	}

	@Override
	public void update() {
		handleInput();
		updateTextures();
		tex.update();
		gravity();

		if(attacking)
			attack();

		if(health <= 0)
			dead = true;
	}

	private void updateTextures() {
		if(moving && !(jumping || falling || crouching || attacking)) {
			bounds = standingBounds;
			tex = walkingTexture;
		} else if(!moving && !(jumping || falling || crouching || attacking)) {
			bounds = standingBounds;
			tex = idleTexture;
		} else if((jumping || falling || crouching) && !attacking) {
			bounds = crouchingBounds;
			tex = jumpTexture;
		} else if(attacking && !(jumping || falling || crouching)) {

			if(tex.equals(jumpAttackTexture)) {
			
			} else {
				bounds = standingBounds;
				tex = attackTexture;
			}
		} else if(attacking &&(jumping || falling || crouching)) {
			bounds = crouchingBounds;
			tex = jumpAttackTexture;
		}

		for(Texture t: textures) {
			if(!tex.equals(t)) {
				t.reset();
			}
		}
	}

	@Override
	protected void handleInput() {
		if(Keys.isHeld(Keys.ACTION1))
			attack();

		if(Keys.isHeld(Keys.LEFT) && !Keys.isHeld(Keys.RIGHT) && (!attacking || jumping)) {
			moveX(Entity.Direction.LEFT, speed);
			facingRight = false;
			moving = true;
		}
		else if(Keys.isHeld(Keys.RIGHT) && !Keys.isHeld(Keys.LEFT) && (!attacking || jumping)) {
			moveX(Entity.Direction.RIGHT, speed);
			facingRight = true;
			moving = true;
		} else
			moving = false;
		if(Keys.isPressed(Keys.UP) && !attacking) {
			jump();
		}

		if(Keys.isHeld(Keys.DOWN)) {
			if(!(jumping || falling || attacking)) {
				crouching = true;
			}
		} else if(!attacking) {
			crouching = false;
		}
	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			if(facingRight)
				tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());
			else
				tex.draw(g, (int)x + 16 - (int)camera.getX(), (int)y - (int)camera.getY(), -tex.getWidth(), tex.getHeight());

			if(GamePanel.DEBUG_RENDERBOXES) {
				g.setColor(Color.RED);
				g.drawRect((int)x + (int)bounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY()- (int)camera.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());

				if(attacking) {
					g.setColor(Color.GREEN);
					g.drawRect((int)x + (int)bounds.getX() + (int)attackBounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY() + (int)attackBounds.getY()- (int)camera.getY(), (int)attackBounds.getWidth(), (int)attackBounds.getHeight());
				}
			}
		}
	}
}
