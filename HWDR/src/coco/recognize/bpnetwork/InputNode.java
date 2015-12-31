package coco.recognize.bpnetwork;

/**
 * BP�������������Ľڵ���
 *
 */
public class InputNode extends AbstractNode {

	/**
	 * Eclipse �Զ����ɵ������к�
	 */
	private static final long serialVersionUID = -9020890049730904050L;

	/**
	 * ��������ڵ��ֵ
	 */
	public void setValue(double val) {
//		if (val <= 0.0 || val >= 1.0) {
		if (val < 0.0 || val > 4.0) {
			throw new IllegalArgumentException("�����Ƿ�");
		}
		value = val;
	}
}
