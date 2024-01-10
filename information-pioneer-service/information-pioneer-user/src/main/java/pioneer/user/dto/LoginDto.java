package pioneer.user.dto;

import lombok.Data;

@Data
public class LoginDto {

    //设备id
    private String equipmentId;

    //手机号
    private String phone;

    //密码
    private String password;
}
