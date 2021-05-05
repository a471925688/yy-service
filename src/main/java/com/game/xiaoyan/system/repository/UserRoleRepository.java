package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    List<UserRole> findAllByUserIdIn(List<Integer> userIds);

    void deleteAllByUserId(Integer userId);


//    List<UserRole> selectByUserIds(@Param("userIds") List<Integer> userIds);
//
//    int insertBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);
}
