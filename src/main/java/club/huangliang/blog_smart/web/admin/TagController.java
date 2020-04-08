package club.huangliang.blog_smart.web.admin;

import club.huangliang.blog_smart.po.Tag;
import club.huangliang.blog_smart.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));

        return "admin/tags";
    }
    //添加
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    //修改标签
    @GetMapping("/tags/{id}/input")
    public String editinput(@PathVariable Long id,Model model){
         model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }
    //添加校验
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","不能添加重复类");
        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag tag2=tagService.saveTag(tag);
        if (tag2==null){
            attributes.addFlashAttribute("messsage","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }
    //更新校验
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    //删除
    @GetMapping("/tags/{id}/delete")
    public  String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteType(id);
        return "redirect:/admin/tags";
    }
}
