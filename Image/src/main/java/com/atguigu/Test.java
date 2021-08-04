package com.atguigu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {

	public static void main(String[] args) {
		ChangeCharImage.doIt("D:\\sss", "jpg", "D:\\sss");
//		test();
	}
	
	
	public static void test() {
		File f = new File("D:\\sss");
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			String name = file.getName();
			int index = name.indexOf(".");
			String realName = name.substring(0,index);
			File ff = new File("D:\\sss"+realName+".mp4");
			try (	FileInputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream(ff)){
				int i;
				byte[] bs = new byte[1024];
				while((i = fis.read(bs)) != -1) {
					fos.write(bs, 0, i);
					fos.flush();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
