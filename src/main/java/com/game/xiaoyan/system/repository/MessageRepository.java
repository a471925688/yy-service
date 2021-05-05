package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {
    List<Message> findAllByUserIdAndIsRead(String userId,Boolean isRead, Pageable pageable);
    List<Message> findAllByUserId(String userId,Pageable pageable);

    @Modifying
    @Query("update Message m set m.isRead = true where m.messageId = ?1 and m.userId = ?2")
    void readMessage(String messageId,String userId);
}
