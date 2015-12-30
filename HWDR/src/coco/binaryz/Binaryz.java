package coco.binaryz;

import java.awt.image.BufferedImage;

/**
 * 二值化器
 * @author coco
 *
 */
public interface Binaryz {
	
	/**
	 * 图像二值化
	 * @param bi 待二值化的图像
	 * @return 二值化后的图像
	 */
	public abstract BufferedImage binaryzation(BufferedImage bi);
}
