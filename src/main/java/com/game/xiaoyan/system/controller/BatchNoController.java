package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.service.BatchNoService;
import com.game.xiaoyan.system.service.GameService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;


/**
 * 批次管理
 */
@Controller
@RequestMapping("/batchNo/batchNo")
public class BatchNoController extends BaseController {
    @Autowired
    private BatchNoService batchNoService;
    @Autowired
    private GameService gameService;


    @RequiresPermissions("batchNo:view")
    @RequestMapping
    public String batchNo(Model model) {
        return "batchNo/batchNo.html";
    }

    @RequestMapping("/editForm")
    public String addBatchNo(Model model) {
        model.addAttribute("games",gameService.getAllGame());
        return "batchNo/batchNo_form.html";
    }

    /**
     * 查询批次列表
     */
    @RequiresPermissions("batchNo:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit) {
        return SUCESS.pageData(batchNoService.list(page, limit, getUserId()));
    }

    /**
     * 添加批次
     **/
    @RequiresPermissions("batchNo:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(BatchNo batchNo) throws Exception{
        batchNo.setUserId(getUserId());
        batchNoService.add(batchNo);
        return ADDSUCESS.getJSON();
    }

    /**
     * 修改批次
     **/
    @RequiresPermissions("batchNo:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(BatchNo batchNo)throws Exception {
        batchNoService.update(batchNo);
        return SUCESS.getJSON();
    }

    /**
     * 修改批次
     **/
    @RequiresPermissions("batchNo:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(Integer id)throws Exception {
        batchNoService.del(id);
        return DELSUCESS.getJSON();
    }



}
