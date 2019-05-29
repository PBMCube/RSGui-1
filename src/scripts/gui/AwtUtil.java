package scripts.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.tribot.api.General;

public class AwtUtil {
	public static BufferedImage getImage(String name) {
		try {
			General.println("Loading image: " + name);
			return ImageIO.read(AwtUtil.class.getClassLoader().getResource(name));
		} catch (IOException e) {
			General.println(name + " not loaded. ");
		}
		return null;
	}

	public static BufferedImage getImage(URL name) {
		if (name == null)
			return null;
		try {
			General.println("Loading image: " + name);
			return ImageIO.read(name);
		} catch (IOException e) {
			General.println(name + " not loaded. ");
		}
		return null;
	}

	public static BufferedImage getImage(BufferedImage source, int x, int y, int w, int h) {
		BufferedImage ret = new BufferedImage(w, h, 2);
		Graphics g = ret.getGraphics();
		g.drawImage(source, -x, -y, null);
		return ret;
	}
	
	public static BufferedImage getImage(byte[] data) {
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(data);
			return ImageIO.read(stream);
		} catch (IOException e) {
			General.println("image could not load.");
		}
		return null;
	}

	public static Font getFont(String name) {
		Font font = null;
		try {
			General.println("Loading font: " + name);
			URL url = AwtUtil.class.getClassLoader().getResource(name);

			return getFont(url);
		} catch (Exception ex) {
			ex.printStackTrace();
			General.println(name + " not loaded.  Using serif font.");
			font = new Font("serif", 0, 16);
		}
		return font;
	}

	public static Font getFont(URL url) {
		try {
			InputStream stream = url.openStream();
			Font font = Font.createFont(0, stream);
			return font.deriveFont(0, 16.0F);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Font getFont(byte[] array) {
		Font font = null;
		try {
			InputStream useStream = new ByteArrayInputStream(array);
			font = Font.createFont(0, useStream);
			font = font.deriveFont(0, 16.0F);
		} catch (Exception ex) {
			ex.printStackTrace();
			General.println("font not loaded. Using serif font.");
			font = new Font("serif", 0, 16);
		}
		return font;
	}

	public static void clearImage(BufferedImage image) {
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setComposite(AlphaComposite.getInstance(1));
		g2.setColor(new Color(0, 0, 0, 0));
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2.setComposite(AlphaComposite.getInstance(3));
	}

	public static BufferedImage generateMask(BufferedImage image, Color col) {
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), 2);
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				if ((rgb >> 24 & 0xFF) > 0)
				{
					img.setRGB(x, y, col.getRGB());
				}
			}
		}
		return img;
	}
}
