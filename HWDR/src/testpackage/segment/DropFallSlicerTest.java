package testpackage.segment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import coco.segment.DropFallSlicer;

public class DropFallSlicerTest {
	public static void main(String[] args) throws IOException {
		String path = "F:\\Handwritten_Digits_Recognition\\segement\\��ճ���ַ�\\tup2\\";
		DropFallSlicer dfs = new DropFallSlicer();
		BufferedImage bi = ImageIO.read(new File(path + "38.png"));
		
		long startTime = System.currentTimeMillis();
		// �ָ�õ�����ͼƬ
		ArrayList<BufferedImage> sub_imgs = dfs.segment(bi);
		// ������ͼƬ
		int count = 0;
		for (BufferedImage sbi : sub_imgs) {
			ImageIO.write(sbi, "png", new File(path + (count++) + "_dropfall_38.png"));
		}
		
		// ��ȡ���е�ˮ·������ͼƬ
		dfs.drawDropPath(bi, new File(path + "droppath_38.png"));
		long endTime = System.currentTimeMillis();
		System.out.println("over, spend time = " + (endTime - startTime) + "ms");
	}
}
