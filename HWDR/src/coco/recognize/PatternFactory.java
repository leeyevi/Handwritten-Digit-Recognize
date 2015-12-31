package coco.recognize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ģ�͹���
 * @author coco
 *
 * @param <V> ����ʶ�������ֵģ��
 * @param <E> ����ѵ���Ͳ��Ե�����ģ��
 */
public abstract class PatternFactory<V, E> {
	/**
	 * ��Ʒ������ȡ��
	 */
	protected FeatureExtractor<double[]> fex = null;
	
	public PatternFactory(FeatureExtractor<double[]> fex) {
		this.fex = fex;
	}
	
	/**
	 * ����ͼ��Ԥ��(ʶ��)�����������ֵ����(ģ��)
	 * @param bi
	 * @return
	 * @throws IOException
	 */
	public abstract V produce_inputmodel(BufferedImage bi) throws IOException;
	
	/**
	 * ��������ѵ������Ե�����ģ�ͣ���������ģֵ�ͺ�Ŀ��ֵ
	 * @param sample
	 * @return
	 * @throws IOException
	 */
	public abstract E produce_sample(File sample) throws IOException;
	
	public void setFeatureExtractor(FeatureExtractor<double[]> fex) {
		this.fex = fex;
	}
}
