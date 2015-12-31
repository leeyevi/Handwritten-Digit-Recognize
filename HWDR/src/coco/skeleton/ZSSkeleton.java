package coco.skeleton;

import java.awt.image.BufferedImage;

import coco.util.RgbUtil;

/**
 * ����ZS�㷨��ϸ����
 * <p>p9 p2 p3</p>
 * <p>p8 p1 p4</p>
 * <p>p7 p6 p5</p>
 * @author coco
 *
 */
public class ZSSkeleton implements Skeleton {
	/**
	 * �洢��ͼ���ж�Ӧ��������rgbֵ������һ��(0��1)��ֵ��
	 * <p> rgbs[x][y] = 1 ����ǰ��ɫ-��ɫ </p>
	 * <p> rgbs[x][y] = 0 ������ɫ-��ɫ </p>
	 * <p> rgbs[x][y] = -1 ������ǣ���ʱ��ǰ��ɫ�ȼ�</p>
	 */
	private int[][] rgbs; 
	
	/**
	 * constructor
	 */
	public ZSSkeleton() {}

	/**
	 * ����p2,p3,...,p9,p2ÿ2��������ɵ����У��磺
	 * <p> <\p2,p3> <\p3,p4> ... <\p9,p2> </p>
	 * <p> ��������<\ǰ��ɫ,����ɫ>���еĸ�����������Ҳ�ȼ���<\��ɫ,��ɫ> </p>
	 * @param rgbs �����˴�����ͼƬ������������Ӧ�����ص�rgbֵ
	 * @param x ����p1�ĺ�����x
	 * @param y ����p1��������y
	 * @return ����<\ǰ��ɫ,����ɫ>���еĸ���
	 */
	private int seq_neighbour_num(int x, int y) {
		int count = 0;
		
		// Ϊ�˱���Խ�磬��ɨ��biʱ��Ӧ�ô�(1,1)��ʼɨ��,��(width-2, height-2)����
		// ����ʹ�� != 1 ����Ϊ��ϸ��������Ҫ�ԣ�����ȥ���ĵ㣩���б�ǣ�������ֱ����rgbs�ϱ�ǵģ����Ϊ -1����0�ǵȼ۵ģ�
		if (rgbs[x][y - 1] != 1 && rgbs[x + 1][y - 1] == 1) count++; // <p2,p3>
		if (rgbs[x + 1][y - 1] != 1 && rgbs[x + 1][y] == 1) count++; // <p3,p4>
		if (rgbs[x + 1][y] != 1 && rgbs[x + 1][y + 1] == 1) count++; // <p4,p5>
		if (rgbs[x + 1][y + 1] != 1 && rgbs[x][y + 1] == 1) count++; // <p5,p6>
		if (rgbs[x][y + 1] != 1 && rgbs[x - 1][y + 1] == 1) count++; // <p6,p7>
		if (rgbs[x - 1][y + 1] != 1 && rgbs[x - 1][y] == 1) count++; // <p7,p8>
		if (rgbs[x - 1][y] != 1 && rgbs[x - 1][y - 1] == 1) count++; // <p8,p9>
		if (rgbs[x - 1][y - 1] != 1 && rgbs[x][y - 1] == 1) count++; // <p9,p2>
		
		return count;
	}
	
	@Override
	public BufferedImage skeleton(BufferedImage bi) {
		// ��ʼ��rgbs
		rgbs = SkeletonUtil.init_rgbs(bi);
		
		boolean isModified = true;
		do {
			isModified = false;
			// Ϊ�˱���Ǳ�Խ�磬��(1, 1)��ʼ��(width - 2, heigth - 2)����
			for (int x = 1; x < rgbs.length - 1; x++) {
				for (int y = 1; y < rgbs[0].length - 1; y++) {
					if (rgbs[x][y] == 1) {
						
						// (1) ����1��2 <= B(p1) <= 6
						// B(p1) ���� black_neighbour_num(p1)
						int black_nei_num = SkeletonUtil.black_neighbour_num(rgbs, x, y);
						if (black_nei_num < 2 || black_nei_num > 6) continue;
						
						// (2) ����2: A(p1) == 1
						// A(p1) ���� seq_neighbour_num(p1)
						if (seq_neighbour_num(x, y) != 1) continue;
						
						/* (3)' ������(1)(2)�������£��������������һ����������
						 * a) p2 * p4 * p6 == 0 && p4 * p6 * p8 == 0 [��,��]
						 * b) p2 * p4 * p8 == 0 && p2 * p6 * p8 == 0 [��,��]
						 * ��ע������3��ֵ��˵���-1ʱҲ�㣬��Ϊ-1���������Ҫɾ���ĵ㣬�뱳��ɫ�ȼ�
						 */
						if (
							// !a)
							(rgbs[x][y - 1] * rgbs[x + 1][y] * rgbs[x][y + 1] == 1 ||
							 rgbs[x + 1][y] * rgbs[x][y + 1] * rgbs[x - 1][y] == 1) 
							 && // !b)
							(rgbs[x][y - 1] * rgbs[x + 1][y] * rgbs[x - 1][y] == 1 ||
							 rgbs[x][y - 1] * rgbs[x][y + 1] * rgbs[x - 1][y] == 1)
						) continue;
						
						// (x, y)����ǣ��ȵ���ѭ���������󽫱���ǵ����ر�ɱ���ɫ
						rgbs[x][y] = -1;
						// ֻҪ���б��޸ĵľ���Ҫ����������ֱ����Ҳû�е㱻���
						isModified = true; 
					} // if
				} // for y
			} // for x
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
