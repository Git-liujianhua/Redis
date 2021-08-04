package com.atguigu;

import java.io.File;

public class CreateImageRunner implements Runnable {
	private String imageType;
	private String outPath;
	private int start;
	private int end;	
	private File[] listFiles;

	public CreateImageRunner(String imageType, String outPath, int start, int end, File[] listFiles) {
		super();
		this.imageType = imageType;
		this.outPath = outPath;
		this.start = start;
		this.end = end;
		this.listFiles = listFiles;
	}

	@Override
	public void run() {
		if(listFiles.length < end) {
			end = listFiles.length;
		}
		for(int i = start;i < end;i++) {
			ChangeCharImage.change(listFiles[i], imageType, outPath);
		}
	}

}
