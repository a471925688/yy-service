package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.FollowAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FollowAccountService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, FollowAccount followAccount);

    void add(FollowAccount followAccount,AccountInfo accountInfo);

    void update(FollowAccount followAccount,AccountInfo accountInfo);

    void importAccount(String fileName,Integer userId,Integer state)throws Exception;

    void batchChange(List<Integer> ids,  Integer state);
}
