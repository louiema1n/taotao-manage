package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 通用页面跳转
 * @author xtztx
 *
 */
@Controller
@RequestMapping("page")
public class PageController {
    
    @RequestMapping(value = "{pagename}", method = RequestMethod.GET)
    public String toPage(@PathVariable String pagename) {
        return pagename;
    }
}
