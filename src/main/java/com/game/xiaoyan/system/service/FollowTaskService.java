package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.FollowTask;
import org.springframework.data.domain.Page;

public interface FollowTaskService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, FollowTask followTask);

    void add(FollowTask followTask);

    void update(FollowTask followTask);

    void updateTimeOutTask();
}
