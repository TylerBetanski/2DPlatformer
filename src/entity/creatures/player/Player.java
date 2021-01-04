package entity.creatures.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import gfx.Camera;
import gfx.Texture;
import input.Keys;
import main.GamePanel;
import tiles.Tile;
import tiles.TileMap;

public class Player extends Creature {

	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	private static Texture idleTexture = new Texture(Assets.PLAYER_IDLE);
	private static AnimatedTexture walkingTexture = new AnimatedTexture(Assets.PLAYER_WALK);
	private static Texture jumpTexture = new Texture(Assets.PLAYER_JUMP);
	private static Texture stairsUpStandingTexture = new Texture(Assets.PLAYER_IDLE_ASCEND_STAIRS);
	private static Texture stairsDownStandingTexture = new Texture(Assets.PLAYER_IDLE_DESCEND_STAIRS);
	private static AnimatedTexture stairsUpWalkingTexture = new AnimatedTexture(Assets.PLAYER_WALKING_ASCEND_STAIRS);
	private static AnimatedTexture stairsDownWalkingTexture = new AnimatedTexture(Assets.PLAYER_WALKING_DESCEND_STAIRS);
	private static AnimatedTexture deathTexture = new AnimatedTexture(Assets.PLAYER_DIE);
	private static AnimatedTexture attackTexture = new AnimatedTexture(Assets.PLAYER_ATTACK);
	private static AnimatedTexture jumpAttackTexture = new AnimatedTexture(Assets.PLAYER_JUMP_ATTACK);
	private static AnimatedTexture stairsUpAttackTexture = new AnimatedTexture(Assets.PLAYER_ASCEND_STAIRS_ATTACK);
	private static AnimatedTexture stairsDownAttackTexture = new AnimatedTexture(Assets.PLAYER_DESCEND_STAIRS_ATTACK);
	private static  Rectangle standingBounds = new Rectangle(0, 2, 15, 29);
	private static Rectangle crouchingBounds = new Rectangle(0, 9, 15, 22);
	private static Rectangle attackBoundsRight = new Rectangle(16, 8, 48, 2);
	private static Rectangle attackBoundsLeft = new Rectangle(-49, 8, 48, 2);
	private static Rectangle attackBounds = attackBoundsRight;

	private boolean attacking = false;
	private boolean crouching = false;
	private boolean moving = false;
	private boolean onStairs = false;
	private boolean stairsUp = false;

