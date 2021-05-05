package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.exception.CodeAndMsg;
import com.game.xiaoyan.common.exception.CustormException;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.CurTaskState;
import com.game.xiaoyan.system.entity.LiveTask;
import com.game.xiaoyan.system.entity.TaskAccount;
import com.game.xiaoyan.system.repository.*;
import com.game.xiaoyan.system.service.CurTaskStateService;
import com.game.xiaoyan.system.service.LiveTaskService;
import com.game.xiaoyan.system.vo.LiveTaskVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.springdatajpa.zujijpa.Specifications;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LiveTaskServiceImpl implements LiveTaskService {

    @Resource
    private LiveTaskRepository liveTaskRepository;
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Resource
    private TaskAccountRepository taskAccountRepository;
    @Resource
    private CurTaskStateService curTaskStateService;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<LiveTask> list(Integer page, Integer limit, AccountInfo accountInfo, LiveTask liveTask) {
        accountInfo.setType(ProjectConfig.AccountType.TASK.getValue());
        liveTask.setAccountInfo(accountInfo);
        Specification<LiveTask> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(liveTask.getAccountInfo().getUsername()), "accountInfo.username", liveTask.getAccountInfo().getUsername())
                    .eq(liveTask.getAccountInfo().getType() != null, "accountInfo.type", liveTask.getAccountInfo().getType())
                    .eq(liveTask.getState() != null, "state",liveTask.getState())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",accountInfo.getUserId())
                    .between(!StringUtils.isEmpty(liveTask.getSearchTime()), "createTime", DateUtil.parseDate(liveTask.getSearchTime()+" 00:00:00"),DateUtil.parseDate(liveTask.getSearchTime()+" 23:59:59"));
        });
        Sort sort = Sort.by("state").descending();
        return liveTaskRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return liveTaskRepository.findAll(Example.of(liveTask,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(LiveTask liveTask) {
        liveTaskRepository.save(liveTask);

    }


    @Override
    @Transactional
    public void update(LiveTask liveTask) {
        LiveTask  oldLiveTask  = liveTaskRepository.getOne(liveTask.getId());
        oldLiveTask.setUpdateTme(new Date());
        BeanUtils.copyNotNullProperties(liveTask,oldLiveTask);
        liveTaskRepository.save(oldLiveTask);
    }

    @Override
    @Transactional
    public void checkTimeOutTask() {
        Date dataTime = DateUtil.getAppointDate(-2);
        List<LiveTask> list = liveTaskRepository.getAllByUpdateTmeBeforeAndState(dataTime, ProjectConfig.LiveTaskState.RUNING.getValue());
        list.forEach(v->{
           TaskAccount taskAccount =  taskAccountRepository.getByAccountInfoId(v.getAccountInfoId());
            curTaskStateService.releaseTaskState(taskAccount.getCurTaskStateId());
            v.setState(ProjectConfig.LiveTaskState.SUSPEND.getValue());
        });
        liveTaskRepository.flush();
    }

    @Override
    @Transactional
    public LiveTaskVo getLiveTask(Long time,Integer state) {
        TaskAccount taskAccount =  taskAccountRepository.getFirstByCurTaskStateTaskStateAndCurTaskStateLiveTimeLessThanAndStateOrderByBatchNoNo(0,time,state);
        if(taskAccount==null){
            return null;
        }
        if(!curTaskStateService.updateTaskType(taskAccount.getCurTaskStateId(),1)){
            throw  new CustormException("改变任务状态失败，请重新获取",1);
        }
        LiveTask liveTask = liveTaskRepository.getByStateAndAccountInfoId(2,taskAccount.getAccountInfoId());
        if(liveTask==null){
            liveTask = LiveTask.builder()
                    .accountInfoId(taskAccount.getAccountInfoId()).build();
        }
        liveTask.setState(0);
        liveTask.setUpdateTme(new Date());
        liveTask = liveTaskRepository.save(liveTask);

        LiveTaskVo liveTaskVo = LiveTaskVo.builder()
                .liveTime(taskAccount.getCurTaskState().getLiveTime())
                .curTaskStateId(taskAccount.getCurTaskStateId())
                .password(taskAccount.getAccountInfo().getPassword())
                .username(taskAccount.getAccountInfo().getUsername())
                .romNo(taskAccount.getRomNo())
                .yyNo(taskAccount.getYyNo())
                .liveTaskId(liveTask.getId())
                .state(taskAccount.getState()).build();
        return liveTaskVo;
    }

    @Override
    public void updateLiveTime(Integer time, Integer liveId,Integer taskId) {
        LiveTask liveTask = liveTaskRepository.getOne(liveId);
        liveTask.setTime(liveTask.getTime()+time);
        liveTask.setUpdateTme(new Date());
        curTaskStateService.updateLiveTime(taskId,time);
        liveTaskRepository.flush();
    }

    @Override
    @Transactional
    public void resetTask() {
        liveTaskRepository.resetTask();
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

