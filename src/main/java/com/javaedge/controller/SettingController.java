package com.javaedge.controller;

import com.javaedge.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by javaedge on 2016/7/10.
 */
@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession) {
        return "Setting OK. " + wendaService.getMessage(1);
    }
}
