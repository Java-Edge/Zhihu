package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Shusheng Shi
 * @since 2017/8/4 20:54
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param model
     * @param username
     * @param password
     * @param next
     * @param rememberme
     * @param response
     * @return
     */
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("next") String next,
                      @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                //包含ticket,则再将其下发到浏览器客户端
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    //有要跳转的地址next,则实现未跳转登录
                    return "redirect:" + next;
                }
                return "redirect:/";
            }else {
                //将出现的问题返回给前端
                model.addAttribute("msg", map.get("msg"));
                //回到注册页面,重新再注册
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            //出现异常时,重新回到注册页面
            return "login";
        }

    }

    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String regloginPage(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    /**
     * 登录
     *
     * @param model      模型
     * @param username   用户名
     * @param password   密码
     * @param next       跳转的页面URI
     * @param rememberme 记住密码
     * @return
     */
    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String loign(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                //包含ticket,则再将其下发到浏览器客户端
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) {
                    //有要跳转的地址next,则实现未跳转登录
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                //将出现的问题返回给前端
                model.addAttribute("msg", map.get("msg"));
                //回到注册页面,重新再注册
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            //出现异常时,重新回到注册页面
            return "login";
        }

    }

    /**
     * 退出
     *
     * @param ticket
     * @return 重定向到首页
     */
    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}
