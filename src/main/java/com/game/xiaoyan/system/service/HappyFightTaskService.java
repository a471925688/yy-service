package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.HappyFightTask;
import org.springframework.data.domain.Page;

public interface HappyFightTaskService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, HappyFightTask liveTask);

    void add(HappyFightTask liveTask);

    void update(HappyFightTask liveTask);


    void updateTimeOutTask();
}
