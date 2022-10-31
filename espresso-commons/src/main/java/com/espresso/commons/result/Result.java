package com.espresso.commons.result;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Result implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Result.class);
    private static final long serialVersioinUID = 1L;

    private Integer code;
    private String message;
    private Object data;

    public static Result ok() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), null);
    }

    public static Result ok(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), data);
    }

    public static Result ok(String message, Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), message, data);
    }

    public static Result error(String message) {
        logger.debug("return error: code={}, message={}", ResultEnum.ERROR.getCode(), message);
        return new Result(ResultEnum.ERROR.getCode(), message, null);
    }

    public static Result build(int code, String message) {
        logger.debug("return result: code={}, message={}", code, message);
        return new Result(code, message, null);
    }

    public static Result build(ResultEnum resultEnum) {
        logger.debug("return result: code={}, message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), null);
    }
    public static Result build(ResultEnum resultEnum, Object data) {
        logger.debug("return result: code={}, message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result(resultEnum.getCode(), resultEnum.getDesc(), data);
    }

    public static Result buildWithArgs(ResultEnum resultEnum, Object... args) {
        logger.debug("return result: code={}, message={}", resultEnum.getCode(), String.format(resultEnum.getDesc(), args));
        return new Result(resultEnum.getCode(), String.format(resultEnum.getDesc(), args), null);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
