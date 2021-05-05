package com.game.xiaoyan.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Machine implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '编号'",nullable = false)
    private String no;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '远程链接账号'")
    private String linkName;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '远程链接验证码'")
    private String linkPassword;
    @Column(columnDefinition = "INT NOT NULL  COMMENT '用户id'",nullable = false)
    private Integer userId;
    @Column(columnDefinition = "VARCHAR(1000) NOT NULL COMMENT '备注'")
    private String remarks;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable=false)
    private Date updateTme;
}
