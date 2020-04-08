package club.huangliang.blog_smart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {
    @GetMapping("/about")
    public String about() {

        return "about";
    }
}
