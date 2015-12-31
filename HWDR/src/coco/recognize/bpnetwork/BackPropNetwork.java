package coco.recognize.bpnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coco.recognize.PatternList;

/**
 * from : http://sourceforge.net/projects/backprop1/
 */
public class BackPropNetwork {
	private NetWork _netWork;
	
	/**
	 * BP�������࣬���ڸ���������Ĺ��캯��
	 * @param inputNum �����ڵ����Ŀ
	 * @param hiddenNum ���ز�ڵ����Ŀ
	 * @param outputNum �����ڵ����Ŀ
	 * @param learningRate ѧϰ����
	 * @param momentum Ȩֵ�仯����
	 */
	public BackPropNetwork(int inputNum, int hiddenNum, int outputNum, double learningRate, double momentum) {
		_netWork = new NetWork(inputNum, hiddenNum, outputNum, learningRate, momentum);
	}
	
	/**
	 * BP�������࣬�����ļ����Ĺ��캯������ȡ�Ѿ�������file�е�����
	 * @param file ����NetWork���ļ�
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public BackPropNetwork(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	    _netWork = (NetWork) ois.readObject();
	    ois.close();
	}
	
	/**
	 * ���������磬��������ֵ�����������
	 * @param input �����ڵ�ֵ�ļ���
	 * @return �����ڵ�ֵ�ļ���
	 */
	public double[] runNetWork(double[] input) {
		return _netWork.runNetWork(input);
	}
	
	public int trainNetWork(PatternList<BpPattern> pls, double errorRate, int maxCycle, double threshold) throws IOException {
		int success = 0, maxSuccess = 0;
		int limit = pls.size();
		System.out.println("limit = " + limit);
		int count = 0, counter = 0, cc = 0;
		boolean whileFlag = true;
		
		double err = 0.0;
		if (errorRate <= 0.0) {
			errorRate = 0.001;
		}
		
		do {
			success = 0;
			
			for (int i = 0; i < limit; i++) {
				BpPattern pattern = pls.get(i);
				
				double[] result = _netWork.runNetWork(pattern.getInputs()); // �����������ֵ�ļ���
				_netWork.trainNetWork(pattern.getOutputs()); // ѵ��
				int[] truth = BpMath.thresholdArray(threshold, result); // ��ֵ�������ֵ����
				int[] target = BpMath.thresholdArray(threshold, pattern.getOutputs()); // ��ֵ��ģ��Ŀ�����ֵ
								
				pattern.setTrained(true);
				for (int j = 0; j < truth.length; j++) {
					if (truth[j] != target[j]) {
						pattern.setTrained(false);
					}
				}
				
				if (pattern.isTrained()) {
					success++;
				}
			}
			
			if (success > maxSuccess) {
				maxSuccess = success;
			}
			
			if ((++counter % 100) == 0) {
				System.out.println((cc++) + " maxSuccess: " + maxSuccess + "   errorRate: " + (double) (limit - success) / limit);
				counter = 0;
			}
//			whileFlag = ((limit - success) / limit <= errorRate || ++count > maxCycle) ? false : true;
			err = (double)(limit - success) / limit;
			if (err <= errorRate) {
				whileFlag = false;
				System.out.println(" is errorRate, err = " + err);
			}
			if (++count > maxCycle) {
				whileFlag = false;
				System.out.println(" is cycle, count = " + count);
			}
		} while(whileFlag);
		
		return maxSuccess;
	}
	
	/**
	 * ��������
	 * @param file ��Ҫ����������ļ�
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void saveNetWork(File file) throws IOException, FileNotFoundException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(_netWork);
		oos.close();
	}
}
