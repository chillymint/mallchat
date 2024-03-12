package com.nb.mallchat.common.common.domain.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sensitive_word")
public class SensitiveWord {
	private String word;
}

