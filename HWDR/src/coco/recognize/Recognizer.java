package coco.recognize;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ʶ����
 * @author coco
 *
 */
public interface Recognizer {
	
	/**
	 * �Խ��й���Ӧ������ͼƬ����Ԥ��(ʶ��)
	 * @param bi
	 * @return Ԥ����(0~9)
	 * @throws IOException
	 */
	public abstract String predict(BufferedImage bi) throws IOException;
	
	public abstract void setFeatureExtractor(FeatureExtractor<double[]> fex);
}
