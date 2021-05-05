package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BrushGiftsAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BrushGiftsAccountRepository extends JpaRepository<BrushGiftsAccount,Integer>, JpaSpecificationExecutor<BrushGiftsAccount>{
    Page<BrushGiftsAccount> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String username,Integer type,Integer userId);

    BrushGiftsAccount getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String userName,Integer type,Integer userId);
}
