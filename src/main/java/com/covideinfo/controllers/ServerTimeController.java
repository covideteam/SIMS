package com.covideinfo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/timeCtrl")
public class ServerTimeController {

	@RequestMapping(value="/serverTime", method=RequestMethod.GET)
    public @ResponseBody String getServerTime() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf2.format(new Date());
    }

}
