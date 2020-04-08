package club.huangliang.blog_smart.service;

import club.huangliang.blog_smart.po.Blog;
import club.huangliang.blog_smart.po.Tag;
import club.huangliang.blog_smart.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    List<Blog> listRecommendBlog(Integer size);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Long tagId, Pageable pageable);//根据标签查询分页


    Page<Blog> listBlog(String query, Pageable pageable);

    Blog getAndConvert(Long id);//获取并转换博文markdown的类

    Map<String, List<Blog>> archiveBlog();//归档

    Long countBlog();
}
