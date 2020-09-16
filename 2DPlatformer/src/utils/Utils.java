package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

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

}
