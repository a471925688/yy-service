package com.game.xiaoyan.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.game.xiaoyan.common.BaseController;
import com.game.xiaoyan.system.entity.Game;
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
 * 廣告管理
 */
@Controller
@RequestMapping("/game/game")
public class GameController extends BaseController {
    @Autowired
    private GameService gameService;


    @RequiresPermissions("game:view")
    @RequestMapping
    public String game(Model model) {
        return "game/game.html";
    }

    @RequestMapping("/editForm")
    public String addGame(Model model) {
        return "game/game_form.html";
    }

    /**
     * 查询廣告列表
     */
    @RequiresPermissions("game:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit) {
        return SUCESS.pageData(gameService.list(page, limit, getUserId()));
    }

    /**
     * 添加廣告
     **/
    @RequiresPermissions("game:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(Game game) throws Exception{
        game.setUserId(getUserId());
        gameService.add(game);
        return ADDSUCESS.getJSON();
    }

    /**
     * 修改廣告
     **/
    @RequiresPermissions("game:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(Game game)throws Exception {
        gameService.update(game);
        return SUCESS.getJSON();
    }

    /**
     * 修改廣告
     **/
    @RequiresPermissions("game:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(Integer id)throws Exception {
        gameService.del(id);
        return DELSUCESS.getJSON();
    }



}
