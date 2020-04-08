package club.huangliang.blog_smart.dao;

import club.huangliang.blog_smart.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String  name);
    @Query("select t from Tag t")
    List<Tag> findTagTop(Pageable pageable);

}
