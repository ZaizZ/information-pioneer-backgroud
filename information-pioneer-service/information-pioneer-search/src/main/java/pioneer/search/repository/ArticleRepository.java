package pioneer.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pioneer.search.entity.ApArticleSearch;

public interface ArticleRepository extends ElasticsearchRepository<ApArticleSearch, Long> {
}
