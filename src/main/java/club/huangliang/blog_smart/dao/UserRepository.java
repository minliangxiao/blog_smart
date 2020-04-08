package club.huangliang.blog_smart.dao;

import club.huangliang.blog_smart.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {//<>中的参数User表示操作对象，Long表示主键类型

    User findByUsernameAndPassword(String username, String password);
}
