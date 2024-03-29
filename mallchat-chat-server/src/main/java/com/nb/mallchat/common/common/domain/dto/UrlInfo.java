package com.nb.mallchat.common.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlInfo {
	/**
	 * 标题
	 **/
	String title;

	/**
	 * 描述
	 **/
	String description;

	/**
	 * 网站LOGO
	 **/
	String image;

}
