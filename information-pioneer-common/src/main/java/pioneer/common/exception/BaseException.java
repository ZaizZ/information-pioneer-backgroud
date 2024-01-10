package pioneer.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;

//全局异常处理
@Slf4j
//@RestControllerAdvice(annotations = {RestController.class, Controller.class}) //拦截加了RestController Controller这两个注解的
@RestControllerAdvice //针对所有的controller
public class BaseException {
    //指定处理什么类型的异常
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleException(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}
