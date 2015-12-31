package coco.binaryz;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����Ostu�㷨�Ķ�ֵ������
 * @author coco
 *
 */
public class OstuBinaryz implements Binaryz {
	/**
	 * �Ҷ�ֵ����
	 */
	private int[][] gray = null;
	
	public OstuBinaryz() {}
	
	@Override
	public BufferedImage binaryzation(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		// ͼ��ҶȻ�
		gray = RgbUtil.graying(bi);
		// ��ȡ��ֵ����ֵ
		int threshold = ostu(this.gray, width, height);
		BufferedImage binBufImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				gray[x][y] = (gray[x][y] > threshold) ? RgbUtil.white : RgbUtil.black;	
				binBufImg.setRGB(x, y, gray[x][y]);
			}
		}
		return binBufImg;
	}
	
	// ͼ���ֵ��(���ڻ�ͼ��)��ʹ��ostu�㷨��ѡȡ���ŷ�ֵthreshold��ʹ������֮�������
	private int ostu(int[][] gray, int width, int height) {
		// ʹ�ûҶ�ͼ����ɫ�ռ��1600W+�����256�֣�����������ѹ��(�任)
		int[] histData  = new int[width * height]; // ������ͬ�Ҷ�ֵ���ֵĸ���,�ûҶ�ֵ��Ϊ�Ǳ�
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int red = gray[x][y] & 0xff; // int(4bytes) 0x000000ff, ֻȡ���8bitֵ
				histData[red]++;
			}
		}
		
		int total = width * height;
		float sum = 0; // ���� ���ص�  �Ҷ�ֵֵ ���� ���ִ�����ֵ���ܺ�
		for (int t = 0; t < 256; t++) {
			sum += t * histData[t];
		}
		
		// �ɷ�ֵthreshold������ǰ��(������Ϣ)�ͱ���(������Ϣ)
		float sumB = 0;
		int wB = 0; // ����Ȩ��
		int wF = 0; // ǰ��Ȩ��
		
		float disMax = 0; // ǰ���뱳��������������ֵ
		int threshold = 0; // ��ֵ
		
		for (int t = 0; t < 256; t++) {
			if ((wB += histData[t]) == 0) continue;
			if ((wF = total - wB) == 0) break;
			
			sumB += (float) (t * histData[t]);
			
			float mB = sumB / wB; // ����ƽ��ֵ
			float mF = (sum - sumB) / wF; // ǰ��ƽ��ֵ
			
			// ����������(ǰ���뱳������ľ���)
			float disBF = (float) wB * (float) wF * (mB - mF) * (mB - mF);
			/*
			 * ͼ�����ƽ���Ҷ�Ϊ��u=w0*u0+w1*u1��
			 * ǰ���ͱ���ͼ��ķ��g=w0*(u0-u)*(u0-u)+w1*(u1-u)*(u1-u)=w0*w1*(u0-u1)*(u0-u1)
			 */
			
			if (disBF > disMax) {
				disMax = disBF;
				threshold = t; // �����������ʱ��ֵ����
			}
		} // for
		return threshold;
	}
}
