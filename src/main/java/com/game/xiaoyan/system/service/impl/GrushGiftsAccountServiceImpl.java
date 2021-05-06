package com.game.xiaoyan.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.game.xiaoyan.common.ProjectConfig;
import com.game.xiaoyan.common.exception.CodeAndMsg;
import com.game.xiaoyan.common.exception.CustormException;
import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsAccount;
import com.game.xiaoyan.system.repository.AccountInfoRepository;
import com.game.xiaoyan.system.repository.BatchNoRepository;
import com.game.xiaoyan.system.repository.CurTaskStateRepository;
import com.game.xiaoyan.system.repository.BrushGiftsAccountRepository;
import com.game.xiaoyan.system.service.BrushGiftsAccountService;
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
public class GrushGiftsAccountServiceImpl implements BrushGiftsAccountService {

    @Resource
    private BrushGiftsAccountRepository brushGiftsAccountRepository;
    @Resource
    private AccountInfoRepository accountInfoRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page<BrushGiftsAccount> list(Integer page, Integer limit, AccountInfo accountInfo, BrushGiftsAccount brushGiftsAccount) {
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        brushGiftsAccount.setAccountInfo(accountInfo);
        Specification<BrushGiftsAccount> spec = Specifications.where(e -> {
            e.eq(!StringUtils.isEmpty(brushGiftsAccount.getAccountInfo().getUsername()), "accountInfo.username", brushGiftsAccount.getAccountInfo().getUsername())
                    .eq(brushGiftsAccount.getAccountInfo().getType() != null, "accountInfo.type", brushGiftsAccount.getAccountInfo().getType())
                    .eq(accountInfo.getUserId()!=null,"accountInfo.userId",brushGiftsAccount.getAccountInfo().getUserId())
                    .eq(brushGiftsAccount.getState() != null, "state",brushGiftsAccount.getState());
        });
        Sort sort = Sort.by("state").descending();
        return brushGiftsAccountRepository.findAll(spec,PageRequest.of(page-1,limit,sort));
//        return brushGiftsAccountRepository.findAll(Example.of(brushGiftsAccount,matcher), PageRequest.of(page-1,limit));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(BrushGiftsAccount brushGiftsAccount,AccountInfo accountInfo) {
        if (brushGiftsAccountRepository.existsByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),accountInfo.getUserId())) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        accountInfo.setType(ProjectConfig.AccountType.FOLLOW.getValue());
        accountInfo = accountInfoRepository.save(accountInfo);
        brushGiftsAccount.setAccountInfoId(accountInfo.getId());
        brushGiftsAccountRepository.save(brushGiftsAccount);

    }


    @Override
    @Transactional
    public void update(BrushGiftsAccount brushGiftsAccount,AccountInfo accountInfo) {
        accountInfo.setId(null);
        BrushGiftsAccount  oldBrushGiftsAccount  = brushGiftsAccountRepository.getOne(brushGiftsAccount.getId());
        BeanUtils.copyNotNullProperties(brushGiftsAccount,oldBrushGiftsAccount);
        BeanUtils.copyNotNullProperties(accountInfo,oldBrushGiftsAccount.getAccountInfo());
        brushGiftsAccountRepository.save(oldBrushGiftsAccount);
        accountInfoRepository.save(oldBrushGiftsAccount.getAccountInfo());
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
            BrushGiftsAccount brushGiftsAccount = new BrushGiftsAccount();
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setUserId(userId);
            brushGiftsAccount.setState(state);
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
            BrushGiftsAccount  oldBrushGiftsAccount  = brushGiftsAccountRepository.getByAccountInfoUsernameAndAccountInfoTypeAndAccountInfoUserId(accountInfo.getUsername(),accountInfo.getType(),userId);
            if (oldBrushGiftsAccount!=null) {
                if(oldBrushGiftsAccount.getAccountInfo() == null){
                    continue;
                }
                brushGiftsAccount.setId(oldBrushGiftsAccount.getId());
                BeanUtils.copyNotNullProperties(accountInfo,oldBrushGiftsAccount.getAccountInfo());
                BeanUtils.copyNotNullProperties(brushGiftsAccount,oldBrushGiftsAccount);
                brushGiftsAccountRepository.save(oldBrushGiftsAccount);
                accountInfoRepository.save(oldBrushGiftsAccount.getAccountInfo());
            }else {
                add(brushGiftsAccount,accountInfo);
            }

        }

        //close
        inputStream.close();
        bufferedReader.close();
    }

    @Override
    @Transactional
    public void batchChange(List<Integer> ids, Integer state) {
        List<BrushGiftsAccount> updates = new ArrayList<>();
        ids.forEach(v->{
            BrushGiftsAccount  oldBrushGiftsAccount  = brushGiftsAccountRepository.getOne(v);
            oldBrushGiftsAccount.setState(state);
            updates.add(oldBrushGiftsAccount);
        });
        brushGiftsAccountRepository.saveAll(updates);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

