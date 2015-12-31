package coco.recognize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * ��Ʒģ�ͼ���
 * @author coco
 *
 */
public class PatternList<E extends Pattern> {
	/**
	 * ��Ʒģ�ͼ��� ArrayList<E> _list
	 */
	private ArrayList<E> _list = new ArrayList<E>();
	
	public PatternList() {}
	
	/**
	 * ͨ���ļ�����ȡģ��������ģ�ͼ���
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public PatternList(File file) 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		reader(file);
	}
	
	/**
	 * ���ģ��
	 * @param pattern
	 */
	public void add(E pattern) {
		_list.add(pattern);
	}
	
	/**
	 * ��ȡģ�ͼ��ϵĴ�С
	 * @return ģ�ͼ��ϵ�Ԫ�ظ���
	 */
	public int size() {
		return _list.size();
	}
	
	/**
	 * ��ȡģ��
	 * @param index ��Ҫ�Ǳ�
	 * @return ����ָ���ģ��
	 */
	public E get(int index) {
		return _list.get(index);
	}
	
	/**
	 * ͨ���ļ�����ȡģ��
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void reader(File file) 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		_list = (ArrayList<E>) ois.readObject();
		ois.close();
	}
	
	/**
	 * ͨ���ļ���д��ģ��
	 * @param file
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	public void writer(File file) 
			throws IOException, FileNotFoundException, ClassNotFoundException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(_list);
		oos.close();
	}
}
