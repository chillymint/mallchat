package com.nb.mallchat.common.common.utils;

import cn.hutool.core.util.StrUtil;
import com.nb.mallchat.common.common.domain.dto.UrlInfo;
import org.dom4j.Document;

import javax.annotation.Nullable;

public class CommonUrlDiscover extends AbstractUrlDiscover {
	// @Nullable
	// @Override
	// public String getTitle(Document document) {
	// 	return document.title();
	// }

	@Nullable
	@Override
	public UrlInfo getContent(String url) {
		return null;
	}

	@Nullable
	@Override
	public String getTitle(Document document) {
		return null;
	}

	@Nullable
	@Override
	public String getDescription(Document document) {
		// String description = document.head().select("meta[name=description]").attr("content");
		// String keywords = document.head().select("meta[name=keywords]").attr("content");
		// String content = StrUtil.isNotBlank(description) ? description : keywords;
		//只保留一句话的描述
		// eturn StrUtil.isNotBlank(content) ? content.substring(0, content.indexOf("。")) : content;r
		return null;
	}

	@Nullable
	@Override
	public String getImage(String url, Document document) {
		// String image = document.select("link[type=image/x-icon]").attr("href");
		//如果没有去匹配含有icon属性的logo
		// String href = StrUtil.isEmpty(image) ? document.select("link[rel$=icon]").attr("href") : image;
		//如果url已经包含了logo
		if (StrUtil.containsAny(url, "favicon")) {
			return url;
		}
		//如果icon可以直接访问或者包含了http
		// if (isConnect(!StrUtil.startWith(href, "http") ? "http:" + href : href)) {
		// 	return href;
		// }

		// return StrUtil.format("{}/{}", url, StrUtil.removePrefix(href, "/"));
		return null;
	}


}
