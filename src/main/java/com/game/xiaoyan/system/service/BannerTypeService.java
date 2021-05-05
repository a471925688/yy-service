package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.BannerType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BannerTypeService {
    Page list(Integer page, Integer limit, BannerType bannerType);

    void add(BannerType bannerType)throws Exception;

    void update(BannerType bannerType)throws Exception;

    List<BannerType> listAll();
}
