package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.entity.CurTaskState;
import com.game.xiaoyan.system.entity.LiveTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CurTaskStateRepository extends JpaRepository<CurTaskState,Integer>{
    @Query("update CurTaskState  c set c.curTaskType = 0 ,c.taskState = 0 where c.id=?1")
    @Modifying
    Integer releaseTaskState(Integer id);

    //重置所有任务
    @Modifying
    @Query("update CurTaskState  c set  c.taskState = 0,c.curTaskType = 0 ,c.followNum = 0,c.happyFight = 0,c.liveTime = 0")
    void resetTask();


    //超时更新任务执行状态
    @Modifying
    @Query("update CurTaskState  c set c.taskState = 0 where c.updateTme<?1 and c.curTaskType = 0")
    Integer updateAllByUpdateTmeBeforeAndState(Date dataTime, Integer value);

    @Modifying
    @Query("update  CurTaskState  c set  c.taskState = 1,c.curTaskType = ?2 where c.id  = ?1")
    Integer updateTaskType(Integer id,Integer type);

    @Query("update CurTaskState  c set c.liveTime = c.liveTime+?2 where c.id=?1")
    @Modifying
    void updateLiveTime(Integer id, Long time);
}
