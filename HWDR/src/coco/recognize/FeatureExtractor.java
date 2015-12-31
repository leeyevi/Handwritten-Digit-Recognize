package coco.recognize;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ������ȡ��
 * @author coco
 *
 */
public interface FeatureExtractor<T> {
	
	/**
	 * ��ȡ����,
	 * <T> ��Ӧ��ֻ��int[], double[] �Ȼ����������͹��ɵļ��ϣ�
	 * Ӧ�ÿ����������Զ�������������Ȼ��ͨ����װ��ģʽ������������࣬
	 * �ٽ���������ȡ --> 2015/12/7 0:12
	 * 
	 * @param bi
	 * @throws IOException
	 * @return ����ֵ
	 */
	public abstract T extract(BufferedImage bi) throws IOException;
}
