package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
//直播任务
public class HappyFightTask implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "INT COMMENT '账号id'",nullable = false)
    private Integer accountInfoId;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '状态：待匹配,进行中,已完成，已失败'",insertable = false)
    private Integer state;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '是否已完成刷礼物'",insertable = false)
    private Integer brushGiftsed;
    @Column(columnDefinition = "INT  COMMENT '是否发起方'",insertable = false)
    private boolean iSActive;//是否发起方
    @Column(columnDefinition = "VARCHAR(10) COMMENT '匹配YY号'")
    private String targetYyno;
    @Column(columnDefinition = "VARCHAR(250) COMMENT '备注'")
    private String remarks;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '是否在执行刷礼物任务'",insertable = false)
    private Integer brushGiftsing;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable=false)
    private Date updateTme;

    //账号信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="accountInfoId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private AccountInfo accountInfo;


    @Transient
    private String searchTime;

}
