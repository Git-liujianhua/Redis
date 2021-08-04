package com.atguigu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

public class ChangeCharImage {
	
	private static String ch = "@&%#M8XOHLTI!)=+;:,^.' ";
	
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public static void doIt(String dirPath,String imageType,String outPath) {
		 
		File f = new File(dirPath);
		if(!f.isDirectory()) {
			System.out.println("???????????");
		}
		File[] listFiles = f.listFiles();
		// ??????????
		int num = listFiles.length;
		// 
		if(num > 1000) {
			List<CreateImageRunner> list = new ArrayList<>();
			int threadNum = num / 500;
			int fileNum = num / threadNum;
			for(int i = 0;i < threadNum;i++) {
				CreateImageRunner createImageRunner = new CreateImageRunner(imageType, outPath, i * fileNum, (i+1) * fileNum, listFiles);
				list.add(createImageRunner);
			}
			if(fileNum * threadNum > listFiles.length) {
				CreateImageRunner createImageRunner = new CreateImageRunner(imageType, outPath, fileNum * threadNum, listFiles.length, listFiles);
				list.add(createImageRunner);
			}
			list.forEach(r -> executorService.execute(r));
			executorService.shutdown();
		}else {
			Arrays.stream(listFiles).filter(file -> file.getName().endsWith("."+imageType)).forEach(file -> {
				change(file,imageType,outPath);
			});
		}
	}
	
	public static void change(File f,String imageType,String out) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		int width = bi.getWidth();
		int height = bi.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// ???????
		Graphics2D graphics = (Graphics2D)image.getGraphics();
		graphics.fillRect(0, 0, width, height);
		for(int i = 0;i < width;i+=8) {
			for(int j = 0;j < height;j+=8) {
				int rgb = bi.getRGB(i, j);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = rgb & 0xFF;
				// ???????
//				double gray = Math.pow((Math.pow(r, 2.2) + Math.pow(1.5 * g, 2.2) + Math.pow(0.6 * b, 2.2)) / (1 + Math.pow(1.5, 2.2) + Math.pow(0.6, 2.2)), (1/2.2));
				int gray = (int) (0.299f * r + 0.578f * g + 0.114f * b);
				int index = (int) Math.floor(gray*ch.length()/256);
				Color c = new Color(r,g,b);
				graphics.setColor(c);
				graphics.setFont(new Font("??????", 0, 8));
				graphics.drawString(ch.charAt(index)+"", i, j);
				System.out.print(ch.charAt(index));
			}
		}
//		try {
//			File dir = new File(out);
//			dir.mkdirs();
//			File file = new File(out+"/"+f.getName());
//			ImageIO.write(image, imageType, file);
//			System.out.println(String.format("%s_?????", file.getName()));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
}
