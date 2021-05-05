package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsTask;
import org.springframework.data.domain.Page;

public interface BrushGiftsTaskService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, BrushGiftsTask brushGiftsTask);

    void add(BrushGiftsTask brushGiftsTask);

    void update(BrushGiftsTask brushGiftsTask);

    //刷礼物任务超时处理
    void updateTimeOutTask();
}
