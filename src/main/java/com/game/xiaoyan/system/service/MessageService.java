package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<Message> list(Boolean isRead, Pageable pageable);

    void read(String messageId);

    void insert(Integer num)throws Exception;
}
