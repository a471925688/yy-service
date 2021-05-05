package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.Machine;
import com.game.xiaoyan.system.service.MachineService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;


/**
 * 机器管理
 */
@Controller
@RequestMapping("/machine/machine")
public class MachineController extends BaseController {
    @Autowired
    private MachineService machineService;


    @RequiresPermissions("machine:view")
    @RequestMapping
    public String machine(Model model) {
        return "machine/machine.html";
    }

    @RequestMapping("/editForm")
    public String addMachine(Model model) {
        return "machine/machine_form.html";
    }

    /**
     * 查询机器列表
     */
    @RequiresPermissions("machine:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit) {
        return SUCESS.pageData(machineService.list(page, limit, getUserId()));
    }

    /**
     * 添加机器
     **/
    @RequiresPermissions("machine:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(Machine machine) throws Exception{
        machine.setUserId(getUserId());
        machineService.add(machine);
        return ADDSUCESS.getJSON();
    }

    /**
     * 修改机器
     **/
    @RequiresPermissions("machine:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(Machine machine)throws Exception {
        machineService.update(machine);
        return SUCESS.getJSON();
    }

    /**
     * 修改机器
     **/
    @RequiresPermissions("machine:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(Integer id)throws Exception {
        machineService.del(id);
        return DELSUCESS.getJSON();
    }



}
