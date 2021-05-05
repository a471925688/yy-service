package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.HappyFightTask;
import org.springframework.data.domain.Page;

public interface CurTaskStateService {
    //释放任务状态
    void releaseTaskState(Integer id);
    //重置所有任务
    void resetTask();
    //每隔2分钟检查超时任务状态,防止前端死掉
    void updateTimeOutTask();

    boolean updateTaskType(Integer id,Integer taskType);
    //添加直播时间
    void updateLiveTime(Integer id,Integer time);
}
