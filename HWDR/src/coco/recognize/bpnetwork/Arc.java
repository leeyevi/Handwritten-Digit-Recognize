package coco.recognize.bpnetwork;

/**
 * �������еĻ�Arc(����ڵ�/����ڵ�/Ȩֵ)
 *
 */
public class Arc extends AbstractArcNode {
	/**
	 * Eclipse �Զ����ɵ�������ID
	 */
	private static final long serialVersionUID = 1455760609876116647L;

	/**
	 * �û���Ȩ��weight
	 */
	private double _weight;
	
	/**
	 * �û�����ڵ�InputNode
	 */
	private AbstractNode _inNode;
	
	/**
	 * �û�������ڵ�OutputNode
	 */
	private AbstractNode _outNode;
	
	/**
	 * �û���ǰ��weight�仯��
	 */
	private double _delta;
	
	public void setInputNode(AbstractNode inNode) {
		_inNode = inNode;
	}
	
	public void setOutputNode(AbstractNode outNode) {
		_outNode = outNode;
	}
	
	public void setArcWeight(double weight) {
		_weight= weight;
	}
	
	public double getInputNodeValue() {
		return _inNode.getValue();
	}
	
	/**
	 * @return _inNode.getValue() * _weidht
	 */
	public double getWeightInputNodeValue() {
		return (_inNode.getValue() * _weight);
	}

	/**
	 * @return _outNode.getError() * _weight
	 */
	public double getWeightOutputNodeError() {
		return (_outNode.getError() * _weight);
	}
	
	/**
	 * ����Ȩ�ر仯��delta������weight;
	 * ͬʱҲ���˴εı仯�������������������´ζ�weigth�ĸ���
	 * @param delta Ȩ�ر仯��
	 */
	public void updateWeight(double delta) {
		OutputNode on = (OutputNode) _outNode;
		_weight += delta + on.getMomentum() * _delta;
		_delta = delta;
	}
}
