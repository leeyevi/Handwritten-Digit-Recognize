package coco.recognize.svm.libsvm;

import coco.recognize.Pattern;

/**
 * ���������͵�������ʾ������LibSVM������ʽ��
 * <p>
 * svm_feature_sample = label index:value index:value ...
 * </p>
 * <p>
 * svm_node = index:value
 * </p>
 * @author coco
 *
 */
public class SvmPattern implements Pattern {
	/**
	 * ��������ǩ
	 */
	public int label;
	/**
	 * i:��i��������ֵ --> index_i:value_i
	 */
	public svm_node[] svm_nodes;
}
