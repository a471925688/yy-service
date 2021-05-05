package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.BatchNo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BatchNoService {
    Page list(Integer page, Integer limit,  Integer userId);
    List<BatchNo> getAllByUserId(Integer userId);

    void add(BatchNo batchNo)throws Exception;

    void update(BatchNo batchNo)throws Exception;


    void del(Integer batchNoId);
}
