package com.game.xiaoyan.system.controller;

import com.game.xiaoyan.common.utils.DateUtil;
import com.game.xiaoyan.system.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller()
@RequestMapping("/home")
public class HomeController {


    @Resource
    private MemberService memberService;


    /**
     * 控制台
     */
    @RequestMapping("/console")
    public String console(Model model)throws Exception {
        Integer activeUser = memberService.countByActive(DateUtil.getFirstDayOfMonth());
        Integer allUser = memberService.countAll();
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("newUser",memberService.countBytime(DateUtil.getFirstDayOfMonth()));
        model.addAttribute("allUser",allUser);
        model.addAttribute("activeUserAatio",activeUser/allUser);
        return "home/console.html";
    }

    /**
     * 消息弹窗
     */
    @RequestMapping("/message")
    public String message() {
        return "tpl/message.html";
    }

    /**
     * 修改密码弹窗
     */
    @RequestMapping("/password")
    public String password() {
        return "tpl/password.html";
    }

    /**
     * 主题设置弹窗
     */
    @RequestMapping("/theme")
    public String theme() {
        return "tpl/theme.html";
    }

//    @ResponseBody
//    @RequestMapping("/consoleData.do")
//    public JSONObject getHomeData()throws Exception{
//        return CodeAndMsg.SUCESS.getJSON().fluentPut("mouth",orderService.countByMonth(DateUtil.getCurrentYear()))
//                .fluentPut("station",orderService.countGroupByPickUpStation(DateUtil.getCurrentYear()));
//    }

    /**
     * 设置主题
     */
    @RequestMapping("/setTheme")
    public String setTheme(String themeName, HttpServletRequest request) {
        if (null == themeName) {
            request.getSession().removeAttribute("theme");
        } else {
            request.getSession().setAttribute("theme", themeName);
        }
        return "redirect:/";
    }
}
