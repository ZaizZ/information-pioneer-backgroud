package pioneer.article.service.impl;

import pioneer.article.entity.ApArticleContent;
import pioneer.article.mapper.ApArticleContentMapper;
import pioneer.article.service.IApArticleContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * APP已发布文章内容表 服务实现类
 */
@Service
public class ApArticleContentServiceImpl extends ServiceImpl<ApArticleContentMapper, ApArticleContent> implements IApArticleContentService {

}
