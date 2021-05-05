package com.game.xiaoyan.system.service.impl;

import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.entity.Game;
import com.game.xiaoyan.system.entity.LiveTask;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.GameRepository;
import com.game.xiaoyan.system.service.CurTaskStateService;
import com.game.xiaoyan.system.service.GameService;
import org.aspectj.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class CurTaskStateServiceImpl implements CurTaskStateService {

    @Resource
    private CurTaskStateRepository curTaskStateRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Override
    public void releaseTaskState(Integer id) {
        curTaskStateRepository.releaseTaskState(id);
    }

    @Override
    public void resetTask() {
        curTaskStateRepository.resetTask();
    }

    @Override
    @Transactional
    public void updateTimeOutTask() {
        Date dataTime = DateUtil.getAppointDate(-2);
        curTaskStateRepository.updateAllByUpdateTmeBeforeAndState(dataTime, ProjectConfig.LiveTaskState.RUNING.getValue());
    }

    @Override
    public boolean updateTaskType(Integer id, Integer taskType) {

        return curTaskStateRepository.updateTaskType(id,taskType)>0;
    }

    @Override
    @Transactional
    public void updateLiveTime(Integer id,Integer time) {
        curTaskStateRepository.updateLiveTime(id,Long.valueOf(time));
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

