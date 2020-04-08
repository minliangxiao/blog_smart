package club.huangliang.blog_smart.web.admin;

import club.huangliang.blog_smart.po.Tag;
import club.huangliang.blog_smart.service.BlogService;
import club.huangliang.blog_smart.service.TagService;

import club.huangliang.blog_smart.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags=tagService.listTagTop(10000000);//这儿这样子写肯定不行1000000必须想办法消除
        if (id==-1){
            id=tags.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTypeId",id);
        return "tags";
    }
}
