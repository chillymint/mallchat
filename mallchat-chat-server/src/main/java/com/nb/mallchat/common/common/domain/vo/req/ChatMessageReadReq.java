package com.nb.mallchat.common.common.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageReadReq extends CursorPageBaseReq {
    @ApiModelProperty("消息id")
    @NotNull
    private Long msgId;

    @ApiModelProperty("查询类型 1已读 2未读")
    @NotNull
    private Long searchType;
}
