package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.User;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface MemberService {
    Page list(Integer page, Integer limit, User user);

    void add(User user);

    Integer countBytime(Date time);
    Integer countAll();
    Integer countByActive(Date time);
    void update(User user);

}
