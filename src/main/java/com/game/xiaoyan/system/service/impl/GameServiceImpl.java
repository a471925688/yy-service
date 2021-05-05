package com.game.xiaoyan.system.service.impl;

import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.system.entity.Game;
import com.game.xiaoyan.system.repository.GameRepository;
import com.game.xiaoyan.system.service.GameService;
import org.aspectj.util.FileUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Resource
    private GameRepository gameRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, Integer userId) {
        return gameRepository.getByUserId(userId,PageRequest.of(page-1,limit));
    }

    @Override
    public   List<Game> getAllGame() {
        return gameRepository.findAll();
    }



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(Game game)throws Exception {
        gameRepository.save(game);
    }


    @Override
    @Transactional
    public void update(Game game)throws Exception {
        Game oldGame = gameRepository.getOne(game.getId());
        BeanUtils.copyNotNullProperties(game,oldGame);
        gameRepository.save(oldGame);
    }

    @Override
    @Transactional
    public void del(Integer gameId) {
        gameRepository.deleteById(gameId);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    private  void fileHandle(String name) throws IOException {
        if(StringUtils.isEmpty(name))
            return;
        File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+name);
        if(file.exists()){
            FileUtil.copyFile(file,new File(ProjectConfig.FILE_IMAGE_DIRECTORY+name));
            file.delete();
        }
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

