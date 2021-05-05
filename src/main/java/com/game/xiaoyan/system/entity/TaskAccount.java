package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
//任务号
public class TaskAccount implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "INT COMMENT '账号id'",nullable = false)
    private Integer accountInfoId;
    @Column(columnDefinition = "INT NOT NULL  COMMENT '批次id'",nullable = false)
    private Integer batchNoId;
    @Column(columnDefinition = "INT NOT NULL  COMMENT '今日任务id'",nullable = false)
    private Integer curTaskStateId;
    @Column(columnDefinition = "INT NOT NULL COMMENT '状态：不可用，待认证，未开通直播,未获低保，低保中，已过低保'",nullable = false)
    private Integer state;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '房间号'")
    private String romNo;  //
    @Column(columnDefinition = "VARCHAR(100) COMMENT 'yy号'")
    private String yyNo;  //
    @Column(columnDefinition = "VARCHAR(100) COMMENT '绑定的电话号码'")
    private String phone;  //
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable=false)
    private Date updateTme;


    //账号信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="accountInfoId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private AccountInfo accountInfo;

    //批次信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="batchNoId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private BatchNo batchNo;

    //当前任务信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="curTaskStateId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private CurTaskState curTaskState;


}
