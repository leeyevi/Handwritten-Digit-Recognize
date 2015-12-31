package coco.recognize.bpnetwork;

import java.util.ArrayList;

/**
 * Node �ĳ����࣬�����˲�ͬNode(Input/Hidden/Output)�Ĺ������Ժͷ���
 *
 */
public abstract class AbstractNode extends AbstractArcNode {
	/**
	 * Eclipse �Զ����ɵ�������ID
	 */
	private static final long serialVersionUID = 3926512961588761256L;

	/**
	 * �ýڵ��errorֵ�����ڸ���weight
	 */
	double error;
	
	/**
	 * �ýڵ��ֵvalue�������Output�����Hidden����ȼ��ڸýڵ�����ֵ
	 */
	double value;
	
	/**
	 * �ýڵ�����뻡 Input arcs
	 */
	ArrayList<Arc> inputArcs = new ArrayList<Arc>();
	
	/**
	 * �ýڵ������� Output arcs
	 */
	ArrayList<Arc> outputArcs = new ArrayList<Arc>();
	
	public double getError() {
		return error;
	}
	
	public void setError(double err) {
		error = err;
	}
	
	/**
	 * ����Hidden���Output�㣬valueΪ�ýڵ�����ֵ��
	 * ����Input�㣬valueΪ�ýڵ������ֵ
	 * @return �ýڵ��ֵ
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * ��ǰ�ڵ�ͨ����(arc)������һ���ڵ�(destNode)
	 * @param destNode Ŀ��ڵ�
	 * @param arc ���ӻ�
	 */
	public void connect(AbstractNode destNode, Arc arc) {
		outputArcs.add(arc); // �ڸýڵ������������м���arc
		destNode.inputArcs.add(arc); // ��Ŀ��ڵ�����뻡�����м���arc
		arc.setInputNode(this); // ���û�������ڵ�Ϊ���ö���
		arc.setOutputNode(destNode); // ���û�������Ľڵ�ΪĿ��ڵ�destNode
	}
}
