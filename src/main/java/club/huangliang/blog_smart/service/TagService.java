package club.huangliang.blog_smart.service;

import club.huangliang.blog_smart.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);//分页查询

    List<Tag> listTag();

    Tag updateTag(Long id, Tag tag);

    void deleteType(Long id);

    List<Tag> listTag(String ids);//用于添加博客时将上传的ids进行分割

    List<Tag> listTagTop(Integer size);
}
