package coco.segment;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * �ָ���
 * @author coco
 *
 */
public interface Slicer {
	/**
	 * ͼ��ָ����д����ָ�����ɸ���ͼƬ����
	 * @param bi ���ָ�Ķ���
	 * @return ������ͼƬ����������
	 */
	public abstract ArrayList<BufferedImage> segment(BufferedImage bi);
}
