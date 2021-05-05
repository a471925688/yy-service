package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.Banner;
import com.game.xiaoyan.system.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer>{
    Page<Game> getByUserId(Integer userId, Pageable pageable);
}
