package com.nb.mallchat.common.common.domain.entity;

/**
 * @author yunfu.ye
 * @date 2024/3/6 16:27
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nb.mallchat.common.common.domain.entity.msg.MsgRecall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 消息扩展属性
 */
@Data
@Builder
@AllArgsConstructor
// @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageExtra implements Serializable {
	private static final long serialVersionUID = 1L;
	// //url跳转链接
	// private Map<String, UrlInfo> urlContentMap;
	//消息撤回详情
	private MsgRecall recall;
	// //艾特的uid
	// private List<Long> atUidList;
	// //文件消息
	// private FileMsgDTO fileMsg;
	// //图片消息
	// private ImgMsgDTO imgMsgDTO;
	// //语音消息
	// private SoundMsgDTO soundMsgDTO;
	// //文件消息
	// private VideoMsgDTO videoMsgDTO;
	//
	// /**
	//  * 表情图片信息
	//  */
	// private EmojisMsgDTO emojisMsgDTO;
}
