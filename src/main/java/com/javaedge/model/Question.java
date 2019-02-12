package com.javaedge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author javaedge
 * @date 2016/7/15
 */
@Getter
@Setter
public class Question {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    private int userId;
    private int commentCount;
}
