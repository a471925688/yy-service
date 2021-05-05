package com.game.xiaoyan.system.service.impl;


import com.game.xiaoyan.common.utils.BeanUtils;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.repository.BatchNoRepository;
import com.game.xiaoyan.system.service.BatchNoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BatchNoServiceImpl implements BatchNoService {

    @Resource
    private BatchNoRepository batchNoRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, Integer userId) {
        return batchNoRepository.getByUserId(userId,PageRequest.of(page-1,limit));
    }

    @Override
    public List<BatchNo> getAllByUserId(Integer userId) {
        return batchNoRepository.findAll();
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(BatchNo batchNo)throws Exception {
        batchNoRepository.save(batchNo);
    }


    @Override
    @Transactional
    public void update(BatchNo batchNo)throws Exception {
        BatchNo oldBatchNo = batchNoRepository.getOne(batchNo.getId());
        BeanUtils.copyNotNullProperties(batchNo,oldBatchNo);
        batchNoRepository.save(oldBatchNo);
    }

    @Override
    @Transactional
    public void del(Integer batchNoId) {
        batchNoRepository.deleteById(batchNoId);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}

