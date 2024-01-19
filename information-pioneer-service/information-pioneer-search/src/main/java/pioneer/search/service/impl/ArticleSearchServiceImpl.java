package pioneer.search.service.impl;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.util.UserThreadLocalUtil;
import pioneer.search.dto.ApArticleSearchDto;
import pioneer.search.entity.ApArticleSearch;
import pioneer.search.repository.ArticleRepository;
import pioneer.search.service.IApUserSearchService;
import pioneer.search.service.IArticleSearchService;

@Service
public class ArticleSearchServiceImpl implements IArticleSearchService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private IApUserSearchService apUserSearchService;


    @Override
    public ResponseResult search(ApArticleSearchDto dto) {
        System.out.println("搜索当前线程的名称："+Thread.currentThread().getName());


//        如果关键字为空 直接返回一个null
        if(dto==null|| StringUtils.isEmpty(dto.getSearchWords())){
            return null;
        }
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", dto.getSearchWords());
        // 分页并且根据发布时间排序
        PageRequest pageRequest = PageRequest.of( dto.getPage().intValue() - 1, dto.getSize(), Sort.by(Sort.Direction.DESC, "publishTime"));
        Page<ApArticleSearch> searchPage = articleRepository.search(queryBuilder, pageRequest);


        // 异步保存用户的搜索记录  1 启动类上添加注解   2 新建任务类 添加异步方法
        // 获取当前的用户 异步属于多线程，需要将当前线程的用户传过去
        User user = UserThreadLocalUtil.get();

        //        向mongo中插入历史记录  谁 在什么时候 什么关键字
        apUserSearchService.saveUserSearch(dto,user);

        //只返回列表就可以
        return ResponseResult.okResult(searchPage.getContent());
    }
}

