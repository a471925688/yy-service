package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsTask;
import com.game.xiaoyan.system.entity.FollowTask;
import com.game.xiaoyan.system.repository.AccountInfoRepository;
import com.game.xiaoyan.system.repository.FollowTaskRepository;
import com.game.xiaoyan.system.repository.LiveTaskRepository;
import com.game.xiaoyan.system.service.FollowTaskService;
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
public class FollowTaskServiceImpl implements FollowTaskService {

    @Resource
    private FollowTaskRepository followTaskRepository;
    @Resource
    private LiveTaskRepository liveTaskRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<FollowTask> list(Integer page, Integer limit, AccountInfo accountInfo, FollowTask followTask) {
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        followTask.setAccountInfo(accountInfo);
        Specification<FollowTask> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(followTask.getAccountInfo().getUsername()), "accountInfo.username", followTask.getAccountInfo().getUsername())
                    .eq(followTask.getAccountInfo().getType() != null, "accountInfo.type", followTask.getAccountInfo().getType())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",accountInfo.getUserId())
                    .eq(!StringUtils.isEmpty(followTask.getRomNo()), "romNo", followTask.getRomNo())
                    .eq(followTask.getState() != null, "state",followTask.getState())
                    .between(!StringUtils.isEmpty(followTask.getSearchTime()), "createTime", DateUtil.parseDate(followTask.getSearchTime()+" 00:00:00"),DateUtil.parseDate(followTask.getSearchTime()+" 23:59:59"));
        });
        Sort sort = Sort.by("state").descending();
        return followTaskRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return followTaskRepository.findAll(Example.of(followTask,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(FollowTask followTask) {
        followTaskRepository.save(followTask);

    }


    @Override
    @Transactional
    public void update(FollowTask followTask) {
        FollowTask  oldFollowTask  = followTaskRepository.getOne(followTask.getId());
        BeanUtils.copyNotNullProperties(followTask,oldFollowTask);
        followTaskRepository.save(oldFollowTask);
    }

    @Override
    @Transactional
    public void updateTimeOutTask() {
        Date dataTime = DateUtil.getAppointDate(-6);
        List<FollowTask> followTasks =followTaskRepository.getAllByStateAndCreateTimeBefore(0,dataTime);
        followTasks.forEach(v->{
            v.setState(2);
            liveTaskRepository.releaseFollowState(v.getLiveTaskId());
        });
        followTaskRepository.flush();
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

