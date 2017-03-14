package com.xxx.collect.core.util.htmlparse;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitor.ModifyVisitor;
import org.htmlparser.visitors.NodeVisitor;

import java.util.HashMap;
import java.util.Map;

public class HtmlModify extends NodeVisitor {

	private Parser parser = null;
	private String html = null;

	public static HtmlModify getModify(String html) {
		return new HtmlModify(html);
	}

	public HtmlModify(String html) {
		try {
			parser = new Parser();
			parser.setInputHTML(html);
			this.html = html;
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

	public String toHtml() {
		// ȥ�����ո�
		html = HtmlCompress.compress1(html);
		return html;
	}

	public HtmlModify removeAttribute(String attribute) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("attribute", attribute);
		ModifyVisitor modify = new ModifyVisitor(map) {
			@Override
			public void modifyTag(Tag tag) {
				tag.removeAttribute((String) this.getParm().get("attribute"));
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
	
	/**
	 * ȥ���򵥵ı�ǩ
	 */
	public HtmlModify removeSimplyTag(){
		html = html.replace("<b>", "");
		html = html.replace("</b>", "");
		html = html.replace("<p>", "");
		html = html.replace("</p>", "");
		html = html.replace("<br>", "");
		html = html.replace("</br>", "");
		return new HtmlModify(this.html);
	}

	/**
	 * ȥ��ע��
	 */
	public HtmlModify removeRemark() {
		this.html = html.replaceAll("<!--.*?-->", "");
		return new HtmlModify(this.html);
	}

	/**
	 * ȥ���ض�����
	 */
	public HtmlModify removeText(String text) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("text", text);
		ModifyVisitor modify = new ModifyVisitor(map) {
			@Override
			public void modifyText(Text text) {
				String dText = (String) this.getParm().get("text");
				if (dText.equals(text.getText().trim())) {
					text.setText("");
				}
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

	/**
	 * �޸����Ӷ���#
	 */
	public HtmlModify modifyLinkNone() {
		ModifyVisitor modify = new ModifyVisitor() {
			@Override
			public void modifyTag(Tag tag) {
				if (tag instanceof LinkTag) {
					LinkTag linkTag = (LinkTag) tag;
					linkTag.removeAttribute("href");
				}
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
}
