package coco.binaryz;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����Bernsen�㷨�Ķ�ֵ������
 * @author coco
 *
 */
public class BernsenBinaryz implements Binaryz {
	/**
	 * �Ҷ�ֵ����
	 */
	private int[][] gray;
	/**
	 * wsize�����˴��ڴ�С
	 * <p>���ڴ�СΪ : (2 * wsize + 1) * (2 * wsize + 1) </p>
	 */
	public int wsize = 1;
	/**
	 * �������������ػҶ�ֵ�����ֵ��ȥ��Сֵ�Ĳ�ķ�ֵ��
	 * <p> d_vaule = threshold about : max_gray - min_gray </p>
	 */
	public int d_value = 15;
	/**
	 * ����ͼƬ�Ҷ�ֵ��ȫ�ַ�ֵ
	 */
	public int global_threshold = 128;
	/**
	 * �������洰���ڻҶ�ֵ�������Сֵ
	 */
	private static MaxMinGray max_min_gray = new MaxMinGray();
	
	public BernsenBinaryz() {}
	
	/**
	 * ������¼�����ڻҶ�ֵ�����ֵ����Сֵ
	 * @author coco
	 *
	 */
	private static class MaxMinGray {
		public int max_gray;
		public int min_gray;
	}
	
	/**
	 * constructor
	 * @param wsize �������ڴ�С�����ڴ�СΪ��(2 * wsize + 1) * (2 * wsize + 1)
	 * @param d_value �������������ػҶ�ֵ�����ֵ��ȥ��Сֵ�Ĳ�
	 * @param global_threshold ����ͼƬ�Ҷ�ֵ��ȫ�ַ�ֵ
	 */
	public BernsenBinaryz(int wsize, int d_value, int global_threshold) {
		this.wsize = wsize;
		this.d_value = d_value;
		this.global_threshold = global_threshold;
	}
	
	@Override
	public BufferedImage binaryzation(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		// ��ȡͼ��Ҷ�ֵ
		gray = RgbUtil.graying(bi);
		BufferedImage binBufImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// ��ȡ�����С�Ҷ�ֵ
				max_min_grays(x, y, width, height);
				int avg_gray = (max_min_gray.max_gray + max_min_gray.min_gray) >> 1;
			
				if (max_min_gray.max_gray - max_min_gray.min_gray > d_value) {
					// ��ǰ��ĻҶ�ֵ��ֵΪ T = agv_gray
					gray[x][y] = gray[x][y] > avg_gray ? RgbUtil.white : RgbUtil.black;
				} else {
					// ƽ���Ҷ�ֵavg_gray��ȫ�ַ�ֵglobal_threshold�Ƚ�
					gray[x][y] = avg_gray > global_threshold ? RgbUtil.white : RgbUtil.black;
				}
				binBufImg.setRGB(x, y, gray[x][y]);
			}
		}
		return binBufImg;
	}
	
	/**
	 * ���㴰�����������ػҶ�ֵ�����ֵ����Сֵ
	 * @param x �������ĵ�p�ĺ�����x
	 * @param y �������ĵ�p��������y
	 * @param width ͼ����
	 * @param height ͼ��߶�
	 * @return ����max_gray��min_gray���ڲ������
	 */
	private MaxMinGray max_min_grays(int x, int y, int width, int height) {
		max_min_gray.max_gray = 0;
		max_min_gray.min_gray = 255;
		
		int xleft = (x - wsize) < 0 ? x : (x - wsize);
		int xright = (x + wsize) >= width ? x : (x + wsize);
		int yup = (y - wsize) < 0 ? y : (y - wsize);
		int ydown = (y + wsize) >= height ? y : (y + wsize);
		
		for (int i = xleft; i <= xright; i++) {
			for (int j = yup; j <= ydown; j++) {
				int grayPi = gray[i][j];
				if (grayPi > max_min_gray.max_gray) {
					max_min_gray.max_gray = grayPi;
				} 
				if (grayPi < max_min_gray.min_gray) {
					max_min_gray.min_gray = grayPi;
				}
			}
		}
		return max_min_gray;
	}
}
