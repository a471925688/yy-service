package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.TaskAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo,Integer>{

}
