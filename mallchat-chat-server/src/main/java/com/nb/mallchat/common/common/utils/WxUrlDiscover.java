package com.nb.mallchat.common.common.utils;

import com.nb.mallchat.common.common.domain.dto.UrlInfo;
import org.dom4j.Document;

import javax.annotation.Nullable;

/**
 * Description: 针对微信公众号文章的标题获取类
 */
public class WxUrlDiscover extends AbstractUrlDiscover {

	@Nullable
	@Override
	public UrlInfo getContent(String url) {
		return null;
	}

	@Nullable
	@Override
	public String getTitle(Document document) {
		return "";
	}

	@Nullable
	@Override
	public String getDescription(Document document) {
		return "";
		// return document.getElementsByAttributeValue("property", "og:description").attr("content");
	}

	@Nullable
	@Override
	public String getImage(String url, Document document) {
		// String href = document.getElementsByAttributeValue("property", "og:image").attr("content");
		// return isConnect(href) ? href : null;
		return "";
	}
}
