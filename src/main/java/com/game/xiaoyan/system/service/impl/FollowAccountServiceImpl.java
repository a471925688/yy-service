package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.exception.CodeAndMsg;
import com.game.xiaoyan.common.exception.CustormException;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.entity.CurTaskState;
import com.game.xiaoyan.system.entity.FollowAccount;
import com.game.xiaoyan.system.repository.AccountInfoRepository;
import com.game.xiaoyan.system.repository.BatchNoRepository;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.FollowAccountRepository;
import com.game.xiaoyan.system.service.FollowAccountService;
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
import java.util.List;

@Service
public class FollowAccountServiceImpl implements FollowAccountService {

    @Resource
    private FollowAccountRepository followAccountRepository;
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Resource
    private CurTaskStateRepository curTaskStateRepository;
    @Resource
    private BatchNoRepository batchNoRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<FollowAccount> list(Integer page, Integer limit, AccountInfo accountInfo, FollowAccount followAccount) {
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        followAccount.setAccountInfo(accountInfo);
        Specification<FollowAccount> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(followAccount.getAccountInfo().getUsername()), "accountInfo.username", followAccount.getAccountInfo().getUsername())
                    .eq(followAccount.getAccountInfo().getType() != null, "accountInfo.type", followAccount.getAccountInfo().getType())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",followAccount.getAccountInfo().getUserId())
                    .eq(followAccount.getState() != null, "state",followAccount.getState());
        });
        Sort sort = Sort.by("state").descending();
        return followAccountRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return followAccountRepository.findAll(Example.of(followAccount,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(FollowAccount followAccount,AccountInfo accountInfo) {
        if (followAccountRepository.existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),accountInfo.getUserId())) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        accountInfo = accountInfoRepository.save(accountInfo);
        followAccount.setAccountInfoId(accountInfo.getId());
        followAccountRepository.save(followAccount);

    }


    @Override
    @Transactional
    public void update(FollowAccount followAccount,AccountInfo accountInfo) {
        accountInfo.setId(null);
        FollowAccount  oldFollowAccount  = followAccountRepository.getOne(followAccount.getId());
        BeanUtils.copyNotNullProperties(followAccount,oldFollowAccount);
        BeanUtils.copyNotNullProperties(accountInfo,oldFollowAccount.getAccountInfo());
        followAccountRepository.save(oldFollowAccount);
        accountInfoRepository.save(oldFollowAccount.getAccountInfo());
    }


    @Override
    @Transactional
    public void importAccount(String fileName,Integer userId,Integer state) throws Exception{
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(ProjectConfig.FILE_TEMPORARY_DIRECTORY+fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            String[] temp =   str.split("----");
            FollowAccount followAccount = new FollowAccount();
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUserId(userId);
            followAccount.setState(state);
            accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
            accountInfo.setGameId(1);
            for(int i=0;i<temp.length;i++) {
                switch (i) {
                    case 0:
                        accountInfo.setUsername(temp[i]);
                        break;
                    case 1:
                        accountInfo.setPassword(temp[i]);
                        break;
                }
            }
            FollowAccount  oldFollowAccount  = followAccountRepository.getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),userId);
            if (oldFollowAccount!=null) {
                if(oldFollowAccount.getAccountInfo() == null){
                    continue;
                }
                followAccount.setId(oldFollowAccount.getId());
                BeanUtils.copyNotNullProperties(accountInfo,oldFollowAccount.getAccountInfo());
                BeanUtils.copyNotNullProperties(followAccount,oldFollowAccount);
                followAccountRepository.save(oldFollowAccount);
                accountInfoRepository.save(oldFollowAccount.getAccountInfo());
            }else {
                add(followAccount,accountInfo);
            }

        }

        //close
        inputStream.close();
        bufferedReader.close();
    }

    @Override
    @Transactional
    public void batchChange(List<Integer> ids, Integer state) {
        List<FollowAccount> updates = new ArrayList<>();
        ids.forEach(v->{
            FollowAccount  oldFollowAccount  = followAccountRepository.getOne(v);
            oldFollowAccount.setState(state);
            updates.add(oldFollowAccount);
        });
        followAccountRepository.saveAll(updates);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

