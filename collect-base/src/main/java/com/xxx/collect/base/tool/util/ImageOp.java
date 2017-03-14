package com.xxx.collect.base.tool.util;

import com.gif4j.*;
import com.xxx.collect.core.util.file.FileNameUtil;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.*;

public class ImageOp {

	public static void main(String[] args) throws IOException {
		String baseDir = "C:\\imageTest";
		String tarBaseDir = "C:\\imageTest-ok";
		File dir = new File(baseDir);
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			if (file.isDirectory())
				continue;
			String extName = FileNameUtil.getExtToLowerCase(file.getName());
			String fileName = file.getName();
			System.out.println("fileName:" + fileName);
			FileInputStream is = new FileInputStream(file);
			FileOutputStream os = new FileOutputStream(new File(tarBaseDir, "scale-" + fileName));
			ImageOp imageOp = new ImageOp();
			imageOp.read(is, extName);
			imageOp.scaleByWidthHeight(30, 30);
			imageOp.write(os);

			FileInputStream is2 = new FileInputStream(file);
			FileOutputStream os2 = new FileOutputStream(new File(tarBaseDir, "mark-" + fileName));
			ImageOp imageOp2 = new ImageOp();
			imageOp2.read(is2, extName);
			imageOp2.drawTextOnTop("www.2gei.com");
			imageOp2.drawTextOnBottom("爱给网 游戏素材下载");
			imageOp2.write(os2);
		}

	}

	private String resultFormat = "png";
	private String oldFormat;

	private BufferedImage srcBufImage;
	private BufferedImage newBufImage;
	private InputStream inputStream;
	private Graphics g;
	private int srcWidth;
	private int srcHeight;
	private int width;
	private int height;

	// gif
	private boolean isUseGifEncoder = false;
	private GifImage gifImage;

	private float baseMarkRate = 1 / 25f;
	private int fontHeight;
	private int rectPad;

	public ImageOp() {

	}

	public void write(OutputStream outputStream) {
		try {
			if (this.isUseGifEncoder) {
				GifEncoder.encode(gifImage, outputStream);
			} else {
				g.dispose();
				// 始终存成png，使得图片透明

				// 输出到文件流
				BufferedImage bufImage = newBufImage != null ? newBufImage : srcBufImage;
				// 经过测试，只有同格式对同格式输出才能保证图片最小，效果最好
				ImageIO.write(bufImage, resultFormat, outputStream);
				this.inputStream.close();
				outputStream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void read(InputStream inputStream, String format) throws IOException {
		this.inputStream = inputStream;
		this.oldFormat = format;
		this.resultFormat = smartConvertFormat(format);
		// 如果是多帧gif动画，java图像处理后都变为单帧了，就需要使用GifEncoder处理
		if (oldFormat.toLowerCase().equals("gif")) {
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			inputStream.close();
			ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
			gifImage = GifDecoder.decode(arrayInputStream);
			if (gifImage.getNumberOfFrames() > 1) {
				this.isUseGifEncoder = true;
			} else {
				inputStream = new ByteArrayInputStream(byteArray);
			}
		}
		if (isUseGifEncoder) {
			srcWidth = gifImage.getScreenWidth(); // 得到源图宽
			srcHeight = gifImage.getScreenHeight(); // 得到源图长
			// 目前gif加水印还存在问题，因此对于所有gif的图片实行通用处理，就是把尺寸缩小到正常的80%
			srcWidth = Math.round(srcWidth * 0.8f);
			srcHeight = Math.round(srcHeight * 0.8f);
		} else {
			srcBufImage = ImageIO.read(inputStream);
			srcWidth = srcBufImage.getWidth(); // 得到源图宽
			srcHeight = srcBufImage.getHeight(); // 得到源图长
		}
		width = srcWidth;
		height = srcHeight;
		fontHeight = Math.round(height * baseMarkRate);
		rectPad = Math.round(height * baseMarkRate * 0.4f);
	}

	/**
	 * 智能格式转化
	 */
	public static String smartConvertFormat(String oldFormat) {
		// png -> png
		// jpg,jpeg -> jpg,jpeg
		// gif -> gif
		// bmp -> jpg
		return oldFormat.equals("bmp") ? "jpg" : oldFormat;
	}

	/**
	 * 根据宽度和高度来计算新图形，按比例缩放
	 */
	public void scaleByWidthHeight(int toWidth, int toHeight) {
		setResultSize(toWidth, toHeight);
		if (this.isUseGifEncoder) {
			gifImage = GifTransformer.resize(gifImage, this.width, this.height, true);
		} else {
			picScaleByWidthHeight(toWidth, toHeight);
		}
	}

	public void drawTextOnTop(String pressText) {
		if (this.isUseGifEncoder) {
			// 目前加水印暂时还有问题
			// Watermark watermark = this.getGifWaterText(pressText, 10,
			// fontHeight + 2 * rectPad);
			// this.gifImage = watermark.apply(this.gifImage, true);
		} else {
			picDrawTextOnTop(pressText);
		}
	}

	public void drawTextOnBottom(String pressText) {
		if (this.isUseGifEncoder) {
			// Watermark watermark = this.getGifWaterText(pressText,
			// 10,Math.round(height - fontHeight * 0.4f));
			// this.gifImage = watermark.apply(this.gifImage, true);
		} else {
			picDrawTextOnBottom(pressText);
		}
	}

	@SuppressWarnings("unused")
	private Watermark getGifWaterText(String watermarkText, int x, int y) {
		// 水印初始化、设置（字体、样式、大小、颜色）
		TextPainter textPainter = new TextPainter(new Font("黑体", Font.BOLD, fontHeight));
		textPainter.setOutlinePaint(Color.BLACK);
		BufferedImage renderedWatermarkText = textPainter.renderString(watermarkText, true);

		// int tw = renderedWatermarkText.getWidth();
		// int th = renderedWatermarkText.getHeight();

		// 水印位置
		Point p = new Point();
		p.x = x;
		p.y = y;

		// 加水印
		Watermark watermark = new Watermark(renderedWatermarkText, p);
		return watermark;
	}

	private void picDrawTextOnBottom(String pressText) {
		// 水印
		this.drawRect(0, height - fontHeight - 2 * rectPad, width, fontHeight + 2 * rectPad);
		this.drawText(pressText, "宋体", Font.BOLD, fontHeight, 10,
				Math.round(height - fontHeight * 0.4f));
	}

	private void picDrawTextOnTop(String pressText) {
		this.drawRect(0, 0, width, fontHeight + 2 * rectPad);
		this.drawText(pressText, "宋体", Font.BOLD, fontHeight, 10, Math.round(fontHeight * 1f));
	}

	private void setResultSize(int toWidth, int toHeight) {
		// 如果目标尺寸大于原图，则使用原图尺寸
		if (toWidth > srcHeight && toHeight > srcHeight) {
			this.width = srcWidth;
			this.height = srcHeight;
		} else {
			// 判断长宽比,等比缩放
			if ((float) srcWidth / (float) srcHeight > (float) toWidth / (float) toHeight) {
				this.width = toWidth;
				this.height = (int) ((float) toWidth / (float) srcWidth * (float) srcHeight);
			} else {
				this.height = toHeight;
				this.width = (int) ((float) toHeight / (float) srcHeight * (float) srcWidth);
			}
		}
	}

	private void picScaleByWidthHeight(int toWidth, int toHeight) {
		Image image = srcBufImage.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
		// BufferedImage.TRANSLUCENT 图片透明
		if (this.resultFormat.equals("png")) {
			newBufImage = new BufferedImage(this.width, this.height, BufferedImage.TRANSLUCENT);
		} else if (this.resultFormat.equals("gif")) {
			newBufImage = new BufferedImage(this.width, this.height,
					BufferedImage.TYPE_BYTE_INDEXED, createIndexColorModel());
		} else {
			newBufImage = new BufferedImage(this.width, this.height, BufferedImage.SCALE_SMOOTH);
		}
		g = newBufImage.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩放后的图
	}

	private void drawText(String pressText, String fontName, int fontStyle, int fontSize, int x,
			int y) {
		Graphics g = this.getGraphics();
		Color color = this.resultFormat.equals("gif") ? Color.GRAY : Color.WHITE;
		// color = new Color(color.getRed(), color.getGreen(),
		// color.getBlue());
		// color = new Color(102, 102, 102);
		//  设置绘图使用透明合成规则 
		g.setColor(color);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.drawString(pressText, x, y);
	}

	private Graphics getGraphics() {
		g = g != null ? g : this.srcBufImage.createGraphics();
		return g;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getResultFormat() {
		return resultFormat;
	}

	public void setResultFormat(String resultFormat) {
		this.resultFormat = resultFormat;
	}

	static IndexColorModel createIndexColorModel() {
		BufferedImage ex = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_INDEXED);
		IndexColorModel icm = (IndexColorModel) ex.getColorModel();
		int SIZE = 256;
		byte[] r = new byte[SIZE];
		byte[] g = new byte[SIZE];
		byte[] b = new byte[SIZE];
		byte[] a = new byte[SIZE];
		icm.getReds(r);
		icm.getGreens(g);
		icm.getBlues(b);
		java.util.Arrays.fill(a, (byte) 255);
		r[0] = g[0] = b[0] = a[0] = 0; // transparent
		return new IndexColorModel(8, SIZE, r, g, b, a);
	}

	private void drawRect(int x, int y, int width, int height) {
		Graphics g = this.getGraphics();
		// 先绘制矩形打底
		Color color = this.resultFormat.equals("gif") ? Color.WHITE : Color.BLACK;
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
		Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);
		Graphics2D g2 = (Graphics2D) g;
		// 如果是顶部则从下至下渐变，如果是非顶部则从下至上渐变
		GradientPaint gradientPaint = y == 0 ? new GradientPaint(0, y, color, 0, y + height, color2)
				: new GradientPaint(0, y + height, color, 0, y, color2);
		g2.setPaint(gradientPaint);
		// g2.fillRect(0, 0, width, height);
		// g2.setPaint(storedPaint);
		g2.fillRect(x, y, width, height);
	}
}
