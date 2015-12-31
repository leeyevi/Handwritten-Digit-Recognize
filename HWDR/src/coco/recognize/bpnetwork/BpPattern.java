package coco.recognize.bpnetwork;

import java.io.Serializable;

import coco.recognize.Pattern;

/**
 * BP�������ѵ��ģ��
 * @author coco
 *
 */
public class BpPattern implements Pattern, Serializable {
	/**
	 * Eclipse �Զ����ɵ������к�
	 */
	private static final long serialVersionUID = 3597380217345812284L;

	/**
	 * ģ������ֵ
	 */
	private double[] _input;
	
	/**
	 * ģ�����ֵ(Ŀ�����ֵ)
	 */
	private double[] _output;
	
	/**
	 * ��ģ���Ƿ񾭹�ѵ��
	 */
	private boolean _trained = false;
	
	/**
	 * ������
	 * @param inputs ģ������ֵ
	 * @param outputs ģ�͵����ֵ(Ŀ��ֵ)
	 */
	public BpPattern(double[] inputs, double[] outputs) {
		_input = inputs;
		_output = outputs;
	}
	
	public void setInputs(double[] inputs) {
		_input = inputs;
	}
	
	public double[] getInputs() {
		return _input;
	}
	
	public void setOutputs(double[] outputs) {
		_output = outputs;
	}
	
	public double[] getOutputs() {
		return _output;
	}
	
	public boolean isTrained() {
		return _trained;
	}
	
	public void setTrained(boolean arg) {
		_trained = arg;
	}
	
}
