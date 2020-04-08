package club.huangliang.blog_smart.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_blog")
@lombok.Data
public class Blog {
    @Id
    @GeneratedValue//主键的自增方式，自增
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob//这两个注解可以使得content的内容过大时使用懒加载的方法让它的内容从content变化成Longtext
    private String content;
    private String firstPicture;
    private String flag;//标记
    private Integer views;
    private String description;//博客描述
    private boolean appreciation;//赞赏
    private boolean shareStatement;//转载申明
    private boolean commentabled;
    private boolean published;
    @Transient//写了这个注解后这个属性不会进数据库
    private String tagIds;
    private boolean recommend;//推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date creaTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})//设置级联新增
    private List<Tag> tags=new ArrayList<>();
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private  List<Comment> comments=new ArrayList<>();
    //处理tagIds
    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }
    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
