package coco.zoom;

import java.awt.image.BufferedImage;

/**
 * ͼ��������
 * @author coco
 *
 */
public interface Zoomer {
	
	/**
	 * ��Դͼ������ΪĿ��ͼ���С
	 * @param bi Դͼ��
	 * @param destW Ŀ��ͼ����
	 * @param destH Ŀ��ͼ��߶�
	 * @param imgType Ŀ��ͼ������ͣ�һ��Ϊ��BufferedImage.TYPE_BYTE_BINARY��TYPE_INT_RGB��TYPE_INT_ARGB��
	 * @return ���ź��ͼ��
	 */
	public abstract BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType);
}
