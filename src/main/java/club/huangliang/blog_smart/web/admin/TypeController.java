package club.huangliang.blog_smart.web.admin;

import club.huangliang.blog_smart.po.Type;
import club.huangliang.blog_smart.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
        , Model model){
//@PageableDefault注解参数是对分页的数据的补充，size是指页面的条数，sort是指根据id来排序，direction是指排列顺序参数表示倒序
        model.addAttribute("page",typeService.listType(pageable));
        typeService.listType(pageable);
        return "admin/types";
    }
    @GetMapping("/types/input")
    public String  input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @PostMapping("/types")
    public  String post(@Valid Type type , BindingResult result,RedirectAttributes attributes){
        /*
        这儿给一个参数提交上来的表单会自动封装成这个对象
        @Valid代表要校验Type对象，可以将后端的校验信息传输到前端去
        BindingResult 用于接收校验之后的结果（一定要与type靠着不然没有效果）
        * */
        //校验名字是否一样
        Type type2 = typeService.getTypeByName(type.getName());
        if (type2!=null){
            result.rejectValue("name","nameError","该分类已经存在");//s,s1为固定格式s2为返回的消息

        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type type1= typeService.saveType(type);
        if (type1==null){
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }
    //修改type页面跳转
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        /*@PathVariable能过接收到id
        * */
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    //修改type校验并进库
    @PostMapping("/types/{id}")
    public  String editPost(@Valid Type type , BindingResult result,@PathVariable Long id, RedirectAttributes attributes){

        Type type2 = typeService.getTypeByName(type.getName());
        if (type2!=null){
            result.rejectValue("name","nameError","该分类已经存在");//s,s1为固定格式s2为返回的消息

        }
        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type type1= typeService.updateType(id,type);
        if (type1==null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
