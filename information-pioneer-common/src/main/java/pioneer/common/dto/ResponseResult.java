package pioneer.common.dto;

import lombok.Data;
import pioneer.common.enums.AppHttpCodeEnum;

import java.io.Serializable;


/**
 * 通用的结果返回类
 * @param <T>
 */
@Data
public class ResponseResult<T> implements Serializable {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String errorMessage;
    /**
     * 返回数据
     */
    private T data;

    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
    }

    public static ResponseResult okResult() {
        return okResult(null);
    }

    public static <T> ResponseResult okResult(T data) {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(), data);
    }

    public static ResponseResult errorResult(int code, String msg) {
        return new ResponseResult(code, msg);
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums) {
        return new ResponseResult(enums.getCode(), enums.getErrorMessage());
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums, String errorMessage) {
        return new ResponseResult(enums.getCode(), errorMessage);
    }

}
