package com.game.xiaoyan.system.repository;

import com.game.xiaoyan.system.entity.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha,String> {
    Captcha findByCaptchaPhoneAndCaptchaData(String captchaPhone, String captchaData);//根據驗證碼獲取信息
}
