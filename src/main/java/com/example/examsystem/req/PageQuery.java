package com.example.examsystem.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

//通用分页类
@Data
public class PageQuery {
    @NotNull(message = "【当前页】不能为空")
    private int pageNum;    //当前页码

    @NotNull(message = "【每页显示条数】不能为空")
    private int pageSize;   //每页显示条数
}
