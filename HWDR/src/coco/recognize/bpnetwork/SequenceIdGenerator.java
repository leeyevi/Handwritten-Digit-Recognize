package coco.recognize.bpnetwork;

/**
 * Ϊÿһ�����ͽڵ��ṩΨһ��ID
 *
 */
public class SequenceIdGenerator {
	
	/**
	 * �����¼ID�ı�������ʼֵΪ1
	 */
	private static int _id = 1;
	
	/**
	 * ÿһ�����ͽڵ㶼Ӧ����һ��Ψһ��ID
	 * @return ΨһID
	 */
	public static synchronized int getId() {
		return _id++;
	}
}
