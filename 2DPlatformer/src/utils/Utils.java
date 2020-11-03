package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import assets.Assets;
import gfx.Texture;
import tiles.Tile;

public class Utils {

	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();

		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String line;
			while((line = bufferedReader.readLine()) != null)
				builder.append(line + "\n");

			bufferedReader.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static BufferedImage loadImage(String path) {

		try {
			return ImageIO.read(Utils.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static void convertImageToTilemap(BufferedImage image) {
		System.out.println(image.getWidth() / Tile.TILE_SIZE + " " + image.getHeight() / Tile.TILE_SIZE);

		for(int y = 0; y < image.getHeight() / Tile.TILE_SIZE; y++) {
			for(int x = 0; x < image.getWidth() / Tile.TILE_SIZE; x++) {

				if(new Color(image.getRGB(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE)).equals(Color.decode("#FF00FF"))) {
					System.out.print("000 ");
				} else {
					for(int tilesetIndex = 0; tilesetIndex < Assets.TILESETS.size(); tilesetIndex++) {
						for(int tileIndex = 0; tileIndex < Assets.TILESETS.get(tilesetIndex).getTiles().length; tileIndex++) {
							BufferedImage tileImage = Assets.TILESETS.get(tilesetIndex).getTile(tileIndex).getTexture().getImage();
							
							boolean matching = true;
							for(int xPixel = 0; xPixel < Tile.TILE_SIZE; xPixel++) {
								if(matching) {
									for(int yPixel = 0; yPixel < Tile.TILE_SIZE; yPixel++) {
										//System.out.println("X: " + x + ", Y:" + y);
										//System.out.println("xPixel: " + xPixel + ", yPixel: " + yPixel);
										//System.out.println("Pixel Coords: " + (x * Tile.TILE_SIZE + xPixel) + ", " + (y * Tile.TILE_SIZE + yPixel));
										
										//System.out.println("ImageRGB: "+image.getRGB(x * Tile.TILE_SIZE + xPixel,y * Tile.TILE_SIZE + yPixel));
										//System.out.println("TileRGB: "+tileImage.getRGB(xPixel, yPixel));
										if(image.getRGB(x * Tile.TILE_SIZE + xPixel,y * Tile.TILE_SIZE + yPixel) != tileImage.getRGB(xPixel, yPixel))
											matching = false;
									}
								} else
									break;
							}
							if(matching) {
								System.out.print(tilesetIndex+"#"+tileIndex+" ");
							}
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	public static Texture flipTexture(Texture texture) {
		BufferedImage flippedImage = new BufferedImage(texture.getImage().getWidth(), texture.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < flippedImage.getWidth(); x++) {
			for(int y = 0; y < flippedImage.getHeight(); y++) {
				flippedImage.setRGB(x, y, texture.getImage().getRGB(flippedImage.getWidth() - x - 1, y));
			}
		}
		
		return new Texture(flippedImage);
	}

}
