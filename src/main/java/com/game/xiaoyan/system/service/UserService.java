package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User getByUsername(String username);

    Page<User> list(int pageNum, int pageSize, User user);

    User getById(Integer userId);

    boolean add(User user) throws Exception;

    boolean update(User user);

    boolean updateState(Integer userId, int state) throws Exception;

    boolean updatePsw(Integer userId, String username, String newPsw);

    boolean delete(Integer userId);


    //獲取驗證碼
    void getCaptcha(String phone);
    //註冊用戶
    User registerUser(String areaCode,String phone,String verificationCode,String nickname,String passWord)throws Exception;

    void modifyPassword(String phone, String verificationCode, String passWord)throws Exception;
    void modifyPassword(String oldPassWord, String newPassWord,User user)throws Exception;

    void modifyNick(String nickName)throws Exception;

    void savHeadPortrait(String fileName);

    void updateAppId(String appId);
}
