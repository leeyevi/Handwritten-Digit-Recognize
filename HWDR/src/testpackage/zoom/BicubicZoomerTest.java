package testpackage.zoom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import coco.zoom.BicubicZoomer;

public class BicubicZoomerTest {
	public static void main(String[] args) throws IOException {
		String path = null; // Ҫ������Ҫ�Լ���д·��
		BicubicZoomer _zoomer = new BicubicZoomer();
		
		BufferedImage bi = ImageIO.read(new File(path));
		long startTime = System.currentTimeMillis();
		ImageIO.write(_zoomer.zoom(bi, 22, 22, BufferedImage.TYPE_BYTE_BINARY), 
				"bmp", new File(path + "xxx"));
		long endTime = System.currentTimeMillis();
		System.out.println("over, spend time = " + (endTime - startTime) + "ms");
	}
}
