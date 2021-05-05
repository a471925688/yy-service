package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BatchNo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchNoRepository extends JpaRepository<BatchNo,Integer>{
    Page<BatchNo> getByUserId(Integer userId, Pageable pageable);
}
