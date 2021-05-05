package com.game.xiaoyan.system.timingtask;

import com.game.xiaoyan.system.repository.GameRepository;
import com.game.xiaoyan.system.service.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultithreadScheduleTask {

    @Resource
    private LiveTaskService  liveTaskService;
    @Resource
    private HappyFightTaskService happyFightTaskService;
    @Resource
    private BrushGiftsTaskService brushGiftsTaskService;
    @Resource
    private FollowTaskService followTaskService;
    @Resource
    private CurTaskStateService curTaskStateService;

    static private Integer index=0;

    @Scheduled(fixedRate=1000*60*2)  //更新結束時間大於2分鐘的預約
    public void updateDeslAState(){
        liveTaskService.checkTimeOutTask();
        brushGiftsTaskService.updateTimeOutTask();
        curTaskStateService.updateTimeOutTask();
        if(index%3==0){//超过6分钟没有完成的点关注判定为失败
            followTaskService.updateTimeOutTask();
            System.out.println("改变超过6分钟没有完成点关注任务状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        }
        if(index%5==0){//超过20分钟没有完成的斗播判定为失败
            happyFightTaskService.updateTimeOutTask();
            System.out.println("改变超过20分钟没有完成斗播的任务状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        }
        index++;
        System.out.println("改变超过两分钟没有更新直播时间的直播任务状态 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }


    @Async
    @Scheduled(cron = "0 0 0 * * ?")  //每天0點重置所有任务
    public void updateBanner(){
        curTaskStateService.resetTask();
        liveTaskService.resetTask();
    }

}
