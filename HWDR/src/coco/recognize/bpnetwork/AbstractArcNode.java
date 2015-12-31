package coco.recognize.bpnetwork;

import java.io.Serializable;

/**
 * Arc �� Node �ĳ����࣬������Arc��Node�Ĺ��Է���������
 * @author coco
 *
 */
public abstract class AbstractArcNode implements Serializable {

	/**
	 * Eclipse �Զ����ɵ�������ID
	 */
	private static final long serialVersionUID = 3521432475494353360L;
	
	/**
	 * Arc �� Node ��ID����SequenceIdGenerator����
	 */
	final int id = SequenceIdGenerator.getId();
	
	/**
	 * ÿһ�����ͽڵ㶼��Ψһһ����Ӧ��ȫ��ID
	 * @return Arc �� Node ��ID
	 */
	public int getId() {
		return id;
	}
}
