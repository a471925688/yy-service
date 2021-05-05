package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BrushGiftsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BrushGiftsTaskRepository extends JpaRepository<BrushGiftsTask,Integer>, JpaSpecificationExecutor<BrushGiftsTask>{
    Page<BrushGiftsTask> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoType(String username,Integer type);

    BrushGiftsTask getByAccountInfoUsernameAndAccountInfoType(String userName,Integer type);


    List<BrushGiftsTask>   getAllByStateAndCreateTimeBefore(Integer state, Date time);
}
