package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.TaskAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAccountRepository extends JpaRepository<TaskAccount,Integer>, JpaSpecificationExecutor<TaskAccount>{

    boolean existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String username,Integer type,Integer userId);

    TaskAccount getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String userName,Integer type,Integer userId);
    TaskAccount getByAccountInfoId(Integer id);

    //获取直播时间低于time的空闲任务账号
    TaskAccount getFirstByCurTaskStateTaskStateAndCurTaskStateLiveTimeLessThanAndStateOrderByBatchNoNo(Integer taskState,Long time,Integer state);
}
