package club.huangliang.blog_smart.dao;

import club.huangliang.blog_smart.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParenCommentNull(Long blogId, Sort sort);
}
