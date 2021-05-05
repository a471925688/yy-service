package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,String>{

    List<Banner> findAllByTypeId(String typeId);
}
