package pioneer.search.service;

import pioneer.common.dto.ResponseResult;
import pioneer.search.dto.ApArticleSearchDto;

public interface IArticleSearchService {
    ResponseResult search(ApArticleSearchDto dto);
}
