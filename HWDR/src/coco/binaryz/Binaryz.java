package coco.binaryz;

import java.awt.image.BufferedImage;

/**
 * ��ֵ����
 * @author coco
 *
 */
public interface Binaryz {
	
	/**
	 * ͼ���ֵ��
	 * @param bi ����ֵ����ͼ��
	 * @return ��ֵ�����ͼ��
	 */
	public abstract BufferedImage binaryzation(BufferedImage bi);
}
