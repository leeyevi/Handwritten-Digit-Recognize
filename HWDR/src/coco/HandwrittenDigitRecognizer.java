package coco;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import coco.binaryz.Binaryz;
import coco.binaryz.OstuBinaryz;
import coco.recognize.FeatureExtractor;
import coco.recognize.Recognizer;
import coco.recognize.svm.SvmRecognizer;
import coco.segment.Slicer;
import coco.segment.VerticalProjectionSlicer;
import coco.util.ImageSquaring;
import coco.zoom.BilinearZoomer;
import coco.zoom.Zoomer;

/**
 * ��д����ʶ����
 * @author coco
 *
 */
public class HandwrittenDigitRecognizer {
	private static final int widthThreshold = 7;
	
	private Binaryz _binaryz = null;
	
	private Slicer _slicer = null;
		
	private Zoomer _zoomer = null;
	
	private Recognizer _recognizer = null;
	
	public HandwrittenDigitRecognizer(String model_file_name) {
		_binaryz = new OstuBinaryz();
		_slicer = new VerticalProjectionSlicer();
		_zoomer = new BilinearZoomer();
		try {
			_recognizer = new SvmRecognizer(model_file_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ͼƬ�е���д���ֱ���ַ���
	 * @param picture_name ��ʶ��ͼƬ���ļ����ƣ�����·����
	 * @return
	 * @throws IOException
	 */
	public String toString(String picture_name) throws IOException {
		return toString(ImageIO.read(new File(picture_name)));
	}
	
	/**
	 * ��ͼƬ�е���д���ֱ���ַ���
	 * @param bi
	 * @return ʶ����(�ַ�����ʽ)
	 * @throws IOException 
	 */
	public String toString(BufferedImage bi) throws IOException {
		
		// (1) ��ֵ��
		BufferedImage img = null;
		img = _binaryz.binaryzation(bi);
		
		// (2) �ָ�
		ArrayList<BufferedImage> sub_imgs = new ArrayList<BufferedImage>();
		sub_imgs = _slicer.segment(img);
		
		// (3) ��һ��
		int sub_imgs_num = sub_imgs.size();
		for (int i = 0; i < sub_imgs_num; i++) {
			BufferedImage square_img = ImageSquaring.squaring(sub_imgs.get(i));
			if (square_img.getWidth() < widthThreshold) {
				sub_imgs.remove(i); sub_imgs_num--; i--;
				continue;
			}
			sub_imgs.set(i, _zoomer.zoom(square_img, 20, 20, BufferedImage.TYPE_BYTE_BINARY));
		}
		
		// (4) ʶ��
		String result = null;
		StringBuilder sb = new StringBuilder();
		for (BufferedImage subimg : sub_imgs) {
			result = _recognizer.predict(subimg);
			sb.append(result);
		}
		
		return sb.toString();
	}
	
	public void setBinaryz(Binaryz binaryz) {
		_binaryz = binaryz;
	}
	
	public void setSlicer(Slicer slicer) {
		_slicer = slicer;
	}
	
	public void setZoomer(Zoomer zoomer) {
		_zoomer = zoomer;
	}
	
	public void setRecognizer(Recognizer recognizer) {
		_recognizer = recognizer;
	}
	
	public void setFeatureExtractor(FeatureExtractor<double[]> fex) {
		_recognizer.setFeatureExtractor(fex);
	}
}