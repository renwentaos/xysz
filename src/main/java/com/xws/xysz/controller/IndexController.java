package com.xws.xysz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * JokerYG
 * Date: 2019-01-19
 * Time: 11:59
 */
@Controller
public class IndexController {
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about(){
        return "about";
    }

    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public String service(){
        return "service";
    }
}
