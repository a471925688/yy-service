package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.TaskAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskAccountService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, TaskAccount taskAccount);

    void add(TaskAccount taskAccount,AccountInfo accountInfo);

    void update(TaskAccount taskAccount,AccountInfo accountInfo);

    void importAccount(String fileName,Integer type,Integer batchNoId,Integer userId,Integer state)throws Exception;

    void batchChange(List<Integer> ids, Integer batchNoId, Integer state);
}
