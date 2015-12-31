package coco.recognize.svm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import coco.recognize.FeatureExtractor;
import coco.recognize.PatternFactory;
import coco.recognize.fextractor.CoarseMeshFeatureExtractor;
import coco.recognize.svm.libsvm.SvmPattern;
import coco.recognize.svm.libsvm.svm_node;

/**
 * SVMģ�͹���
 * @author coco
 *
 */
public class SvmPatternFactory extends PatternFactory<svm_node[], SvmPattern> {
	
	public SvmPatternFactory() {
		this(new CoarseMeshFeatureExtractor());
	}
	
	/**
	 * constructor
	 * @param fex
	 */
	public SvmPatternFactory(FeatureExtractor<double[]> fex) {
		super(fex);
	}
	
	/**
	 * ����һ��������������������LibSVM������ʽ��
	 * @param sample
	 * @return ����������[label, svm_nodes[]]
	 * @throws IOException
	 */
	@Override
	public SvmPattern produce_sample(File sample) throws IOException {
		BufferedImage img = ImageIO.read(sample);
		SvmPattern svm_pattern = new SvmPattern();
		
		// ��ǩ
		svm_pattern.label = SvmPatternFactory.what_label(sample);
		// ��ʼ��svm_pattern���������nodes
		svm_pattern.svm_nodes = produce_inputmodel(img);
		
		return svm_pattern;
	}
	
	/**
	 * ����ͼ��Ԥ��(ʶ��)�����������ֵ����(ģ��),���磺
	 * <p>LibSVM�е�֧������������ģ��Ϊ��svm_node[]</p>
	 * @param bi
	 * @return
	 * @throws IOException
	 */
	@Override
	public svm_node[] produce_inputmodel(BufferedImage bi) throws IOException {
		double feaVector[] = fex.extract(bi);
		svm_node[] svm_nodes = new svm_node[feaVector.length];
		
		for (int i = 0; i < feaVector.length; i++) {
			svm_nodes[i] = new svm_node();
			svm_nodes[i].index = i + 1;
			svm_nodes[i].value = feaVector[i];
		}
		return svm_nodes;
	}
	
	/**
	 * ����������ǩ
	 * @param sample
	 * @return
	 */
	private static int what_label(File sample) {
		// ȡsample���Ƶĵ�һ���ַ������ļ�����ϣ�
		return Integer.parseInt(sample.getName().substring(0, 1));
	}
	
	/**
	 * ����LibSVM�����ĸ�ʽ��svm_fea_sampת��Ϊ��Ӧ��ʽ��String,
	 * �Ա�д���ļ�
	 * @param svm_fea_samp ����������
	 * @return ����LibSVM������ʽ��String
	 */
	public static String format(SvmPattern svm_fea_samp) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(svm_fea_samp.label + " "); // д��label
		// д������ֵ
		for (int i = 0; i < svm_fea_samp.svm_nodes.length; i++) {
			// value����0��ʱ�����������д
			if (svm_fea_samp.svm_nodes[i].value != 0.0) {
				sb.append(svm_fea_samp.svm_nodes[i].index + ":"
						+ svm_fea_samp.svm_nodes[i].value + " ");
			}
		}
		return sb.toString();
	}
	
}
