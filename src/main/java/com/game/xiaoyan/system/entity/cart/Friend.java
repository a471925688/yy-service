package com.game.xiaoyan.system.entity.cart;


import com.game.xiaoyan.system.entity.User;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

//@Entity
public class Friend {
    /**
     * 自增主键
     */
    @Id
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 好友id
     */
    private Integer friendId;

    /**
     * 建立时间
     */
    private String buildTime;

    /**
     * 好友分组id
     */
    private Integer typeId;

    @Transient
    private List<User> users;

    /**
     * 好友用户
     */
    @Transient
    private User friendInfo;


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getFriendInfo() {
        return friendInfo;
    }

    public void setFriendInfo(User friendInfo) {
        this.friendInfo = friendInfo;
    }

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取好友id
     *
     * @return friend_id - 好友id
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * 设置好友id
     *
     * @param friendId 好友id
     */
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    /**
     * 获取建立时间
     *
     * @return build_time - 建立时间
     */
    public String getBuildTime() {
        return buildTime;
    }

    /**
     * 设置建立时间
     *
     * @param buildTime 建立时间
     */
    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    /**
     * 获取好友分组id
     *
     * @return type_id - 好友分组id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置好友分组id
     *
     * @param typeId 好友分组id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
