package coco.recognize.bpnetwork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import coco.recognize.FeatureExtractor;
import coco.recognize.PatternFactory;
import coco.recognize.fextractor.CoarseMeshFeatureExtractor;

/**
 * Bp������ģ�͹���
 * @author coco
 *
 */
public class BpPatternFactory extends PatternFactory<double[], BpPattern> {

	public BpPatternFactory() {
		this(new CoarseMeshFeatureExtractor());		
	}
	
	/**
	 * Constructor
	 * @param fex ������ȡ��
	 */
	public BpPatternFactory(FeatureExtractor<double[]> fex) {
		super(fex);
	}
	
	/**
	 * ����Bp�������ѵ���Ͳ�������
	 * @param sample
	 * @return
	 * @throws IOException
	 */
	@Override
	public BpPattern produce_sample(File sample) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedImage img = ImageIO.read(sample);
		double[] inputs = produce_inputmodel(img);
		double[] outputs = toOutputs(sample.getName());
		return new BpPattern(inputs, outputs);
	}
	
	/**
	 * ����ͼ��Ԥ��(ʶ��)�����������ֵ����(ģ��),���磺
	 * <p>Bp�������е�����ģ��Ϊ��double[]</p>
	 * @param bi
	 * @return
	 * @throws IOException
	 */
	@Override
	public double[] produce_inputmodel(BufferedImage bi) throws IOException {
		return fex.extract(bi);
	}
	
	private double[] toOutputs(String fileName) {
		double[] outputs = new double[10];
		int value = Integer.parseInt(fileName.substring(0, 1));
		for (int i = 0; i < 10; i++) {
			outputs[i] = (i == value) ? 1 : 0;
		}
		return outputs;
	}
	
}
