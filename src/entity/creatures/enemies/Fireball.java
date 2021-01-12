package entity.creatures.enemies;


import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Texture;

public class Fireball extends Creature {

	private static Texture texture = new Texture(Assets.FIREBALL);

	public Fireball(GameStateManager gsm, Texture tex, double x, double y, int maxHealth, double speed, double gravityScale, boolean solid) {
		super(gsm, tex, x, y, maxHealth, speed, gravityScale, solid);
	}

	public Fireball(GameStateManager gsm, int x, int y, boolean sideFacing) {
		super(gsm, texture, x, y, 1, 1, 0, true);
	}

	@Override
	public void hurt(Entity e) {

	}
	
	@Override
	public void die() {
		((LevelState)gsm.getCurrentState()).getEntityManager().removeEntity(this);
	}

	private void move() {
		if(facingRight && checkMapCollision((int)(x + speed), (int)y, (int)x, (int)y)) {
			moveX(Entity.Direction.RIGHT, speed);
		} else if(!facingRight && checkMapCollision((int)(x - speed), (int)y, (int)x, (int)y)) {
			moveX(Entity.Direction.LEFT, speed);
		} else {
			die();
		}
	}

	@Override
	public void updateLogic() {
		if(((LevelState)gsm.getCurrentState()).getEntityManager().checkPlayerCollision(this)) {
			((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().hurt(this);
			die();
		}
		move();
	}

	@Override
	public Entity clone(boolean sideFacing) {
		Entity returnEntity = new Fireball(gsm, (int)x, (int)y, sideFacing);
		return returnEntity;
	}

}
