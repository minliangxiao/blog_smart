package club.huangliang.blog_smart.web.admin;

import club.huangliang.blog_smart.po.Blog;
import club.huangliang.blog_smart.po.Tag;
import club.huangliang.blog_smart.po.User;
import club.huangliang.blog_smart.service.BlogService;
import club.huangliang.blog_smart.service.TagService;
import club.huangliang.blog_smart.service.TypeService;
import club.huangliang.blog_smart.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog( pageable,blog));
        return LIST;
    }
    //局部查询博客
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog( pageable,blog));
        return "admin/blogs::blogList";
    }
    //新增页面跳转
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    //获取type和tag列表的方法
    public  void setTypeAndTag(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }
     //修改页面跳转
     @GetMapping("/blogs/{id}/input")
     public String editInput(@PathVariable Long id, Model model){
         setTypeAndTag(model);
         Blog blog=blogService.getBlog(id);
         blog.init();
         model.addAttribute("blog",blog);
         return INPUT;
     }

    //修改和添加(这个没有做后端的数据校验)
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
             blog.setUser((User) session.getAttribute("user"));
             blog.setType(typeService.getType(blog.getType().getId()));//这儿页面只传了一个id上来然后再用type的id将type查出来
            blog.setTags(tagService.listTag(blog.getTagIds()));
            Blog blog1;
            if (blog.getId()==null){
                blog1=blogService.saveBlog(blog);
            }
            else {
                blog1=blogService.updateBlog(blog.getId(),blog);
            }
            if(blog1==null){
                attributes.addFlashAttribute("message","操作失败");
            }
            else {
                attributes.addFlashAttribute("message","操作成功");
            }

        return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public  String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


}
