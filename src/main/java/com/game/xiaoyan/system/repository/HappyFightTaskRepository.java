package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BrushGiftsTask;
import com.game.xiaoyan.system.entity.HappyFightTask;
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
public interface HappyFightTaskRepository extends JpaRepository<HappyFightTask,Integer>, JpaSpecificationExecutor<HappyFightTask>{
    Page<HappyFightTask> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoType(String username,Integer type);

    HappyFightTask getByAccountInfoUsernameAndAccountInfoType(String userName,Integer type);

    List<HappyFightTask> getAllByStateAndCreateTimeBefore(Integer state, Date dataTime);


    //释放刷礼物任务状态
    @Modifying
    @Query("update  HappyFightTask  h set h.brushGiftsing = 0 where h.id=?1 ")
    boolean releasebrushGiftsing(Integer id);
}
