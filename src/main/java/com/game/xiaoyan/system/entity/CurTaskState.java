package com.game.xiaoyan.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class CurTaskState implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "BIGINT DEFAULT 0 NOT NULL COMMENT '当天已直播时间'",insertable = false)
    private Long liveTime;
    @Column(columnDefinition = "INT  DEFAULT 0  COMMENT '当天关注次数'",insertable = false)
    private Integer followNum;
    @Column(columnDefinition = "INT  DEFAULT 0  COMMENT '当天斗播次数'",insertable = false)
    private Integer happyFight;
    @Column(columnDefinition = "INT  DEFAULT 0 COMMENT '状态:空闲，任务中'",insertable = false)
    private Integer taskState;
    @Column(columnDefinition = "INT  DEFAULT 0 COMMENT '当前任务:空闲中,直播，斗播，点关注，刷礼物'",insertable = false)
    private Integer curTaskType;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable=false)
    private Date updateTme;

}
