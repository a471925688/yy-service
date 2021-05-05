package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Entity
@Data
public class  AccountInfo implements Serializable {

    private static final long serialVersionUID = -393463908673953530L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键id
    @Column(columnDefinition = "INT NOT NULL  COMMENT '用户id'",nullable = false)
    private Integer userId;
    @Column(columnDefinition = "INT NOT NULL  COMMENT '游戏id'",nullable = false)
    private Integer gameId;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '账号'")
    private String username;  // 账号
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '密码'")
    private String password;  // 密码
    @Column(columnDefinition = "INT NOT NULL  COMMENT '任务，点关注，刷礼物'",nullable = false)
    private Integer type;  // 用户类型
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;  // 注册时间
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date updateTime;  // 修改时间


}
