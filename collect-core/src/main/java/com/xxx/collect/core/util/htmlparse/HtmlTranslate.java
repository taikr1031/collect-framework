package com.xxx.collect.core.util.htmlparse;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.Text;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitor.ModifyVisitor;

/**
 * @author 鲁炬
 * 
 */
public class HtmlTranslate {

	private Parser parser = null;
	private String html = null;

	public static HtmlTranslate getObj(String html) {
		return new HtmlTranslate(html);
	}

	public HtmlTranslate(String html) {
		try {
			parser = new Parser();
			parser.setInputHTML(html);
			this.html = html;
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 翻译文字
	 */
	public HtmlModify transText() {
		Map<String, String> map = new HashMap<String, String>();
		ModifyVisitor modify = new ModifyVisitor(map) {
			@Override
			public void modifyText(Text text) {
				String textStr = text.getText();
				System.out.println(textStr);
				text.setText(textStr);
			}
		};
		try {
			this.parser.visitAllNodesWith(modify);
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
		this.html = modify.getResult();
		return new HtmlModify(this.html);
	}

	public String toHtml() {
		// 去除多余空格
		html = HtmlCompress.compress1(html);
		return html;
	}

	public static void main(String[] args) {

	}

}
