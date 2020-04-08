package club.huangliang.blog_smart.service;

import club.huangliang.blog_smart.po.User;

public interface UserService {
    User checkUser(String username,String password);

}
