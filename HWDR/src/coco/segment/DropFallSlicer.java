package coco.segment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import coco.util.RgbUtil;
/**
 * ����"��ˮ�㷨"�����2�ַ�ճ���ķָ���
 * @author coco
 *
 */
public class DropFallSlicer implements Slicer {
	/**
	 * Ϊ�˱���ˮ���ڰ�ƽ�����Ҳ�ͣ�ƶ�������toRight��־��
	 * ��cantoRight = falseʱ����ʾ���������ƶ�
	 */
	private boolean cantoRight = true;
	/**
	 * ͼ���еĶ�ά���������(x, y)
	 * @author coco
	 *
	 */
	private class Point {
		public int x;
		public int y;
		
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public DropFallSlicer() {}
	
	/**
	 *  ����"��ˮ�㷨"�����2�ַ�ճ���ķָ�
	 */
	@Override
	public ArrayList<BufferedImage> segment(BufferedImage bi) {
		// TODO Auto-generated method stub
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		Point cpos = searchStartPoint(bi);
		// Ѱ�����ĵ���㣬Ҳ���Ƿָ��
		do {
			cpos = nextpos(bi, cpos);
		} while(cpos.y != -1);
		
		// �����и�����ͼƬ����
		ArrayList<BufferedImage> sub_imgs = new ArrayList<BufferedImage>();
		// ����ˮ�ε����յ�������ָ�ճ���ַ�
		BufferedImage subImg = bi.getSubimage(0, 0, cpos.x, height);
		sub_imgs.add(subImg);
		subImg = bi.getSubimage(cpos.x, 0, width - cpos.x, height);
		sub_imgs.add(subImg);
		
		return sub_imgs;
	}

	/**
	 * ���ر���е�ˮ·����ͼ��
	 * @param bi
	 * @param save_path
	 * @return
	 * @throws IOException
	 */
	public BufferedImage drawDropPath(BufferedImage bi, File save_path) throws IOException {
		Point cpos = searchStartPoint(bi);
		// Ѱ�����ĵ���㣬Ҳ���Ƿָ��
		do {
			bi.setRGB(cpos.x, cpos.y, RgbUtil.red); // ������ˮ·��
			cpos = nextpos(bi, cpos);
		} while(cpos.y != -1);
		// д�뱣��λ��
		ImageIO.write(bi, "png", save_path); // �����ʽӦ���Ƕ�̬��ȡ��... 
		return bi;
	}
	
	/**
	 * Ѱ��ˮ�ε������ʼλ��
	 * @param bi
	 * @return
	 */
	private Point searchStartPoint(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		
		// ��ʼ��������ʼλ��
		Point spos = new Point(0, 0);
		/*
		 * ��ʼλ�õ�p�����������
		 * (1) ��p�ǰ�ɫ���أ� cur_rgb = white
		 * (2) ��p��ǰһ�������Ǻ�ɫ����: pre_rgb = black
		 * (3) �ڸ�����(�����겻��������)��p��֮��(����ֱ�Ӻ��)�����ں�ɫ����: behinds_rgb = black
		 */
		int xstart = -1, ystart = -1;
		for (int y = 0; y < height; y++) {
			xstart = -1;
			for (int x = 1; x < width; x++) {
				int pre_rgb = bi.getRGB(x - 1, y);
				int cur_rgb = bi.getRGB(x, y);
				if (xstart == -1 && RgbUtil.isBlack(pre_rgb) && !RgbUtil.isBlack(cur_rgb)) {
					xstart = x;
					ystart = y;
				}
				if (xstart != -1 && RgbUtil.isBlack(cur_rgb)) {
					spos.x = xstart;
					spos.y = ystart;
					break;
				}
			}
			if (spos.x == xstart) {
				break;
			}
		}
		return spos;
	}
	
	/**
	 * ����ˮ�ε������һ��λ��
	 * @param bi
	 * @param cpos ��ǰλ��
	 * @return 
	 */
	private Point nextpos(BufferedImage bi, Point cpos) {
		int xcur = cpos.x;
		int ycur = cpos.y;
		
		// ��ΪĿǰ���зָ�ǻ���x����ģ��������ָ�����x���꣬���Կ�������y�������жϵ�������ı�־
		if (xcur == 0|| ycur == 0 || xcur == bi.getWidth() - 1 || ycur == bi.getHeight() - 1) {
			cpos.y = -1;
			return cpos;
		}
		
		if (!RgbUtil.isBlack(bi.getRGB(xcur, ycur + 1))) { // n3
			cpos.y++;
		} else if (!RgbUtil.isBlack(bi.getRGB(xcur + 1, ycur + 1))) { // n4
			cpos.x++;	cpos.y++;
		} else if (!RgbUtil.isBlack(bi.getRGB(xcur - 1, ycur + 1))) { // n2
			cpos.x--; 	cpos.y++;					
		} else if (cantoRight && !RgbUtil.isBlack(bi.getRGB(xcur + 1, ycur))) { // n5
			cpos.x++;									
		} else if (!RgbUtil.isBlack(bi.getRGB(xcur - 1, ycur))) { // n1
			cpos.x--;												
			cantoRight = false;
		} else { // n3
			cpos.y++;
			cantoRight = true;
		}
		
		return cpos;
	}
}
