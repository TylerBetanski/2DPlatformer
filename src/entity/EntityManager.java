package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.creatures.Creature;
import entity.creatures.player.Player;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Camera;

public class EntityManager {

	private GameStateManager gsm;
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public EntityManager(GameStateManager gsm) {
		this.gsm = gsm;
	}

	public void update() {
		for(int i = entities.size() - 1; i >= 0; i--) {
			entities.get(i).update();

			if(i < entities.size()) {
				if(entities.get(i).getY() > ((LevelState)gsm.getCurrentState()).getTileMap().getHeight()) {

					if(entities.get(i).getClass().getSuperclass() == Creature.class) {
						((Creature)entities.get(i)).die();
					}
				}
			}
		}
	}
	
	public void init(LevelState levelState) {
		for(int i = entities.size() - 1; i >= 0; i--) {
			entities.get(i).init(levelState);
		}
	}

	public void draw(Graphics2D g, Camera camera) {
		for(int i = entities.size() - 1; i >= 0; i--) {
			entities.get(i).draw(g, camera);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public Player getPlayer() {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getClass() == Player.class) {
				return (Player)entities.get(i);
			}
		}
		return null;
	}

	public boolean checkPlayerCollision(Entity e) {
		Player player = getPlayer();
		Rectangle playerBounds = new Rectangle((int)(player.getX() + player.getBounds().getX()),
				(int)(player.getY() + player.getBounds().getY()),
				(int)player.getBounds().getWidth(),
				(int)player.getBounds().getHeight());
		Rectangle entityBounds = new Rectangle((int)(e.getX() + e.getBounds().getX()),
				(int)(e.getY() + e.getBounds().getY()),
				(int)e.getBounds().getWidth(),
				(int)e.getBounds().getHeight());
		if(entityBounds.intersects(playerBounds))
			return true;
		else
			return false;
	}

	public ArrayList<Entity> checkEntityCollision(Rectangle Bounds) {
		ArrayList<Entity> entitiesHit = new ArrayList<Entity>();

		for(Entity e: entities) {
			Rectangle entityBounds = new Rectangle((int)(e.getX() + e.getBounds().getX()),
					(int)(e.getY() + e.getBounds().getY()),
					(int)e.getBounds().getWidth(),
					(int)e.getBounds().getHeight());
			if(Bounds.intersects(entityBounds)) {
				entitiesHit.add(e);
			}
		}
		return entitiesHit;
	}
}
