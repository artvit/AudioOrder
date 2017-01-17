package by.bsu.audioorder.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    public String getUser(@PathVariable String name) {
        String result = "Hello " + name;
        return result;
    }
}
