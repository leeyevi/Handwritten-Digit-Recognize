package coco.util;

import java.awt.image.BufferedImage;

public class RgbUtil {
	/**
	 * ��ɫ
	 */
	public static final int white = 0xffffffff;
	/**
	 * ��ɫ
	 */
	public static final int black = 0xff000000;
	/**
	 * ��ɫ
	 */
	public static final int red = 0xffff0000;
	/**
	 * ǰ��ɫ�Ͻ磺ǰ��ɫһ��Ϊ��ɫ������ͼƬ�����ز�һ���Ǵ���ɫ(0x00000000)��
	 * ���Զ�����һ���Ͻ磬Ҳ����˵rgbС�����ֵ�����ر���Ϊ�Ǻ�ɫ����
	 */
	public static final int foreground_color_upper = 0xff303030;
	
	public static boolean isBlack(int rgb) {
		return isBlack(rgb, foreground_color_upper);
	}
	
	/**
	 * �ж�����rgb�Ƿ�Ϊ��ɫ
	 * @param rgb
	 * @param black_threshold ����rgb�Ƿ�Ϊ��ɫ�ķ�ֵ
	 * @return 
	 */
	public static boolean isBlack(int rgb, int black_threshold) {
		return rgb <= black_threshold ? true : false;
	}
	
	/**
	 * ͼ��ҶȻ�
	 * @param bi ���ҶȻ���ͼ��
	 * @return ����ͼ������ػҶ�ֵ�ľ���gray[][]
	 */
	public static int[][] graying(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		int[][] gray = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int argb = bi.getRGB(x, y);
				int r = (argb >> 16) & 0xff;
				int g = (argb >> 8) & 0xff;
				int b = argb & 0xff;
				int grayPixel = (int) ((b * 29 + g * 150 + r * 77 + 128) >> 8);
				gray[x][y] = grayPixel;
			}
		}
		return gray;
	}
	
	/**
	 * ��ͼ��bi����λ�õ�argbֵ�ֽ�Ϊ������ʽ��
	 * <p> argbs[x][y][0] ��ʾ alpha </p>
	 * <p> argbs[x][y][1] ��ʾ red </p>
	 * <p> argbs[x][y][2] ��ʾ green </p>
	 * <p> argbs[x][y][3] ��ʾ blue </p>
	 * @param bi
	 * @return
	 */
	public static int[][][] decompose_argbs(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		int[][][] argbs = new int[width][height][4];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int argb = bi.getRGB(x, y);
				argbs[x][y][0] = (argb >> 24) & 0xff; // alpha
				argbs[x][y][1] = (argb >> 16) & 0xff; // red
				argbs[x][y][2] = (argb >> 8) & 0xff; // green
				argbs[x][y][3] = argb & 0xff; //blue
			}
		}
		return argbs;
	}
	
	/**
	 * ���ֽ�õ���argbs[x][y][0~4]�ϲ�Ϊrgbs[x][y]������
	 * <p> ��argbs�е� alpha, red, green, blue �ϲ� </p> 
	 * @param argbs
	 * @return
	 */
	public static int[][] combine_argbs(int[][][] argbs) {
		int width = argbs.length;
		int height = argbs[0].length;
		
		int[][] rgbs = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				rgbs[x][y] = 
					((argbs[x][y][0] << 24) & 0xff000000) | 
					((argbs[x][y][1] << 16) & 0x00ff0000) | 
					((argbs[x][y][2] << 8) & 0x0000ff00) | 
					((argbs[x][y][3]) & 0x000000ff);
			}
		}
		return rgbs;
	}
}
