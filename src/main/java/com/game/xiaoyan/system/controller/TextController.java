package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.exception.CodeAndMsg;
import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.service.MemberService;
import com.game.xiaoyan.system.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text")
public class TextController {
    @Resource
    private MessageService messageService;
    @Resource
    private MemberService memberService;

    @RequestMapping
    public ModelAndView videoView(Model model) {
        ModelAndView view = new ModelAndView("test/test.html");
        return view;
    }


    @PostMapping
    @RequestMapping("/insertMessage.do")
    public JSONObject insertMessage(Integer num)throws Exception{
        messageService.insert(num);
        return CodeAndMsg.SUCESS.getJSON();
    }

    @PostMapping
    @RequestMapping("/getHomeData.do")
    public JSONObject getHomeData(Integer num)throws Exception{
        JSONObject jsonObject = new JSONObject().fluentPut("all",memberService.countAll())
                .fluentPut("active",memberService.countByActive(DateUtil.getFirstDayOfMonth())).fluentPut("cur",memberService.countBytime(DateUtil.getFirstDayOfMonth()));
        return CodeAndMsg.SUCESS.getJSON(jsonObject);
    }

}
