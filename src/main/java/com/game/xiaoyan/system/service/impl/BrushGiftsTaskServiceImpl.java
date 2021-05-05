package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsTask;
import com.game.xiaoyan.system.entity.CurTaskState;
import com.game.xiaoyan.system.entity.TaskAccount;
import com.game.xiaoyan.system.repository.BrushGiftsTaskRepository;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.HappyFightTaskRepository;
import com.game.xiaoyan.system.repository.TaskAccountRepository;
import com.game.xiaoyan.system.service.BrushGiftsTaskService;
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
public class BrushGiftsTaskServiceImpl implements BrushGiftsTaskService {

    @Resource
    private BrushGiftsTaskRepository brushGiftsTaskRepository;
    @Resource
    private HappyFightTaskRepository happyFightTaskRepository;
    @Resource
    private CurTaskStateRepository curTaskStateRepository;

    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<BrushGiftsTask> list(Integer page, Integer limit, AccountInfo accountInfo, BrushGiftsTask brushGiftsTask) {
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        brushGiftsTask.setAccountInfo(accountInfo);
        Specification<BrushGiftsTask> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(brushGiftsTask.getAccountInfo().getUsername()), "accountInfo.username", brushGiftsTask.getAccountInfo().getUsername())
                    .eq(brushGiftsTask.getAccountInfo().getType() != null, "accountInfo.type", brushGiftsTask.getAccountInfo().getType())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",accountInfo.getUserId())
                    .eq(!StringUtils.isEmpty(brushGiftsTask.getRomNo()), "romNo", brushGiftsTask.getRomNo())
                    .eq(brushGiftsTask.getState() != null, "state",brushGiftsTask.getState())
                    .between(!StringUtils.isEmpty(brushGiftsTask.getSearchTime()), "createTime", DateUtil.parseDate(brushGiftsTask.getSearchTime()+" 00:00:00"),DateUtil.parseDate(brushGiftsTask.getSearchTime()+" 23:59:59"));
        });
        Sort sort = Sort.by("state").descending();
        return brushGiftsTaskRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return brushGiftsTaskRepository.findAll(Example.of(brushGiftsTask,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(BrushGiftsTask brushGiftsTask) {
        brushGiftsTaskRepository.save(brushGiftsTask);

    }


    @Override
    @Transactional
    public void update(BrushGiftsTask brushGiftsTask) {
        BrushGiftsTask  oldBrushGiftsTask  = brushGiftsTaskRepository.getOne(brushGiftsTask.getId());
        BeanUtils.copyNotNullProperties(brushGiftsTask,oldBrushGiftsTask);
        brushGiftsTaskRepository.save(oldBrushGiftsTask);
    }

    @Override
    @Transactional
    public void updateTimeOutTask() {
        Date dataTime = DateUtil.getAppointDate(-2);
        List<BrushGiftsTask> brushGiftsTasks =brushGiftsTaskRepository.getAllByStateAndCreateTimeBefore(0,dataTime);
        brushGiftsTasks.forEach(v->{
            v.setState(3);
            happyFightTaskRepository.releasebrushGiftsing(v.getHappyFightTaskId());
        });
        brushGiftsTaskRepository.flush();
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

