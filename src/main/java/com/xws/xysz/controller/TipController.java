package com.xws.xysz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TipController {
    @RequestMapping("/tips")
    public String tip(@ModelAttribute("msg") String msg, @ModelAttribute("url") String url, Model model){
        model.addAttribute("msg",msg);
        model.addAttribute("url",url);
        return "tips/tips";
    }
}
