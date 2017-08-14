package com.javaedge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author javaedge
 * @date 2016/7/24
 */
@Getter
@Setter
public class Comment {
    private int id;
    private int userId;
    private int entityId;
    private int entityType;
    private String content;
    private Date createdDate;
    private int status;
}
