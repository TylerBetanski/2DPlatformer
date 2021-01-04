package utils;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import assets.Assets;
import gfx.AnimatedTexture;
import gfx.Flipbook;
import gfx.Texture;
import tiles.Tile;
import tiles.Tileset;

public class Utils {

	public static String loadFileAsString(String path){
		InputStream input = Utils.class.getResourceAsStream(path);
		StringBuilder builder = new StringBuilder();
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
			String line;
			while((line = bufferedReader.readLine()) != null)
				builder.append(line + "\n");

			bufferedReader.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static void convertImageToTilemap(BufferedImage image) {
		int widthInTiles = image.getWidth() / Tile.TILE_SIZE;
		int heightInTiles = image.getHeight() / Tile.TILE_SIZE;
		ArrayList<Tileset> tilesets = Assets.TILESETS;

		System.out.println(widthInTiles + " " + heightInTiles);

		// Loop through image to get every Tile
		for(int yTile = 0; yTile < heightInTiles; yTile++) {
			int count = 0;
			for(int xTile = 0; xTile < widthInTiles; xTile++) {
				boolean hasTile = false;

				// Loop through all Tilesets in tilesets
				for(int tilesetIndex = 0; tilesetIndex < tilesets.size(); tilesetIndex++) {
					// Loop through all Tiles in each Tileset
					Tileset currentTileset = tilesets.get(tilesetIndex);
					for(int tileIndex = 0; tileIndex < currentTileset.getTiles().length; tileIndex++) {
						Tile currentTile = currentTileset.getTile(tileIndex);
						boolean pixelMatching = false;

						// Loop through every Pixel in Tiles from Image and Tileset
						for(int xPixel = 0; xPixel < Tile.TILE_SIZE; xPixel++) {
							for(int yPixel = 0; yPixel < Tile.TILE_SIZE; yPixel++) {
								int imageRGB = image.getRGB(xTile * Tile.TILE_SIZE + xPixel, yTile * Tile.TILE_SIZE + yPixel);
								int tileRGB = currentTile.getTexture().getImage().getRGB(xPixel, yPixel);
								pixelMatching = (imageRGB == tileRGB);

								if(!pixelMatching)
									break;
							}
							if(!pixelMatching)
								break;
						}
						if(pixelMatching) {
							hasTile = true;
							System.out.print(tilesetIndex + "#" + tileIndex + " ");
							count++;
							break;
						}
					}
					if(hasTile)
						break;
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
		Texture returnedTexture = new Texture(flippedImage);
		returnedTexture.setOrigin(-returnedTexture.getOriginX(), returnedTexture.getOriginY());

		return new Texture(flippedImage);
	}

	public static AnimatedTexture flipTexture(AnimatedTexture texture) {
		Flipbook flipbook = texture.getFlipbook();
		Texture[] textures = new Texture[flipbook.getTextures().length];
		int[] frameCounts =  new int[textures.length];
		for(int i = 0; i < textures.length; i++) {
			textures[i] = Utils.flipTexture(flipbook.getTextureAtIndex(i));
			frameCounts[i] = flipbook.getFrameCount()[i];
		}
		Flipbook returnedFlipbook = new Flipbook(textures, frameCounts);
		return new AnimatedTexture(returnedFlipbook);
	}
}
