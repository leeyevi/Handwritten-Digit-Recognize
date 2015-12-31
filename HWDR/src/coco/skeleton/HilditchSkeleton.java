package coco.skeleton;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����Hilditch��ϣ�����桿�㷨��ϸ������ѧ�ԣ�
 * <p>http://www.cnblogs.com/xiaotie/archive/2010/08/12/1797760.html</p>
 * <p> x4 x3 x2 </p>
 * <p> x5 p  x1 </p>
 * <p> x6 x7 x8 </p>
 * @author coco
 *
 */
public class HilditchSkeleton implements Skeleton {
	/**
	 * �洢��ͼ���ж�Ӧ��������rgbֵ������һ��(0��1)��ֵ��
	 * <p> rgbs[x][y] = 1 ����ǰ��ɫ-��ɫ </p>
	 * <p> rgbs[x][y] = 0 ������ɫ-��ɫ </p>
	 * <p> rgbs[x][y] = -1 ������ǣ���ʱ��ǰ��ɫ�ȼ�</p>
	 */
	private int[][] rgbs; 
	
	public HilditchSkeleton() {}
	
	/**
	 * ���㵱ǰ��p��8��ͨ�����������㹫ʽ���£�
	 * <p>(~x7 - ~x7 * ~x8 * ~x1) + sigma(~xk - ~xk * ~x(k+1) * ~x(k+2), k = {1, 3, 5} </p>
	 * <p> ~x ��ʾȡ��x�ķ�ɫ������x��ʾǰ��ɫ(ֵΪ1)ʱ, ~x��ʾ����ɫ(ֵΪ0), �෴Ҳ��� </p>
	 * @param x p�ĺ�����
	 * @param y p��������
	 * @return p��8��ͨ������
	 */
	private int eight_connected_num(int x, int y) {
		/*
		 * ���ǵ�rgbs[][]��ĳЩ��ᱻ���Ϊ-1�����㷨��Ҫ���"��1��0"�����ϣ�
		 * ������Ҫ��һЩ�任��
		 * 	x ��ֵΪ 0, -1 ʱ ��ʾ ����ɫ������: <= 0
		 *  x ��ֵΪ 1 ʱ��ʾ ǰ��ɫ�� ����: > 0
		 * ����������ͨ����0�ıȽϼ���ʵ�ֶ�xȡ��ɫ���ҷ����㷨Ҫ���"��1��0"
		 */
		int x1 = rgbs[x + 1][y] > 0 ? 0 : 1;
		int x2 = rgbs[x + 1][y - 1] > 0 ? 0 : 1;
		int x3 = rgbs[x][y - 1] > 0 ? 0 : 1;
		int x4 = rgbs[x - 1][y - 1] > 0 ? 0 : 1;
		int x5 = rgbs[x - 1][y] > 0 ? 0 : 1;
		int x6 = rgbs[x - 1][y + 1] > 0 ? 0 : 1;
		int x7 = rgbs[x][y + 1] > 0 ? 0 : 1;
		int x8 = rgbs[x + 1][y + 1] > 0 ? 0 : 1;
		
		int sum = x1 - x1 * x2 * x3;
		sum += x3 - x3 * x4 * x5;
		sum += x5 - x5 * x6 * x7;
		sum += x7 - x7 * x8  * x1;
		
		return sum;
	}
	
	@Override
	public BufferedImage skeleton(BufferedImage bi) {
		// TODO Auto-generated method stub
		
		// ��ʼ��rgbs
		rgbs = SkeletonUtil.init_rgbs(bi);
		
		boolean isModified = true;
		int temp = 0;
		do {
			isModified = false;
			// Ϊ�˱���Ǳ�Խ�磬��(1, 1)��ʼ��(width - 2, heigth - 2)����
			for (int x = 1; x < rgbs.length - 1; x++) {
				for (int y = 1; y < rgbs[0].length - 1; y++) {
					if (rgbs[x][y] == 1) {
						/*
						 * (1) ����1��x1, x3, x5, x7 ��ȫ��Ϊ1��ǰ��ɫ��
						 * ��ȫ��Ϊ1���Ұѵ�p(x,y)ɾ����ͼ��ͻ���ֿ���
						 */
						if (
							rgbs[x + 1][y] == 1 &&	// x1
							rgbs[x][y - 1] == 1 &&	// x3
							rgbs[x - 1][y] == 1 &&	// x5
							rgbs[x][y + 1] == 1		// x7
						) continue;
						
						/*
						 * (2) ����2��x1~x8��������2��Ϊ1
						 * ��ֻ��1��Ϊ1�������߶ζ˵㣻��û��Ϊ1�ģ����ǹ�����
						 */
						if (SkeletonUtil.black_neighbour_num(rgbs, x, y) < 2)
							continue;
						
						// (3) ����3����p��8��ͨ������Ϊ1
						if (eight_connected_num(x, y) != 1) continue;
						
						// (4) ����4������x3�Ѿ����Ϊɾ���������Ϊ-1������ô��x3����Ǻ�p��8��ͨ������Ϊ1
						temp = rgbs[x][y - 1];
						rgbs[x][y - 1] = -1; // ���x3
						if (eight_connected_num(x, y) != 1) {
							rgbs[x][y - 1] = temp; // ��ԭx3ֵ
							continue;
						}
						rgbs[x][y - 1] = temp; // ��ԭx3ֵ
						
						// (5) ����5������x5�Ѿ����Ϊɾ���������Ϊ-1������ô��x5����Ǻ�p��8��ͨ������Ϊ1
						temp = rgbs[x - 1][y];
						rgbs[x - 1][y] = -1; // ���x5
						if (eight_connected_num(x, y) != 1) {
							rgbs[x - 1][y] = temp; // ��ԭx5ֵ
							continue;
						}
						rgbs[x - 1][y] = temp; // ��ԭx5ֵ
						
						// (x, y)����ǣ��ȵ���ѭ���������󽫱���ǵ����ر�ɱ���ɫ
						rgbs[x][y] = -1;
						// ֻҪ���б��޸ĵľ���Ҫ����������ֱ����Ҳû�е㱻���
						isModified = true; 
					} // if
				} // for y
			}// for x
			
		} while (isModified);
		
		// ������ǵ����ص��ɱ���ɫ white
		for (int x = 1; x < rgbs.length - 1; x++) {
			for (int y = 1; y < rgbs[0].length - 1; y++) {
				if (rgbs[x][y] == -1) {
					bi.setRGB(x, y, RgbUtil.white);
				}
			}
		}
		return bi;
	}

}
