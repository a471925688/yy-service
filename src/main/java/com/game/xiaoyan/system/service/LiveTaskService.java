package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.LiveTask;
import com.game.xiaoyan.system.vo.LiveTaskVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LiveTaskService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, LiveTask liveTask);

    void add(LiveTask liveTask);

    void update(LiveTask liveTask);
    //检查超时任务
    void checkTimeOutTask();

    LiveTaskVo getLiveTask(Long time,Integer state);

    //更新直播时长
    void updateLiveTime(Integer time, Integer id,Integer taskId);
    //重置未完成的为过期
    void resetTask();
}
