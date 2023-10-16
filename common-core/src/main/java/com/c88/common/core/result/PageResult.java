 package com.c88.common.core.result;

 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.fasterxml.jackson.annotation.JsonInclude;
 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 import java.io.Serializable;
 import java.util.List;
 import java.util.Map;

 /**
  * 分页响应结构体
  *
  * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
  * @date 2022/2/18 23:29
  */
 @Data
 public class PageResult<T> implements Serializable {

     private String code;

     private Data<T> data;

     private String msg;

     public static <T> PageResult<T> success(IPage<T> page, Object summary) {
         PageResult<T> result = new PageResult<>();
         result.setCode(ResultCode.SUCCESS.getCode());

         Data<T> data = new Data<>();
         data.setList(page.getRecords());
         data.setTotal(page.getTotal());
         data.setPageNum(page.getCurrent());
         data.setPageSize(page.getSize());
         data.setSummary(summary);

         result.setData(data);
         result.setMsg(ResultCode.SUCCESS.getMsg());
         return result;
     }

     public static <T> PageResult<T> success(IPage<T> page){
         return success(page, null);
     }

     @lombok.Data
     @Builder
     @NoArgsConstructor
     @AllArgsConstructor
     @JsonInclude(JsonInclude.Include.NON_NULL)
     public static class Data<T> {

         private List<T> list;

         private long total;

         private long pageNum;

         private long pageSize;

         private Object summary;

     }

 }
