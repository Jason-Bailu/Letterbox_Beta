package com.dxy.letterboxbackstage.common;

import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/5/9 20:00
 * @Description: 统一结果返回类
 */
@ApiModel("返回数据结果")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Enum<CodeEnum> code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(CodeEnum.C200, "success", null);
    }

    public static Result success(Object data) {
        return new Result(CodeEnum.C200, "success", data);
    }

    public static Result success(String msg, Object data) {
        return new Result(CodeEnum.C200, msg, data);
    }

    public static Result error() {
        return new Result(CodeEnum.C500, "sys error", null);
    }

    public static Result error(Enum<CodeEnum> code, String msg) {
        return new Result(code, msg, null);
    }
}
