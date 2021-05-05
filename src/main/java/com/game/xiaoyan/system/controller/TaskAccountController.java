package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.entity.TaskAccount;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.TaskAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.List;

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/taskAccount/taskAccount")
public class TaskAccountController extends BaseController {


    @Resource
    private TaskAccountService taskAccountService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("taskAccount:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("taskAccount/taskAccount.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("taskAccount/taskAccount_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("taskAccount:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("taskAccount/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("taskAccount:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, TaskAccount taskAccount) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(taskAccountService.list(page, limit, accountInfo,taskAccount));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加用户
     **/
    @RequiresPermissions("taskAccount:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(AccountInfo accountInfo,TaskAccount taskAccount) throws Exception{
        accountInfo.setUserId(getUserId());
        taskAccountService.add(taskAccount,accountInfo);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("taskAccount:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(AccountInfo accountInfo,TaskAccount taskAccount) throws Exception{
        taskAccountService.update(taskAccount,accountInfo);
        return EDITSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("taskAccount:edit")
    @ResponseBody
    @RequestMapping("/batchChange")
    public JSONObject batchChange(@RequestParam("ids[]") List<Integer> ids,Integer batchNoId,Integer state) throws Exception{
        taskAccountService.batchChange(ids,batchNoId,state);
        return EDITSUCESS.getJSON();
    }




    /**
     * 导入账号
     **/
    @RequiresPermissions("taskAccount:import")
    @ResponseBody
    @RequestMapping("/import")
    public JSONObject importAccount(@RequestParam("fileName") String fileName,@RequestParam("type") @Max(3) @Min(1) Integer type,Integer batchNoId,Integer state) throws Exception{
        taskAccountService.importAccount(fileName,type,batchNoId,getUserId(),state);
        return EDITSUCESS.getJSON();
    }



    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
