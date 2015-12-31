package coco.skeleton;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

public class SkeletonUtil {
	
	/**
	 * ��ͼ��bi����λ�õ�rgbͨ��ĳһ��ֵ��ֵ��Ϊ1������ǰ��ɫ����0��������ɫ����
	 * ����Щ1��0�����ڶ�ά����rgbs����Ӧλ���ϣ�������rgbs
	 * @param bi
	 * @return 
	 */
	public static int[][] init_rgbs(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		int[][] rgbs = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// ��ԭrgb����ֵ����Ϊ <ǰ��ɫ������ɫ>
				int rgb = bi.getRGB(x, y);
				rgbs[x][y] = RgbUtil.isBlack(rgb) ? 1 : 0;
			}
		}
		return rgbs;
	}
	
	/**
	 * ͳ�����ص�p(x,y)����(8����)�к�ɫ���ص����(ǰ��ɫΪ��ɫ)
	 * @param x ����p�ĺ�����x
	 * @param y ����p��������y
	 * @return p��8�����к�ɫ���ص����
	 */
	public static int black_neighbour_num(int[][] rgbs, int x, int y) {
		int count = 0;
		
		// Ϊ�˱���Խ�磬��ɨ��biʱ��Ӧ�ô�(1,1)��ʼɨ��,��(width-2, height-2)����
		if (rgbs[x][y - 1] == 1) count++; // ��
		if (rgbs[x + 1][y - 1] == 1) count++; // �J
		if (rgbs[x + 1][y] == 1) count++; // ��
		if (rgbs[x + 1][y + 1] == 1) count++; // �K
		if (rgbs[x][y + 1] == 1) count++; // ��
		if (rgbs[x - 1][y + 1] == 1) count++; // �L
		if (rgbs[x - 1][y] == 1) count++; // ��
		if (rgbs[x - 1][y - 1] == 1) count++; // �I
		
		return count;
	}
}
