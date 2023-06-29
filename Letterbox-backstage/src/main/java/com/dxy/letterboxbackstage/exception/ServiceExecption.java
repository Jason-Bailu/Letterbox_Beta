package com.dxy.letterboxbackstage.exception;

import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import lombok.Getter;

/**
 * @Author: JasonD
 * @date: 2023/5/15 23:46
 * @Description:
 */
@Getter
public class ServiceExecption extends RuntimeException{
    private Enum<CodeEnum> code;

    public ServiceExecption(Enum<CodeEnum> code, String msg) {
        super(msg);
        this.code = code;
    }
}
