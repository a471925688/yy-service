package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.LiveTask;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.LiveTaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/liveTask/liveTask")
public class LiveTaskController extends BaseController {


    @Resource
    private LiveTaskService liveTaskService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("liveTask:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("liveTask/liveTask.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("liveTask/liveTask_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("liveTask:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("liveTask/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("liveTask:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, LiveTask liveTask) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(liveTaskService.list(page, limit, accountInfo,liveTask));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加任务
     **/
    @RequiresPermissions("liveTask:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(LiveTask liveTask) throws Exception{
        liveTaskService.add(liveTask);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑任务
     **/
        @RequiresPermissions("liveTask:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(LiveTask liveTask) throws Exception{
        liveTaskService.update(liveTask);
        return EDITSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
