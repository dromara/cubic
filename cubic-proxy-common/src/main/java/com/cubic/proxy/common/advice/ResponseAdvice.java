package com.cubic.proxy.common.advice;

import com.alibaba.fastjson.JSON;
import com.cubic.proxy.common.constant.ResultCode;
import com.cubic.proxy.common.module.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一数据格式返回
 *
 * @ClassName CubicResponseAdvice
 * @Author QIANGLU
 * @Date 2020/12/14 11:21
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {


    /**
     * 自动处理Exception，包装为DataResult
     */
    @ExceptionHandler(Exception.class)
    public DataResult handleApiException(HttpServletRequest request, Exception ex) {
        log.error("process url {} failed", request.getRequestURL().toString(), ex);
        return DataResult.fail(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ex.getMessage());
    }


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (!(body instanceof DataResult)) {

            DataResult result = DataResult.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc(), body);

            if (body instanceof String) {
                return JSON.toJSONString(result);
            }

            return result;
        }

        return body;
    }
}
