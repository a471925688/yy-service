package com.game.xiaoyan.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
//任务号
public class BrushGiftsAccount implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键
    @Column(columnDefinition = "INT COMMENT '账号id'",nullable = false)
    private Integer accountInfoId;

    @Column(columnDefinition = "INT NOT NULL COMMENT '状态：不可用，正常'",nullable = false)
    private Integer state;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable=false)
    private Date updateTme;

    //账号信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="accountInfoId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private AccountInfo accountInfo;

}
