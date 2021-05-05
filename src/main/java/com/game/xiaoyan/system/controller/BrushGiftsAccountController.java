package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BrushGiftsAccount;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.BrushGiftsAccountService;
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
@RequestMapping("/brushGiftsAccount/brushGiftsAccount")
public class BrushGiftsAccountController extends BaseController {


    @Resource
    private BrushGiftsAccountService brushGiftsAccountService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("brushGiftsAccount:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsAccount/brushGiftsAccount.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsAccount/brushGiftsAccount_form.html");
    }

    @RequestMapping("/importView")
    @RequiresPermissions("brushGiftsAccount:import")
    public ModelAndView importView(Model model) {
        model.addAttribute("batchNos",batchNoService.getAllByUserId(getUserId()));
        return new ModelAndView("brushGiftsAccount/account_select_bacthNo.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("brushGiftsAccount:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, AccountInfo accountInfo, BrushGiftsAccount brushGiftsAccount) {
        accountInfo.setUserId(getUserId());
        return SUCESS.pageData(brushGiftsAccountService.list(page, limit, accountInfo,brushGiftsAccount));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加用户
     **/
    @RequiresPermissions("brushGiftsAccount:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(AccountInfo accountInfo,BrushGiftsAccount brushGiftsAccount) throws Exception{
        accountInfo.setUserId(getUserId());
        accountInfo.setGameId(1);
        brushGiftsAccountService.add(brushGiftsAccount,accountInfo);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
        @RequiresPermissions("brushGiftsAccount:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(AccountInfo accountInfo,BrushGiftsAccount brushGiftsAccount) throws Exception{
        brushGiftsAccountService.update(brushGiftsAccount,accountInfo);
        return EDITSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("brushGiftsAccount:edit")
    @ResponseBody
    @RequestMapping("/batchChange")
    public JSONObject batchChange(@RequestParam("ids[]") List<Integer> ids,Integer state) throws Exception{
        brushGiftsAccountService.batchChange(ids,state);
        return EDITSUCESS.getJSON();
    }




    /**
     * 导入账号
     **/
        @RequiresPermissions("brushGiftsAccount:import")
    @ResponseBody
    @RequestMapping("/import")
    public JSONObject importAccount(@RequestParam("fileName") String fileName,Integer state) throws Exception{
        brushGiftsAccountService.importAccount(fileName,getUserId(),state);
        return EDITSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
