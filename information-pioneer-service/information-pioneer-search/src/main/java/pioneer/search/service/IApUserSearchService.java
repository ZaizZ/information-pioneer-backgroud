package pioneer.search.service;

import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.search.dto.ApArticleSearchDto;

public interface IApUserSearchService {
    public void saveUserSearch(ApArticleSearchDto dto, User user);

    ResponseResult loadHistory(ApArticleSearchDto dto);

    ResponseResult delHistory(ApArticleSearchDto dto);
}
