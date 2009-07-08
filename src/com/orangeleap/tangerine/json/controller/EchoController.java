/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Test controller to ensure JSON goodness is working
 *
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

        if (msg == null || msg.length() == 0) {
            map.put("success", false);
            map.put("error", "no msg parameter specified");
        } else {
            map.put("success", true);
            map.put("response", msg);
        }

        return map;
    }

}
