package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerTypeRepository extends JpaRepository<BannerType,String>{


}
