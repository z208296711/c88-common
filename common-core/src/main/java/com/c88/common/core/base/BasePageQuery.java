package com.c88.common.core.base;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasePageQuery {

    @Parameter(description = "頁碼", example = "1")
    @Schema(title = "頁碼", example = "1")
    private int pageNum = 1;

    @Parameter(description = "每頁筆數", example = "10")
    @Schema(title = "每頁筆數", example = "10")
    private int pageSize = 10;

    @Parameter(description = "排序目標欄位名稱")
    @Schema(title = "排序目標欄", description = "排序目標欄位名稱")
    private String column;

    @Parameter(description = "排序方式 0正序Asc 1倒序Desc", example = "0")
    @Schema(title = "排序方式", description = "0正序Asc,1倒序Desc", example = "0")
    private Integer order;

}
