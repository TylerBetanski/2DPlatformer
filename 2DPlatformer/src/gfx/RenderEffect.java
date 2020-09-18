package gfx;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RenderEffect {

	public static BufferedImage colorScale(BufferedImage image, Color colorFrom, Color colorTo) {
		Color pixelColor;
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				double ratio;
				ratio = (new Color(image.getRGB(x, y)).getRed())
						+(new Color(image.getRGB(x, y)).getGreen())
						+(new Color(image.getRGB(x, y)).getBlue());
				ratio /= (double)(255.0 * 3);
				
				pixelColor = new Color(
						(int)(ratio * colorTo.getRed() + (1 - ratio) * colorFrom.getRed()),
						(int)(ratio * colorTo.getGreen() + (1 - ratio) * colorFrom.getGreen()),
						(int)(ratio * colorTo.getBlue() + (1 - ratio) * colorFrom.getBlue())
						);
				
				
				image.setRGB(x, y, pixelColor.getRGB());
			}
		}
		
		return image;
	}

	public static BufferedImage colorMask(BufferedImage image, int colorChannel) { // 0 = Red, 1 = Green, 2 = Blue
		Color pixelColor;
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				pixelColor = new Color(image.getRGB(x, y));
				if(colorChannel == 0) {
					image.setRGB(x, y, new Color(pixelColor.getRed(), 0, 0).getRGB());
				} else if(colorChannel == 1) {
					image.setRGB(x, y, new Color(0, pixelColor.getGreen(), 0).getRGB());
				} else if(colorChannel == 2) {
					image.setRGB(x, y, new Color(0, 0, pixelColor.getBlue()).getRGB());
				}
			}
		}

		return image;
	}
}
