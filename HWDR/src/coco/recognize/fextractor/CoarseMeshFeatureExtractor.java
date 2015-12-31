package coco.recognize.fextractor;

import java.awt.image.BufferedImage;
import java.io.IOException;

import coco.recognize.FeatureExtractor;
import coco.util.RgbUtil;

/**
 * ������������ȡ��
 * @author coco
 *
 */
public class CoarseMeshFeatureExtractor implements FeatureExtractor<double[]> {
	/**
	 * ���������С��Ϊ����������߳�
	 * <p>��λΪ����λ���س���</p>
	 */
	private int wsize = 2;
	/**
	 * ��������
	 */
	private double[] feaVector;
	
	public CoarseMeshFeatureExtractor() {}
	
	public CoarseMeshFeatureExtractor(int wsize) {
		this.wsize = wsize;
	}
	
	@Override
	public double[] extract(BufferedImage bi) throws IOException {
		// TODO Auto-generated method stub

		int width = bi.getWidth();
		int height = bi.getHeight();
		double bpiNum = 0;
		feaVector = new double[(width / wsize) * (height / wsize)];
		
		int x = 0, y = 0;
		for (int k = 0; k < feaVector.length; k++) {
			bpiNum = 0;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (RgbUtil.isBlack(bi.getRGB(i + x, j + y))) bpiNum++;
				}
			}
			feaVector[k] = bpiNum;
			if (x == (width - 2)) { y += 2; x = 0; } 
			else { x += 2; }
		}
		return feaVector;
	}
	
	public int getWsize() {
		return wsize;
	}
	
	public void setWsize(int wsize) {
		this.wsize = wsize;
	}
}
