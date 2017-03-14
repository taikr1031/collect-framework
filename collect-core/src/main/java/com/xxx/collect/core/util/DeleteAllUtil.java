package com.xxx.collect.core.util;

import java.io.File;

public class DeleteAllUtil {
	public static void deleteAll(File file) {
		if (file.isFile() || file.listFiles().length == 0) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteAll(f);
				f.delete();
			}
		}
	}

	public static void main(String[] args) {
		deleteAll(new File("D:\\.m2"));
	}

}