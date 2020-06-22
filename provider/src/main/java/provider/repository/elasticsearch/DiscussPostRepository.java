package provider.repository.elasticsearch;

import provider.dto.DiscussPostDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPostDTO, Integer> {

}
