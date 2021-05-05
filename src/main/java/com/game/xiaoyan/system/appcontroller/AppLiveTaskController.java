package com.game.xiaoyan.system.appcontroller;

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

import static com.game.xiaoyan.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/api/liveTask")
public class AppLiveTaskController extends BaseController {


    @Resource
    private LiveTaskService liveTaskService;
    @Resource
    private BatchNoService batchNoService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 获取直播任务账号信息
     **/
    @ResponseBody
    @RequestMapping("/getLiveTask")
    public JSONObject getLiveTask(Integer state,Long time) throws Exception{
        return ADDSUCESS.getJSON(liveTaskService.getLiveTask(time,state));
    }



    /**
     * 更新直播时长
     **/
    @ResponseBody
    @RequestMapping("/updateLiveTime")
    public JSONObject updateLiveTime(Integer id,Integer time,Integer taskId) throws Exception{
        liveTaskService.updateLiveTime(time,id,taskId);
        return ADDSUCESS.getJSON();
    }



    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
