package org.redrock.ioc.controller;

import org.redrock.ioc.annotation.Autowried;
import org.redrock.ioc.annotation.Controller;
import org.redrock.ioc.annotation.RequestMapping;
import org.redrock.ioc.annotation.RequestMethod;
import org.redrock.ioc.component.World;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    @Autowried
     World world;

    /*@RequestMapping(value = "/qwe", method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().println(this.world.test());

    }*/
}
