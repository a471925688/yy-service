package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsTask;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.BrushGiftsTaskService;
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
@RequestMapping("/brushGiftsTask/brushGiftsTask")
public class BrushGiftsTaskController extends BaseController {


    @Resource
    private BrushGiftsTaskService brushGiftsTaskService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
        @RequiresPermissions("brushGiftsTask:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsTask/brushGiftsTask.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsTask/brushGiftsTask_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("brushGiftsTask:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsTask/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("brushGiftsTask:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, BrushGiftsTask brushGiftsTask) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(brushGiftsTaskService.list(page, limit, accountInfo,brushGiftsTask));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加任务
     **/
    @RequiresPermissions("brushGiftsTask:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(BrushGiftsTask brushGiftsTask) throws Exception{
        brushGiftsTaskService.add(brushGiftsTask);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑任务
     **/
        @RequiresPermissions("brushGiftsTask:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(BrushGiftsTask brushGiftsTask) throws Exception{
        brushGiftsTaskService.update(brushGiftsTask);
        return EDITSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
