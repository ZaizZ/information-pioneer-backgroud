package pioneer.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;
import pioneer.media.service.IAuditService;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IAuditService auditService;

    @GetMapping("/audit/{id}")
    public ResponseResult audit(@PathVariable("id") Integer id) {
        auditService.audit(id);
        return ResponseResult.okResult();
    }
}
