package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.HappyFightTask;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.HappyFightTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/happyFightTask/happyFightTask")
public class HappyFightTaskController extends BaseController {


    @Resource
    private HappyFightTaskService happyFightTaskService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("happyFightTask:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("happyFightTask/happyFightTask.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("happyFightTask/happyFightTask_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("happyFightTask:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("happyFightTask/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("happyFightTask:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, HappyFightTask happyFightTask) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(happyFightTaskService.list(page, limit, accountInfo,happyFightTask));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加任务
     **/
    @RequiresPermissions("happyFightTask:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(HappyFightTask happyFightTask) throws Exception{
        happyFightTaskService.add(happyFightTask);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑任务
     **/
        @RequiresPermissions("happyFightTask:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(HappyFightTask happyFightTask) throws Exception{
        happyFightTaskService.update(happyFightTask);
        return EDITSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
