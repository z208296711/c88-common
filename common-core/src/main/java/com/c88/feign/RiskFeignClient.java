package com.c88.feign;


import com.c88.common.core.result.Result;
import com.c88.common.core.vo.BetRecordVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "c88-risk", path = "/risk")
public interface RiskFeignClient {

    /**
     * 取得會員注單統計
     *
     * @param username      會員帳號
     * @param fromBeginning 是否從頭開始，true 回傳所有注單統計，false 回傳從上一次提款完成後的注單統計
     * @return
     */
    @GetMapping("/betRecord/{username}/{fromBeginning}")
    Result<BetRecordVo> betRecord(@PathVariable String username, @PathVariable boolean fromBeginning);


    /**
     * 取得會員當月注單統計
     * @param username 會員帳號
     * @return
     */
    @GetMapping("/betRecord/monthly/{username}")
    Result<BetRecordVo> monthlyBetRecord(@PathVariable String username);

    /**
     * 取得昨日投注記錄
     * @param
     * @return
     */
    @GetMapping("/betRecord/daily")
    Result<Map<String, BetRecordVo>> yesterdayBetRecord();

}
