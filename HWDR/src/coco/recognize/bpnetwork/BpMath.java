package coco.recognize.bpnetwork;

import java.util.Random;

/**
 * BP�����������õ���һЩ��ѧ����֧���࣬���з���Ϊ��̬static
 *
 */
public class BpMath {
	private final static Random _random = new Random();
	/**
	 * ���ڶ��������з�ֵ����ʹÿ������ڵ��ֵҪô��0��Ҫô��1
	 */
	public final static double out_threshold = 0.86;
	/**
	 * ����һ��ָ����Χ�ڵģ�α������� range = upper - lower
	 * @param lower �½�
	 * @param upper �Ͻ�
	 * @return ָ����Χ�������
	 */
	public static synchronized double getBoundedRandom(double lower, double upper) {
		if (lower > upper) {
			throw new IllegalArgumentException("�����Ƿ�");
		}
		return (_random.nextDouble() * (upper - lower) + lower);
	}
	
	/**
	 * @return һ��double���͵������
	 */
	public static synchronized double getRandomDouble() {
		return _random.nextDouble();
	}
	
	/**
	 * ����<p>ģ��Ŀ�����ֵ����</p> 
	 * ����<p>�������������������ֵ�ļ���</p>
	 * ��ֵ����ʹ�����ܽ��бȽϣ����������������Ҫ��
	 * @param threshold ��ֵ
	 * @param result �����ֵ���� ���� ģ��Ŀ�����ֵ����
	 * @return ������ֵ�����ֵ����
	 */
	public static synchronized int[] thresholdArray(double threshold, double[] result) {
		int[] thresholdArr = new int[result.length];
		
		for (int i = 0; i < thresholdArr.length; i++) {
			thresholdArr[i] = result[i] > threshold ? 1 : 0;
		}
		return thresholdArr;
	}
}
