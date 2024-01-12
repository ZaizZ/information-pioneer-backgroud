package pioneer.media.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.media.dto.SensitiveDto;
import pioneer.media.entity.WmSensitive;
import pioneer.media.mapper.WmSensitiveMapper;
import pioneer.media.service.IWmSensitiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 敏感词信息表 服务实现类
 */
@Service
@Slf4j
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements IWmSensitiveService {

    @Override
    public ResponseResult upload(MultipartFile file) throws IOException {
        //使用EasyExcel框架
        EasyExcel.read(file.getInputStream(), WmSensitive.class, new PageReadListener<WmSensitive>(dataList -> {
            for (WmSensitive ws : dataList) {
                //先判断表中是否已经有这个敏感词，没有再新增
                LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(WmSensitive::getSensitives,ws.getSensitives());
                int count = this.count(queryWrapper);
                if (count==0){
                    ws.setCreatedTime(new Date());
                    this.save(ws);
                }
            }
        })).sheet().doRead();

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult download(HttpServletResponse response) throws Exception {
        List<WmSensitive> list = this.list();

        //  文件下载
        //        一个流：文件输出流
        //        两个头： 1、文件的打开方式 inline(浏览器直接打开)   attachment(以附件形式下载)
        //               2、文件的mime类型 xlsx:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
        response.setHeader("Content-Disposition", "attachment; filename=Sensitive.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream(), WmSensitive.class)
                .sheet("敏感词")
                .doWrite(() -> {
                    return list;
                });
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listByName(SensitiveDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(dto.getName()!=null,WmSensitive::getSensitives, dto.getName());

        Page<WmSensitive> wmSensitivePage = new Page<>(dto.getPage(),dto.getSize());
        Page<WmSensitive> sensitivePage = this.page(wmSensitivePage, queryWrapper);

        return new PageResponseResult(dto.getPage(), dto.getSize(),sensitivePage.getTotal(),sensitivePage.getRecords());
    }
}
