package com.c88.common.web.log;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * API需要紀錄操作的回傳物件
 */
@AllArgsConstructor
@NoArgsConstructor
public class LogOpResponse<I, O> {

	/**
	 * 修改前的數據，資料庫裡查尋到的資料
	 */
	@Getter
	private I before;
	/**
	 * 修改後的數據
	 */
	@Getter
	private O after;

	@Getter
	@JsonIgnore
	private String beforeStr;

	@Getter
	@JsonIgnore
	private String afterStr;

	public void setBefore(I before) {
		this.beforeStr = JSONUtil.toJsonStr(before);
		this.before = before;
	}

	public void setAfter(O after) {
		this.afterStr = JSONUtil.toJsonStr(after);
		this.after = after;
	}

}