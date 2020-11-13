package entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.creatures.player.Player;
import gfx.Camera;

public class EntityManager {
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public EntityManager(Player player) {
		entities.add(player);
	}
	
	public void update() {
		for(int i = entities.size() - 1; i >= 0; i--) {
			entities.get(i).update();
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
}
