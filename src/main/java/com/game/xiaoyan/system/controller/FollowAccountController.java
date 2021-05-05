package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.FollowAccount;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.FollowAccountService;
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
@RequestMapping("/followAccount/followAccount")
public class FollowAccountController extends BaseController {


    @Resource
    private FollowAccountService followAccountService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("followAccount:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("followAccount/followAccount.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("followAccount/followAccount_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("followAccount:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("followAccount/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("followAccount:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, FollowAccount followAccount) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(followAccountService.list(page, limit, accountInfo,followAccount));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加用户
     **/
    @RequiresPermissions("followAccount:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(AccountInfo accountInfo,FollowAccount followAccount) throws Exception{
        accountInfo.setUserId(getUserId());
        accountInfo.setGameId(1);
        followAccountService.add(followAccount,accountInfo);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
        @RequiresPermissions("followAccount:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(AccountInfo accountInfo,FollowAccount followAccount) throws Exception{
        followAccountService.update(followAccount,accountInfo);
        return EDITSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("followAccount:edit")
    @ResponseBody
    @RequestMapping("/batchChange")
    public JSONObject batchChange(@RequestParam("ids[]") List<Integer> ids,Integer state) throws Exception{
        followAccountService.batchChange(ids,state);
        return EDITSUCESS.getJSON();
    }




    /**
     * 导入账号
     **/
        @RequiresPermissions("followAccount:import")
    @ResponseBody
    @RequestMapping("/import")
    public JSONObject importAccount(@RequestParam("fileName") String fileName,Integer state) throws Exception{
        followAccountService.importAccount(fileName,getUserId(),state);
        return EDITSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
