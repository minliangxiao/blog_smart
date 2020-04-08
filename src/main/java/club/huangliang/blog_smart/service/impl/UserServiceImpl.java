package club.huangliang.blog_smart.service.impl;

import club.huangliang.blog_smart.dao.UserRepository;
import club.huangliang.blog_smart.po.User;
import club.huangliang.blog_smart.service.UserService;
import club.huangliang.blog_smart.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
