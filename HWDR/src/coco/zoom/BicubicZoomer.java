package coco.zoom;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����˫������ֵ�㷨������������Ҫѧ�ԣ�
 * <p> http://blog.csdn.net/jia20003/article/details/40020775 </p>
 * <p> Ŀ��ͼ��(x,y)��������任��ӳ�䵽Դͼ��(i+u,j+v)������i,jΪ��������u,vΪ(0,1)֮���С�� </p>
 * <p> ���ݹ�ʽ��D(x,y) = S(i+u,j+v) = sigmaS(i+m,j+n)R(u-m)R(v-n)������m,nΪ[-1,2]������ </p>
 * <p> R(x) Ϊ��ֵ�˺������������У� ����ȡֵ��Bell�ֲ����ʽ��B�������߱��ʽ�� <p>
 * @author coco
 *
 */
public class BicubicZoomer implements Zoomer {
	/**
	 * ��������ȡֵ�Ĳ�ֵ�˺���
	 */
	public static final int interp_type_triangle = 1;
	/**
	 * ����Bell�ֲ������Ĳ�ֵ�˺���
	 */
	public static final int interp_type_bell = 2;
	/**
	 * ����B�������߲����Ĳ�ֵ�˺���
	 */
	public static final int interp_type_bspline = 3;
	/**
	 * ����catmull-Rom�������ߵĲ�ֵ�˺���
	 */
	public static final int interp_type_catmullRom = 4;
	/**
	 * ����sin(x*Pi)/x �����ƽ��Ĳ�ֵ�˺���
	 */
	public static final int interp_type_sinCloser = 5;
	/**
	 * ��ֵ�˺�������
	 */
	private int interp_type = interp_type_sinCloser;
	
	public BicubicZoomer() {}
	
	@Override
	public BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType) {
		// TODO Auto-generated method stub
		int srcW = bi.getWidth();
		int srcH = bi.getHeight();
		
		// ��ȡsrc_argbs
		int[][][] src_argbs = RgbUtil.decompose_argbs(bi);
		// ����dest-argbs
		int[][][] dest_argbs = new int[destW][destH][4];
		
		// x�����ϵı�������y�����ϵı���������������᣺���Ͻ�Ϊ(0,0)��x��������ˮƽ���ң�y����������ֱ����
		float x_ratio = ((float) (srcW)) / ((float) (destW));
		float y_ratio = ((float) (srcH)) / ((float) (destH));
		
		for (int x = 0; x < destW; x++) {
			double src_x = ((float) x) * x_ratio;
			// src_x ����������
			int src_xinteger = (int) Math.floor(src_x);
			// src_x ��С������
			double src_xdecimal = src_x - (double) src_xinteger;
			
			for (int y = 0; y < destH; y++) {
				double src_y = ((float) y) * y_ratio;
				// src_y ����������
				int src_yinteger = (int) Math.floor(src_y);
				// src_y ��С������
				double src_ydecimal = src_y - (double) src_yinteger;
						
				// ����16���ٽ��㣬���ݲ�ֵ�˺���R(x)���D(x,y)��ֵ
				for (int m = -1; m < 3; m++) {
					for (int n = -1; n < 3; n++) {
						double rx = 0.0d; // rx = R(src_xdecimal - m)
						double ry = 0.0d; // ry = R(src_ydecimal - n)
						
						rx = interpCalc((double) (src_xdecimal - m), interp_type);
						ry = interpCalc((double) (src_ydecimal - n), interp_type);
						
						int clip_x = ZoomerMath.clip(src_xinteger - m, srcW - 1, 0);
						int clip_y = ZoomerMath.clip(src_yinteger - n, srcH - 1, 0);
						
						dest_argbs[x][y][0] += (int) (src_argbs[clip_x][clip_y][0] * rx * ry); // alpha
						dest_argbs[x][y][1] += (int) (src_argbs[clip_x][clip_y][1] * rx * ry); // red
						dest_argbs[x][y][2] += (int) (src_argbs[clip_x][clip_y][2] * rx * ry); // green
						dest_argbs[x][y][3] += (int) (src_argbs[clip_x][clip_y][3] * rx * ry); // blue
					} // for n
				} // for m
				dest_argbs[x][y][0] = saturate(dest_argbs[x][y][0]); // alpha ���͵� [0, 255]
				dest_argbs[x][y][1] = saturate(dest_argbs[x][y][1]); // red ���͵� [0, 255]
				dest_argbs[x][y][2] = saturate(dest_argbs[x][y][2]); // green ���͵� [0, 255]
				dest_argbs[x][y][3] = saturate(dest_argbs[x][y][3]); // blue ���͵� [0, 255]
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
	
	/**
	 * ��value���͵�[0, 255]��Χ��
	 * @param value
	 * @return
	 */
	private static int saturate(int value) {
		return value > 255 ? 255 : value < 0 ? 0 : value;
	}
	
	public void setInterpType(int interpType) {
		interp_type = interpType;
	}
	
	private static double interpCalc(double x, int select) {
		switch (select) {
		case interp_type_triangle:
			return ZoomerMath.triangleInterp(x);
		case interp_type_bell:
			return ZoomerMath.bellInterp(x);
		case interp_type_bspline:
			return ZoomerMath.bsplineInterp(x);
		case interp_type_sinCloser:
			return ZoomerMath.sinCloserInterp(x);
		case interp_type_catmullRom:
			return ZoomerMath.catmullRomInterp(x);
		default:
			return ZoomerMath.sinCloserInterp(x);	
		}
	}
}
