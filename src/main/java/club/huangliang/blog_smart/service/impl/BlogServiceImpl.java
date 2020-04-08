package club.huangliang.blog_smart.service.impl;

import club.huangliang.blog_smart.NotFoundException;
import club.huangliang.blog_smart.dao.BlogRepository;
import club.huangliang.blog_smart.po.Blog;
import club.huangliang.blog_smart.po.Tag;
import club.huangliang.blog_smart.po.Type;
import club.huangliang.blog_smart.service.BlogService;
import club.huangliang.blog_smart.util.MarkdownUtils;
import club.huangliang.blog_smart.util.MyBeanUtils;
import club.huangliang.blog_smart.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        /*
         * 重点知识点  public Predicate toPredicate
         * 1.这个方法只是个参数
         * 2.这个方法的参数 root表是操作类的对象 ，criteriaQuery表示sql的查询 ，criteriaBuilder表示查询条件的拼接
         *  predicates.add(criteriaBuilder.like(root.<String >get("title"),"%"+blog.getTitle()+"%"));//指定泛型，注意字符串类型要"%"拼接还要用like
         *   predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getType().getId()));//泛型是指操作对象的属性
         *
         * */
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));//????
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override//手动封装一些数据
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreaTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));//前面放参数后面放复制到的类(最后一个参数保证只复制有值的值)
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public List<Blog> listRecommendBlog(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findByRecommendBlog(pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);

    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        /*
         * 这个方法是难点有空一定要去搜索下JPA的criteriaQuery的api用法
         * */
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");

                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();//声明这个对象是为了 不改变从数据库返回对象的值》》因为改变了返回对象jpa可能就将数据库里的数据也改变了
        BeanUtils.copyProperties(blog, b);
        String content = blog.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));

        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
