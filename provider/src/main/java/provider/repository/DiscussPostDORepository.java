package provider.repository;

import provider.domain.DiscussPostDO;
import provider.vo.DiscussPostVO;

import java.util.List;

/**
 * @authorgouhuo on 2020/04/27.
 */
public interface DiscussPostDORepository {
    int updateCommentCount(int entityId, int count);

    int update(DiscussPostVO post);

    DiscussPostDO get(int id);

    boolean insert(DiscussPostDO post);

    int countByUser(int userId);

    List<DiscussPostDO> list(int userId, int offset, int limit, int orderMode);
}
