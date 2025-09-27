package org.example.springhemingproject02.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springhemingproject02.exception.Code;
import org.example.springhemingproject02.exception.XException;
import org.example.springhemingproject02.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {@ExceptionHandler(XException.class)
    public ResultVO handleValidException(XException exception) {
        if (exception.getCode() != null) {
            return ResultVO.error(exception.getCode());
        }
        return ResultVO.error(exception.getCodeN(), exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception exception) {
        return ResultVO.error(Code.ERROR, exception.getMessage());
    }
}
