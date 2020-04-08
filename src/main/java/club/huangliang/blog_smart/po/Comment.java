package club.huangliang.blog_smart.po;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String  content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    private  Blog blog;
    @OneToMany(mappedBy = "parenComment")//这个没有理解
    private List<Comment>replyComments=new ArrayList<>();
    @ManyToOne//这个没有理解
    private Comment parenComment;
    private boolean adminComment;


}
