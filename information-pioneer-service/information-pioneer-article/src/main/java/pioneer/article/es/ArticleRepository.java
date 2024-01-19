package pioneer.article.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pioneer.article.entity.ApArticleSearch;

public interface ArticleRepository extends ElasticsearchRepository<ApArticleSearch,Long> {
}
