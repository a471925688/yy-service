package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrushGiftsAccountService {
    Page list(Integer page, Integer limit, AccountInfo accountInfo, BrushGiftsAccount brushGiftsAccount);

    void add(BrushGiftsAccount brushGiftsAccount,AccountInfo accountInfo);

    void update(BrushGiftsAccount brushGiftsAccount,AccountInfo accountInfo);

    void importAccount(String fileName,Integer userId,Integer state)throws Exception;

    void batchChange(List<Integer> ids,  Integer state);
}
