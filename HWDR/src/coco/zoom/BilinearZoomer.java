package coco.zoom;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����˫���Բ�ֵ�㷨����������ѧ�ԣ�
 * <p> http://blog.csdn.net/jia20003/article/details/6915185 </p>
 * <p> ԴͼƬΪ(Sw*Sh)���أ�Ŀ��ͼƬΪ(Dw*Dh)���� </p>
 * <p> �������ڽ���ֵ�㷨�������Դ���ص�����
 * 		(x,y)��Sx = Dx * (Sw / Dw)�� Sy = Dy * (Sh / Dh)����ͬ����(x,y)��Ϊ������ </p>
 * <p> Ŀ�����ص��ֵD(x,y)���ɹ�ʽ��
 * <p> D(x,y) = 
 * 		S(ltp_x,ltp_y)*ltp_weight + S(lbp_x,lbp_y)*lbp_weight + 
 * 	   	S(rtp_x,rtp_y)*rtp_weight + S(rbp_x,rbp_y)*rbp_weight �����
 * ����ltp�����Ͻǣ�, lbp�����½ǣ�, rtp�����Ͻǣ�, rbp�����½ǣ�Ϊ����Դ���ص�(x,y)������ĸ����ص�
 * </p>
 * 
 * @author coco
 *
 */
public class BilinearZoomer implements Zoomer {
	
	public BilinearZoomer() {}

	@Override
	public BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType) {
		// TODO Auto-generated method stub
		int srcW = bi.getWidth();
		int srcH = bi.getHeight();

		// ��ȡsrc_argbs
		int[][][] src_argbs = RgbUtil.decompose_argbs(bi);
		// ����dest_argbs
		int[][][] dest_argbs = new int[destW][destH][4];
		
		// x�����ϵı�������y�����ϵı���������������᣺���Ͻ�Ϊ(0,0)��x��������ˮƽ���ң�y����������ֱ����
		float x_ratio = ((float) (srcW)) / ((float) (destW));
		float y_ratio = ((float) (srcH)) / ((float) (destH));

		for (int x = 0; x < destW; x++) {
			double src_x = ((float) x) * x_ratio;
			/*
			 * ltp : left top point
			 * ltp_x Ϊ����src(x,y)�����4�����е������Ͻǵĵ�ĺ�����
			 */
			int ltp_x = (int) Math.floor(src_x);
			
			// src_xdecimal Ϊ��������õ���Դ���ص�src(x,y)�������С������
			double src_xdecimal = src_x - (double) ltp_x;
			
			for (int y = 0; y < destH; y++) {
				double src_y = ((float) y) * y_ratio;
				// ltp_y ͬ��
				int ltp_y = (int) Math.floor(src_y);
				// src_ydecimal ͬ��
				double src_ydecimal = src_y - (double) ltp_y;
				
				/*
				 * ltp: left top point ���Ͻǵ�
				 * lbp: left bottom point ���½ǵ�
				 * rtp: right top point ���Ͻǵ�
				 * rbp: right bottom point ���½ǵ�
				 */
				double ltp_weight = (1.0d - src_xdecimal) * (1.0d - src_ydecimal);
				double lbp_weight = src_xdecimal * (1.0d - src_ydecimal);
				double rtp_weight = (1.0d - src_xdecimal) * src_ydecimal;
				double rbp_weight = src_xdecimal * src_ydecimal;
				
				int clip_ltp_x = ZoomerMath.clip(ltp_x, srcW - 1, 0);
				int clip_ltp_y = ZoomerMath.clip(ltp_y, srcH - 1, 0);
				int clip_rtp_x = ZoomerMath.clip(ltp_x + 1, srcW - 1, 0); // rtp �� ltp ����y��rbp �� rtp ����x
				int clip_lbp_y = ZoomerMath.clip(ltp_y + 1, srcH - 1, 0); // lbp �� ltp ����x��rbp �� lbp ����y
				
				dest_argbs[x][y][0] = // alpha
						(int) (src_argbs[clip_ltp_x][clip_ltp_y][0] * ltp_weight) + // ltp_alpha
						(int) (src_argbs[clip_ltp_x][clip_lbp_y][0] * lbp_weight) + // lbp_alpha
						(int) (src_argbs[clip_rtp_x][clip_ltp_y][0] * rtp_weight) + // rtp_alpha
						(int) (src_argbs[clip_rtp_x][clip_lbp_y][0] * rbp_weight);  // rbp_alpha
				
				dest_argbs[x][y][1] = // red
						(int) (src_argbs[clip_ltp_x][clip_ltp_y][1] * ltp_weight) + // ltp_red
						(int) (src_argbs[clip_ltp_x][clip_lbp_y][1] * lbp_weight) + // lbp_red
						(int) (src_argbs[clip_rtp_x][clip_ltp_y][1] * rtp_weight) + // rtp_red
						(int) (src_argbs[clip_rtp_x][clip_lbp_y][1] * rbp_weight);  // rbp_red
				
				dest_argbs[x][y][2] = // green
						(int) (src_argbs[clip_ltp_x][clip_ltp_y][2] * ltp_weight) + // ltp_green
						(int) (src_argbs[clip_ltp_x][clip_lbp_y][2] * lbp_weight) + // lbp_green
						(int) (src_argbs[clip_rtp_x][clip_ltp_y][2] * rtp_weight) + // rtp_green
						(int) (src_argbs[clip_rtp_x][clip_lbp_y][2] * rbp_weight);  // rbp_green
				
				dest_argbs[x][y][3] = // blue
						(int) (src_argbs[clip_ltp_x][clip_ltp_y][3] * ltp_weight) + // ltp_blue
						(int) (src_argbs[clip_ltp_x][clip_lbp_y][3] * lbp_weight) + // lbp_blue
						(int) (src_argbs[clip_rtp_x][clip_ltp_y][3] * rtp_weight) + // rtp_blue
						(int) (src_argbs[clip_rtp_x][clip_lbp_y][3] * rbp_weight);  // rbp_blue
			} // for y
		} // for x

		BufferedImage zoom_img = new BufferedImage(destW, destH, imgType);
		int[][] dest_rgbs = RgbUtil.combine_argbs(dest_argbs);
		for (int x = 0; x < destW; x++) {
			for (int y = 0; y < destH; y++) {
				zoom_img.setRGB(x, y, dest_rgbs[x][y]);
			}
		}
		return zoom_img;
	}
	
}
