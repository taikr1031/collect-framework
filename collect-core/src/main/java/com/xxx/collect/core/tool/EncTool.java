package com.xxx.collect.core.tool;

import com.xxx.collect.core.util.RegexUtil;
import com.xxx.collect.core.util.enc.EncDesUtil;
import com.xxx.collect.core.util.file.FileNameUtil;
import com.xxx.collect.core.util.enc.EncSimplyUtil;

public class EncTool {
	public static String FILE_ENC_PRIVATE_KEY = "19830925";
	public static String d1EncFileName(String encStr) {
		// 1、提取密文
		String encRequestStr = RegexUtil.searchBetween(encStr, "([\\d\\w]+).\\w{2,4}");
		// 2、解密
		String requestStr = EncDesUtil.decrypt(encRequestStr, FILE_ENC_PRIVATE_KEY);
		String fileName = requestStr + "." + FileNameUtil.getExtToLowerCase(encStr);
		return fileName;
	}

	public static String dEncFileName(String encStr) { // 1、提取密文
		String encRequestStr = RegexUtil.searchBetween(encStr, "([\\d\\w]+).\\w{2,4}");
		// 2、解密
		String requestStr = EncSimplyUtil.decrypt(encRequestStr);
		String fileName = requestStr + "." + FileNameUtil.getExtToLowerCase(encStr);
		return fileName;
	}
}
