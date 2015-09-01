package com.xxx.web;

import com.xxx.rpc.dubbo.IndexDubboService;
import com.xxx.web.base.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/index")
public class IndexAction extends BaseAction {

    @Resource
    private IndexDubboService indexDubboService;


    @RequestMapping("")
    public String initIndexView(HttpServletRequest request, Model model) {
        return "index";
    }

}
