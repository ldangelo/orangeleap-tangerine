package com.mpower.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Test controller to ensure JSON goodness is working
 * @version 1.0
 */
@Controller
public class EchoController {

    @RequestMapping("/echo.json")
    public ModelMap echo(String msg) {
        /* The ModelMap will be serialized by the JsonView
           automagically and returned as a JSON string ready
           for consumption by your JSON library of choice.
         */
        ModelMap map = new ModelMap();

        if(msg == null || msg.length() == 0) {
            map.put("success", false);
            map.put("error", "no msg parameter specified");
        } else {
            map.put("success", true);
            map.put("response", msg);
        }

        return map;
    }

}
