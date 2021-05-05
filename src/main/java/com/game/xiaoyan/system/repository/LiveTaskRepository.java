package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.LiveTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LiveTaskRepository extends JpaRepository<LiveTask,Integer>, JpaSpecificationExecutor<LiveTask>{
    Page<LiveTask> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoType(String username,Integer type);

    LiveTask getByAccountInfoUsernameAndAccountInfoType(String userName,Integer type);

    List<LiveTask> getAllByUpdateTmeBeforeAndState(Date date,Integer state);

    @Query("update LiveTask  l set l.following = 0 where l.id=?1")
    void releaseFollowState(Integer liveTaskId);

    //根据状态和账号id获取任务信息
    LiveTask  getByStateAndAccountInfoId(Integer state,Integer accountInfoId);

    //重置过期状态
    @Query("UPDATE LiveTask  l set l.state = 3 where l.state=2")
    @Modifying
    void resetTask();
}
