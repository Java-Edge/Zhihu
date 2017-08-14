package com.javaedge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author javaedge
 * @date 2016/7/3
 */
@Getter
@Setter
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    // 0有效，1无效
    private int status;
    private String ticket;
}
