package club.huangliang.blog_smart.service;

import club.huangliang.blog_smart.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);


}
