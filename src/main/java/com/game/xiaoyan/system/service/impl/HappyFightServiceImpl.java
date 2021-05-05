package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.entity.*;
import com.game.xiaoyan.system.repository.AccountInfoRepository;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.HappyFightTaskRepository;
import com.game.xiaoyan.system.repository.TaskAccountRepository;
import com.game.xiaoyan.system.service.CurTaskStateService;
import com.game.xiaoyan.system.service.HappyFightTaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.springdatajpa.zujijpa.Specifications;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class HappyFightServiceImpl implements HappyFightTaskService {

    @Resource
    private HappyFightTaskRepository happyFightTaskRepository;
    @Resource
    private TaskAccountRepository taskAccountRepository;
    @Resource
    private CurTaskStateService curTaskStateService;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<HappyFightTask> list(Integer page, Integer limit, AccountInfo accountInfo, HappyFightTask happyFightTask) {
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        happyFightTask.setAccountInfo(accountInfo);
        Specification<HappyFightTask> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(happyFightTask.getAccountInfo().getUsername()), "accountInfo.username", happyFightTask.getAccountInfo().getUsername())
                    .eq(happyFightTask.getAccountInfo().getType() != null, "accountInfo.type", happyFightTask.getAccountInfo().getType())
                    .eq(happyFightTask.getState() != null, "state",happyFightTask.getState())
                    .between(!StringUtils.isEmpty(happyFightTask.getSearchTime()), "createTime",DateUtil.parseDate(happyFightTask.getSearchTime()+" 00:00:00"),DateUtil.parseDate(happyFightTask.getSearchTime()+" 23:59:59"));
        });
        Sort sort = Sort.by("state").descending();
        return happyFightTaskRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return happyFightTaskRepository.findAll(Example.of(happyFightTask,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(HappyFightTask happyFightTask) {
        happyFightTaskRepository.save(happyFightTask);

    }


    @Override
    @Transactional
    public void update(HappyFightTask happyFightTask) {
        HappyFightTask  oldHappyFightTask  = happyFightTaskRepository.getOne(happyFightTask.getId());
        BeanUtils.copyNotNullProperties(happyFightTask,oldHappyFightTask);
        happyFightTaskRepository.save(oldHappyFightTask);
    }
    @Override
    @Transactional
    public void updateTimeOutTask() {
        Date dataTime = DateUtil.getAppointDate(-20);
        List<HappyFightTask> happyFightTasks =happyFightTaskRepository.getAllByStateAndCreateTimeBefore(0,dataTime);
        happyFightTasks.forEach(v->{
            v.setState(3);
            TaskAccount taskAccount =  taskAccountRepository.getByAccountInfoId(v.getAccountInfoId());
            curTaskStateService.releaseTaskState(taskAccount.getCurTaskStateId());
        });
        happyFightTaskRepository.flush();
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

