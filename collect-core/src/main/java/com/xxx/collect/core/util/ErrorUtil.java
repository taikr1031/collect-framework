package com.xxx.collect.core.util;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ErrorUtil {

	/**
	 * 这里对所有后台校验对象，如果校验失败都抛出运行时异常。 程序里所有都需要前台校验，所以正常情况不会到这里。
	 */
	public static void dealError(Errors errors) {
		if (errors.hasErrors()) {
			String errorMsg = "对象校验失败：";
			List<ObjectError> allErrors = errors.getAllErrors();
			for (ObjectError objectError : allErrors) {
				errorMsg += "[" + objectError.getObjectName() + "]:"
						+ objectError.getDefaultMessage() + " ; ";
			}
			throw new RuntimeException(errorMsg);
		}
	}

}
