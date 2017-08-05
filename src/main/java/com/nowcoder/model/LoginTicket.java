package com.nowcoder.model;

import java.util.Date;

/**
 * @author Shusheng Shi
 * @since 2017/8/4 22:31
 */
public class LoginTicket {
    private int userId;
    private String ticket;
    private Date expired;
    //0表示有效状态,1表示无效
    private int status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
