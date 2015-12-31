package coco.recognize.bpnetwork;

import java.util.Iterator;

/**
 * BP�����������ز�Ľڵ���
 *
 */
public class HiddenNode extends OutputNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3770512962052485662L;

	/**
	 * ���캯��
	 * @param learningRate ѧϰ����
	 * @param momentum ��������Ϊ�˿˷������ֲ���Сֵ����������õı���
	 */
	public HiddenNode(double learningRate, double momentum) {
		super(learningRate, momentum);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ͨ���Լ����Sigmoid�󵼣���������ڵ��error�����ڸ���weight
	 * @return error
	 */
	private double computeError() {
		double total = 0.0;
		
		Iterator<Arc> it = outputArcs.iterator();
		while (it.hasNext()) {
			Arc arc = it.next();
			total += arc.getWeightOutputNodeError();
		}
		return (value * (1.0 - value) * total);
	}
	
	// ��ʵ�͸������һ���ģ��������ø�д�ɣ�ֻ�������ø����trainNode()ʱ�����������computeError()ʱ�Ǹ���Ļ��������
	@Override 
	public void trainNode() {
		error = computeError();
		
		Iterator<Arc> it = inputArcs.iterator();
		while (it.hasNext()) {
			Arc arc = it.next();
			double delta = learningRate * error * arc.getInputNodeValue();
			arc.updateWeight(delta);
		}
	}
}
