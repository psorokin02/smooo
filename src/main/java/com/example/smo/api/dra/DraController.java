package com.example.smo.api.dra;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
@CrossOrigin
public class DraController {

    @PostMapping("/dra")
    public void draConfig() {
        System.out.println("notified dra config");
//        throw new RuntimeException();
    }

    @PostMapping("/route")
    public void routeConfig() {
        System.out.println("notified route config");
//        throw new RuntimeException();
    }

}
