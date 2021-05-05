package com.game.xiaoyan.system.service;

import com.game.xiaoyan.system.entity.Banner;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BannerService {
    Page list(Integer page, Integer limit, Banner banner);

    void add(Banner banner)throws Exception;

    void update(Banner banner)throws Exception;

    List<Banner> getImageBanner();

    List<Banner> getInfoBanner();

    void del(String bannerId);
}
