package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine,Integer>{
    Page<Machine> getByUserId(Integer userId, Pageable pageable);
}
