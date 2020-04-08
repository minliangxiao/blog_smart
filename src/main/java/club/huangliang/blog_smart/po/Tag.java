package club.huangliang.blog_smart.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags")//指定被Blog类里面的tags所维护关系
    private List<Blog> blogs = new ArrayList<>();
}
