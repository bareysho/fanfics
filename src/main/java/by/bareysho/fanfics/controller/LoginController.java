package by.bareysho.fanfics.controller;

import by.bareysho.fanfics.security.ulogin.UloginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UloginAuthenticationFilter uloginAuthProvider;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/ulogin", method = RequestMethod.POST)
    public String checkAuthorization(WebRequest request) {
        uloginAuthProvider.attemptAuthentication(request);

        return "redirect:";
    }
}
