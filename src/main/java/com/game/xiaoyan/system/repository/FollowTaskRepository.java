package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BrushGiftsTask;
import com.game.xiaoyan.system.entity.FollowTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FollowTaskRepository extends JpaRepository<FollowTask,Integer>, JpaSpecificationExecutor<FollowTask>{
    Page<FollowTask> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoType(String username,Integer type);

    FollowTask getByAccountInfoUsernameAndAccountInfoType(String userName,Integer type);

    List<FollowTask> getAllByStateAndCreateTimeBefore(Integer state, Date dataTime);
}
