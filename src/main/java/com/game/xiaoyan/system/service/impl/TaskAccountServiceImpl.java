package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.exception.CodeAndMsg;
import com.game.xiaoyan.common.exception.CustormException;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.common.utils.StringUtil;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.entity.CurTaskState;
import com.game.xiaoyan.system.entity.TaskAccount;
import com.game.xiaoyan.system.repository.AccountInfoRepository;
import com.game.xiaoyan.system.repository.BatchNoRepository;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.TaskAccountRepository;
import com.game.xiaoyan.system.service.TaskAccountService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.springdatajpa.zujijpa.Specifications;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskAccountServiceImpl implements TaskAccountService {

    @Resource
    private TaskAccountRepository taskAccountRepository;
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Resource
    private CurTaskStateRepository curTaskStateRepository;
    @Resource
    private BatchNoRepository batchNoRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<TaskAccount> list(Integer page, Integer limit, AccountInfo accountInfo, TaskAccount taskAccount) {
        accountInfo.setType(ProjectConfig.AccountType.TASK.getValue());
        taskAccount.setAccountInfo(accountInfo);
        Specification<TaskAccount> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(taskAccount.getAccountInfo().getUsername()), "accountInfo.username", taskAccount.getAccountInfo().getUsername())
                    .eq(!StringUtils.isEmpty(taskAccount.getPhone()), "phone", taskAccount.getPhone())
                    .eq(taskAccount.getAccountInfo().getType() != null, "accountInfo.type", taskAccount.getAccountInfo().getType())
                    .eq(taskAccount.getBatchNoId() != null, "batchNoId",taskAccount.getBatchNoId())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",taskAccount.getAccountInfo().getUserId())
                    .eq(taskAccount.getState() != null, "state",taskAccount.getState());
        });
        Sort sort = Sort.by("id").descending();
        return taskAccountRepository.findAll(spec,PageRequest.of(page-1,limit));
//        return taskAccountRepository.findAll(Example.of(taskAccount,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(TaskAccount taskAccount,AccountInfo accountInfo) {
        accountInfo.setType(ProjectConfig.AccountType.TASK.getValue());
        if (taskAccountRepository.existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),accountInfo.getUserId())) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        CurTaskState curTaskState = new CurTaskState();
        curTaskState = curTaskStateRepository.save(curTaskState);
        BatchNo batchNo =  batchNoRepository.getOne(taskAccount.getBatchNoId());
        accountInfo.setGameId(batchNo.getGameId());
        taskAccount.setCurTaskStateId(curTaskState.getId());
        accountInfo = accountInfoRepository.save(accountInfo);
        taskAccount.setAccountInfoId(accountInfo.getId());
        taskAccountRepository.save(taskAccount);

    }


    @Override
    @Transactional
    public void update(TaskAccount taskAccount,AccountInfo accountInfo) {
        TaskAccount  oldTaskAccount  = taskAccountRepository.getOne(taskAccount.getId());
        BeanUtils.copyNotNullProperties(taskAccount,oldTaskAccount);
        BeanUtils.copyNotNullProperties(accountInfo,oldTaskAccount.getAccountInfo());
        taskAccountRepository.save(oldTaskAccount);
        accountInfoRepository.save(oldTaskAccount.getAccountInfo());
    }


    @Override
    @Transactional
    public void importAccount(String fileName, Integer type,Integer batchNoId,Integer userId,Integer state) throws Exception{
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(ProjectConfig.FILE_TEMPORARY_DIRECTORY+fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            String[] temp =   str.split("----");
            TaskAccount taskAccount = new TaskAccount();
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUserId(userId);
            taskAccount.setBatchNoId(batchNoId);
            taskAccount.setState(state);
            accountInfo.setType(type);
            for(int i=0;i<temp.length;i++) {
                switch (i) {
                    case 0:
                        accountInfo.setUsername(temp[i]);
                        break;
                    case 1:
                        accountInfo.setPassword(temp[i]);
                        break;
                    case 2:
                        taskAccount.setYyNo(temp[i]);
                        break;
                    case 3:
                        taskAccount.setPhone(temp[i]);
                        break;
                    case 4:
                        taskAccount.setRomNo(temp[i]);
                        break;
                }
            }
            TaskAccount  oldTaskAccount  = taskAccountRepository.getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),userId);
            if (oldTaskAccount!=null) {
                if(oldTaskAccount.getAccountInfo() == null){
                    continue;
                }
                taskAccount.setId(oldTaskAccount.getId());
                BeanUtils.copyNotNullProperties(accountInfo,oldTaskAccount.getAccountInfo());
                BeanUtils.copyNotNullProperties(taskAccount,oldTaskAccount);
                taskAccountRepository.save(oldTaskAccount);
                accountInfoRepository.save(oldTaskAccount.getAccountInfo());
            }else {
                add(taskAccount,accountInfo);
            }

        }

        //close
        inputStream.close();
        bufferedReader.close();
    }

    @Override
    @Transactional
    public void batchChange(List<Integer> ids, Integer batchNoId, Integer state) {
        List<TaskAccount> updates = new ArrayList<>();
        ids.forEach(v->{
            TaskAccount  oldTaskAccount  = taskAccountRepository.getOne(v);
            oldTaskAccount.setState(state);
            oldTaskAccount.setBatchNoId(batchNoId);
            updates.add(oldTaskAccount);
        });
        taskAccountRepository.saveAll(updates);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

