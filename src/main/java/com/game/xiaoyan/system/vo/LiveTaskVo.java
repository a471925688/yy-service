package com.game.xiaoyan.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.game.xiaoyan.system.entity.AccountInfo;
import com.game.xiaoyan.system.entity.BatchNo;
import com.game.xiaoyan.system.entity.CurTaskState;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Data
@Builder
public class LiveTaskVo implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    private Integer id;  // 主键
    private Integer curTaskStateId;
    private Integer state;
    private String romNo;  //
    private String yyNo;  //
    private String username;  // 账号
    private String password;  // 密码
    private Long liveTime;//已直播时长
    private Integer liveTaskId;//直播任务id
}