	public Player(GameStateManager gsm, int x, int y, boolean sideFacing) {
		super(gsm, idleTexture, x, y, standingBounds, true);

		textures.add(idleTexture);
		textures.add(walkingTexture);
		textures.add(jumpTexture);
		textures.add(deathTexture);
		textures.add(attackTexture);
		attackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {0, 0, 0});
		textures.add(jumpAttackTexture);
		jumpAttackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {-7, -7, -7});
		textures.add(stairsUpAttackTexture);
		stairsUpAttackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {0, 0, 0});
		textures.add(stairsDownAttackTexture);
		stairsDownAttackTexture.setOrigin(new int[] {16, 16, 0}, new int[] {0, 0, 0});

		facingRight = sideFacing;

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

			// Attack
			ArrayList<Entity> entitiesHit = 
					((LevelState)gsm.getCurrentState()).getEntityManager().checkEntityCollision(new Rectangle(
							(int)(x + bounds.getX() + attackBounds.getX()),
							(int)(y + bounds.getY() + attackBounds.getY()),
							(int)attackBounds.getWidth(),
							(int)attackBounds.getHeight()));

			if(tex.getCurrentIndex() == 2) {
				for(Entity e : entitiesHit) {
					((Creature)e).hurt(this);
				}
			}
			if(tex.animationEnded()) {
				attacking = false;
			}
		}
	}

	@Override
	public void die() {
		respawn();
	}

	private void respawn() {
		/*
		attacking = false;
		crouching = false;
		moving = false;
		health = maxHealth;
		 */

		state.reload();
	}

	@Override
	public void hurt(Entity e) {
		if(!invulnerable && !dying) {
			if(attacking)
				attacking = false;
			health--;
			knockback();
			if(e.getX() >= x)
				hitFromRight = false;
			else
				hitFromRight = true;

			if(health <= 0)
				die();
			else {
				tex.invert(true);
				invulnerable = true;
				stunned = true;
				System.out.println(this.getClass().getSimpleName()+" has been hurt! Health Remaining: "+health);
			}
		}
	}

	int invulnerableFrames = Creature.invulnerabilityFrames;
	@Override
	public void updateLogic() {
		handleInput();
		updateTextures();
		tex.update();
		if(attacking)
			attack();
	}

	private void updateTextures() {
		boolean invert = tex.isInverted();
		if(onStairs) {
			bounds = standingBounds;
			if(moving && !attacking) {
				if(stairsUp)
					tex = stairsUpWalkingTexture;
				else
					tex = stairsDownWalkingTexture;
			} else if(!(moving || attacking)) {
				if(stairsUp)
					tex = stairsUpStandingTexture;
				else
					tex = stairsDownStandingTexture;
			} else if(attacking) {
				if(stairsUp)
					tex = stairsUpAttackTexture;
				else
					tex = stairsDownAttackTexture;
			}
		} else {
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
		}

		if(invert)
			tex.invert(true);

		for(Texture t: textures) {
			if(!tex.equals(t)) {
				t.reset();
			}
		}
	}

	private boolean stairsEntered = false; // True for in Stairs, false for from above
	private boolean canEnterStairs(boolean direction) {
		int checkX = 0;
		if(facingRight)
			checkX = (int)(x + bounds.getX() + bounds.getWidth());
		else
			checkX = (int)x;

		if((state.getTileMap().getTile(checkX, (int)y + 31).isStairTile() && direction == true)
				|| (state.getTileMap().getTile(checkX, (int)y + 36).isStairTile() && direction == false)) {
			stairsEntered = direction;
			return true;
		}
		return false;
	}

	private boolean stairsSlope = false; // True for Up & to Right, false for Down & to Right
	private void enterStairs() {
		TileMap tileMap = state.getTileMap();
		int check = 0;
		if(stairsEntered) {
			stairsUp = true;
			check = 0;
		} else {
			stairsUp = false;
			check = 1;
		}
		int currentStairX = 0;
		int currentStairY = (int)((y + bounds.getY() + bounds.getHeight() + check) / Tile.TILE_SIZE);
		if(facingRight)
			currentStairX = (int)((x + bounds.getX() + bounds.getWidth()) / Tile.TILE_SIZE);
		else
			currentStairX = (int)(x) / Tile.TILE_SIZE;

		if(tileMap.getTileAtIndex(currentStairX + 1, currentStairY + 1).isStairTile()
				|| tileMap.getTileAtIndex(currentStairX - 1, currentStairY - 1).isStairTile()) {
			stairsSlope = false;
		}
		else if(tileMap.getTileAtIndex(currentStairX + 1, currentStairY - 1).isStairTile()
				|| tileMap.getTileAtIndex(currentStairX - 1, currentStairY + 1).isStairTile()) {
			stairsSlope = true;
		}

		onStairs = true;
		affectedByGravity = false;
	}

	private void stairsLogic(Entity.Direction direction) {
		TileMap tileMap = state.getTileMap();
		double dx = 0;
		double dy = 0;
		if(direction == Entity.Direction.RIGHT) {
			if(stairsSlope) {
				dx = (speed / Math.sqrt(2));
				dy = -dx;
			} else {
				dx = (speed / Math.sqrt(2));
				dy = dx;
			}
		} else {
			if(stairsSlope) {
				dx = -(speed / Math.sqrt(2));
				dy = -dx;
			} else {
				dx = -(speed / Math.sqrt(2));
				dy = dx;
			}
		}

		if(!tileMap.getTile((int)(x + bounds.x + bounds.getWidth() + dx), (int)(y + bounds.getY() + bounds.getHeight() + dy)).isSolid()
				&&!tileMap.getTile((int)(x + dx), (int)(y + bounds.getY() + bounds.getHeight() + dy)).isSolid()
				&& (tileMap.getTile((int)(x + bounds.getX() + bounds.getWidth() + dx), (int)(y + bounds.getY() + bounds.getHeight() + dy + 1)).isStairTile()
						||tileMap.getTile((int)(x + dx), (int)(y + bounds.getY() + bounds.getHeight() + dy + 1)).isStairTile())) {
			x = x + dx;
			y = y + dy;
		} else {
			onStairs = false;
			affectedByGravity = true;
		}
	}

	@Override
	protected void handleInput() {
		if(onStairs) {
			if(Keys.isHeld(Keys.LEFT) && !attacking) {
				facingRight = false;
				stairsLogic(Entity.Direction.LEFT);
				moving = true;
			} else if(Keys.isHeld(Keys.RIGHT) && !attacking) {
				facingRight = true;
				stairsLogic(Entity.Direction.RIGHT);
				moving = true;
			} else
				moving = false;

			if(Keys.isPressed(Keys.ACTION1))
				attack();

			if(Keys.isPressed(Keys.UP) && !attacking) {
				if(checkMapCollision((int)x, (int)y, (int)x, (int)y)) {
					onStairs = false;
					affectedByGravity = true;
				}
			}
		} else {

			if(Keys.isPressed(Keys.ACTION1))
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
			if(Keys.isPressed(Keys.UP) && canEnterStairs(true)) {
				enterStairs();
			} else if(Keys.isPressed(Keys.UP) && !attacking) {
				jump();
			}
			if(Keys.isPressed(Keys.DOWN) && canEnterStairs(false)) {
				enterStairs();
			}
			if(Keys.isHeld(Keys.DOWN)) {
				if(!(jumping || falling || attacking)) {
					crouching = true;
				}
			} else if(!attacking) {
				crouching = false;
			}
		}
	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			if(facingRight)
				tex.draw(g, (int)(x - camera.getX()), (int)(y - camera.getY()));
			else
				tex.draw(g, (int)(x + 16 - camera.getX()), (int)(y - camera.getY()), -tex.getWidth(), tex.getHeight());

			if(GamePanel.DEBUG_RENDERBOXES) {
				g.setColor(Color.RED);
				g.drawRect((int)(x + bounds.getX() - camera.getX()), (int)(y + bounds.getY()- camera.getY()), (int)bounds.getWidth(), (int)bounds.getHeight());

				if(attacking) {
					g.setColor(Color.GREEN);
					g.drawRect((int)(x + bounds.getX() + attackBounds.getX() - camera.getX()), (int)(y + bounds.getY() + attackBounds.getY() - camera.getY()), (int)attackBounds.getWidth(), (int)attackBounds.getHeight());
				}
			}
		}
	}



	@Override
	public Entity clone(boolean sideFacing) {
		Entity returnEntity = new Player(gsm, (int)x, (int)y, sideFacing);
		return returnEntity;
	}
}
