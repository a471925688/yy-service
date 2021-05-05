package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.Game;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GameService {
    Page list(Integer page, Integer limit,  Integer userId);
    List<Game> getAllGame();

    void add(Game game)throws Exception;

    void update(Game game)throws Exception;


    void del(Integer gameId);
}
