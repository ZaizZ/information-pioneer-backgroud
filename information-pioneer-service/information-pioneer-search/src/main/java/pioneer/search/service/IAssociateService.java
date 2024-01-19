package pioneer.search.service;

import pioneer.common.dto.ResponseResult;
import pioneer.search.dto.ApArticleSearchDto;

public interface IAssociateService {
    ResponseResult searchAssociate(ApArticleSearchDto dto);
}
