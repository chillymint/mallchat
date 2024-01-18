package com.nb.mallchat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 */
@AllArgsConstructor
@Data
public class ModifyNameReq {
	@ApiModelProperty("用户名")
	@NotBlank
	@Length(max = 6, message = "用户名不可超6位")
	private String name;

	@NotNull(message = "id必传")
	private Integer id;

}
