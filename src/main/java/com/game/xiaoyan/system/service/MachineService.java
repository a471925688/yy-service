package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.Machine;
import org.springframework.data.domain.Page;

public interface MachineService {
    Page list(Integer page, Integer limit,  Integer userId);

    void add(Machine machine)throws Exception;

    void update(Machine machine)throws Exception;


    void del(Integer machineId);
}
