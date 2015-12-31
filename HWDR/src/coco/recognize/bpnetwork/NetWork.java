package coco.recognize.bpnetwork;

import java.io.Serializable;

/**
 * BP�����������ģ��
 *
 */
public class NetWork implements Serializable {

	/**
	 * Eclipse �Զ����ɵ������к�
	 */
	private static final long serialVersionUID = -2473072398826618195L;
	
	/**
	 * �����л��ļ���
	 */
	private final Arc[] _arcs;
	
	/**
	 * �����������ڵ�ļ���
	 */
	private final InputNode[] _inputs;
	
	/**
	 * ���������ز�ڵ�ļ���
	 */
	private final HiddenNode[] _hiddens;
	
	/**
	 * �����������ڵ�ļ���
	 */
	private final OutputNode[] _outputs;
	
	/**
	 * ����3��BP������ģ�ͣ����������Ӹ� �� �� �ڵ�
	 * @param inputNum �����ڵ����Ŀ
	 * @param hiddenNum ���ز�ڵ����Ŀ
	 * @param outputNum �����ڵ����Ŀ
	 * @param learningRate ѧϰ����
	 * @param momentum Ȩֵ�仯����
	 */
	public NetWork(int inputNum, int hiddenNum, int outputNum, double learningRate, double momentum) {
		_inputs = new InputNode[inputNum];
		for (int i = 0; i < _inputs.length; i++) {
			_inputs[i] = new InputNode();
		}
		
		_hiddens = new HiddenNode[hiddenNum];
		for (int i = 0; i < _hiddens.length; i++) {
			_hiddens[i] = new HiddenNode(learningRate, momentum);
		}
		
		_outputs = new OutputNode[outputNum];
		for (int i = 0; i < _outputs.length; i++) {
			_outputs[i] = new OutputNode(learningRate, momentum);
		}
		
		_arcs = new Arc[(inputNum * hiddenNum) + (hiddenNum * outputNum)];
		for (int i = 0; i < _arcs.length; i++) {
			_arcs[i] = new Arc();
			_arcs[i].setArcWeight(BpMath.getRandomDouble()); // ��ʼ��Ȩ��(���ֵ)
		}
		
		int arc_index = 0;
		for (int i = 0; i < _inputs.length; i++) { // ���������ڵ�����ز�ڵ�
			for (int j = 0; j < _hiddens.length; j++) {
				_inputs[i].connect(_hiddens[j], _arcs[arc_index++]);
			}
		}
		
		for (int i = 0; i < _hiddens.length; i++) { // �������ز�ڵ�������ڵ�
			for (int j = 0; j < _outputs.length; j++) {
				_hiddens[i].connect(_outputs[j], _arcs[arc_index++]);
			}
		}
	}
	
	public InputNode[] getInputNodes() {
		return _inputs;
	}
	
	public HiddenNode[] getHiddenNode() {
		return _hiddens;
	}
	
	public OutputNode[] getOutputNode() {
		return _outputs;
	}
	
	public Arc[] getArcs() {
		return _arcs;
	}
	
	/**
	 * ����ֵ�����������еõ�������
	 * @param input �����ڵ��ֵ
	 * @return �����ڵ��ֵ
	 */
	public double[] runNetWork(double[] input) {
		for (int i = 0; i < _inputs.length; i++) {
			_inputs[i].setValue(input[i]);
//			System.out.println("_inputs[" + i + "] = " + _inputs[i].getValue());
		}
		
		for (int i = 0; i < _hiddens.length; i++) {
			_hiddens[i].runNode();
//			System.out.println("_hiddens[" + i + "] = " + _hiddens[i].getValue());
		}
		
		for (int i = 0; i < _outputs.length; i++) {
			_outputs[i].runNode();
//			System.out.println("_outputs[" + i + "] = " + _outputs[i].getValue());
		}
		
		double[] result = new double[_outputs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = _outputs[i].getValue();
		}
		
		return result;
	}
	
	/**
	 * ����Ŀ��ֵѵ������;
	 * �ⲿ����trainNetWork()������result����������������������������ٴε��ú�������ѵ��
	 * @param target �����ڵ��Ŀ��ֵ
	 * @return �����ڵ��ֵ
	 */
	public void trainNetWork(double[] target) { // return value : double[] --> void
		for (int i = 0; i < _outputs.length; i++) {
			_outputs[i].setError(target[i]);
		}
		
		for (int i = 0; i < _outputs.length; i++) {
			_outputs[i].trainNode();
		}
		
		for (int i = 0; i < _hiddens.length; i++) {
			_hiddens[i].trainNode();
		}
	}
}
