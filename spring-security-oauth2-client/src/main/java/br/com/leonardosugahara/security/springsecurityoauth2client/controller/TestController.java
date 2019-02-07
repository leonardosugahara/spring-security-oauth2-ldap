package br.com.leonardosugahara.security.springsecurityoauth2client.controller;

import br.com.leonardosugahara.security.springsecurityoauth2client.document.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class TestController {
    public TestController() {
        super();
    }


    @PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_DEVELOPERS')")
    @GetMapping(value = "/test/{id}")
    @ResponseBody
    public Test test(@PathVariable final long id) {
        return new Test(id, "Test Spring Security Oauth2 Successful");
    }

}
