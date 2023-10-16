package com.c88.common.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "指定遊戲平台")
public class AssignPlatformGameVO implements Serializable {

    @Schema(title = "遊戲類型代碼")
    private String gameCategoryCode;

    @Schema(title = "平台名稱代碼")
    private List<String> platformCodes;

}
