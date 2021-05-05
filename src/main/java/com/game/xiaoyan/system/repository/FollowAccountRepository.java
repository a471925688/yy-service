package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.FollowAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowAccountRepository extends JpaRepository<FollowAccount,Integer>, JpaSpecificationExecutor<FollowAccount>{
    Page<FollowAccount> getByAccountInfoUserId(Integer userId, Pageable pageable);

    boolean existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String username,Integer type,Integer userId);

    FollowAccount getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(String userName,Integer type,Integer userId);
}
