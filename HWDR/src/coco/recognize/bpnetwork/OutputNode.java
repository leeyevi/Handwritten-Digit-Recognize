package coco.recognize.bpnetwork;

import java.util.Iterator;

/**
 * BP�������������Ľڵ���
 *
 */
public class OutputNode extends AbstractNode {

	/**
	 * Eclipse �Զ����ɵ������к�
	 */
	private static final long serialVersionUID = -4173103446471422283L;

	/**
	 * ѧϰ����learningRate�� ������ѧϰЧ���ĺû���
	 * ̫������ܻ�ﲻ������ֵ��̫С�����ݶ��½�ʱ������
	 */
	double learningRate;
	
	/**
	 * momentum��������Ϊ�˿˷������ֲ���Сֵ����������õı���
	 */
	double momentum;
	
	/**
	 * ���캯��
	 * @param learningRate ѧϰ����
	 * @param momentum ��������Ϊ�˿˷������ֲ���Сֵ����������õı���
	 */
	public OutputNode(double learningRate, double momentum) {
		this.learningRate = learningRate;
		this.momentum = momentum;
	}
	
	/**
	 * ͨ���Ըýڵ����������ڵ�value���Ӧ��weight�˻�����������¸ýڵ�value
	 */
	public void runNode() {
		double total = 0.0;
		
		Iterator<Arc> it = inputArcs.iterator();
		while (it.hasNext()) {
			Arc arc = it.next();
			total += arc.getWeightInputNodeValue();
		}
		value = sigmoidTransfer(total);
	}
	
	/**
	 * ����error�����¸ýڵ��������뻡��weight
	 */
	public void trainNode() {
		error = computeError(); // ������Ҫ���¼���error���Ǹ������Ŀ��ֵtarget�������
		
		Iterator<Arc> it = inputArcs.iterator();
		while (it.hasNext()) {
			Arc arc = it.next();
			double delta = learningRate * error * arc.getInputNodeValue();
			arc.updateWeight(delta);
		}
	}
	
	/**
	 * �����Sigmoid��������������Ԫ�佨�������Թ�ϵ������ֵ��Χ (0.0, 1.0)
	 * @param value ��ǰ�ڵ��ֵ(���ֵ)
	 * @return ����ֵ��Χ 0.0 < result < 1.0
	 */
	private double sigmoidTransfer(double value) {
		return (1.0 / (1.0 + Math.exp(-value)));
	}
	
	/**
	 * ͨ���Լ����Sigmoid�󵼣���������ڵ��error�����ڸ���weight
	 * @return error
	 */
	private double computeError() {
		// ���ҿ����������У�Ӧ���� return value * (1.0 - value) * (target - value); // targerΪĿ�����ֵ
		// ��ʵ��������ڵ㣬���Խ���error ��ʼ��Ϊ target
		return value * (1.0 - value) * (error - value);
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	
	public double getMomentum() {
		return momentum;
	}
}
