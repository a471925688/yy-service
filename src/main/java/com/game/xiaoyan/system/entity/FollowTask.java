package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
//直播任务
public class FollowTask implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "INT COMMENT '账号id'",nullable = false)
    private Integer accountInfoId;
    @Column(columnDefinition = "INT COMMENT '挂播id'",nullable = false)
    private Integer liveTaskId;
    @Column(columnDefinition = "VARCHAR(250) COMMENT '关注的房间号'")
    private String romNo;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '状态：进行中,成功，失败'",insertable = false)
    private Integer state;
    @Column(columnDefinition = "VARCHAR(250) COMMENT '匹配YY号'")
    private String remarks;
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
