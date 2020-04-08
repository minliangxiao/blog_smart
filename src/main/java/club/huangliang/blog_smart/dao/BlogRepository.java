package club.huangliang.blog_smart.dao;

import club.huangliang.blog_smart.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long >, JpaSpecificationExecutor<Blog> {
    /*JpaSpecificationExecutor<Blog>jpa帮助高级查询的类泛型参数是操作的对象
    * */
    @Query("select  b from  Blog b where  b.recommend=true")
    List<Blog> findByRecommendBlog(Pageable pageable);//找寻页面上的推荐博客

    @Query("select b from  Blog b  where b.title like ?1 or  b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying//完成修改不支持新增
    @Query("update Blog b set b.views =b.views+1 where b.id=?1")
    int updateViews(Long id);
    //jpa调用mysql里面的内置函数需要一个function
    @Query("select function('date_format',b.updateTime,'%Y')as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y')=?1")
    List<Blog> findByYear(String year);

}
