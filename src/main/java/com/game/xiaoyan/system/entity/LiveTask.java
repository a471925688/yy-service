package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//直播任务
public class LiveTask implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "INT COMMENT '账号id'",nullable = false)
    private Integer accountInfoId;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '状态：任务中，已完成，已暂停，已过期'",insertable = false)
    private Integer state;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '已直播时长(单位:分钟)'",insertable  = false)
    private Integer time;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '关注次数'",insertable = false)
    private Integer followNum;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '是否在执行关注任务'",insertable = false)
    private Integer following;
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
