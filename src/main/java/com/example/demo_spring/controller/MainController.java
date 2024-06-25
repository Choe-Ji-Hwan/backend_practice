package com.example.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // GetMapping Annotation: get 요청 flush hello_homepage 할 때, 이 메서드를 호출.
    // 주소 창에, localhost:8080/hello_homepage 로 이용할 수 있음.
    // 함수 명은 상관 없음.
    @GetMapping("hello_page")
    public String hello_practice(Model model) {
        // key 역할을 할 data에 hello 데이터 매핑.
        model.addAttribute("data", "hello222");
        // templates 에서, "[리턴 값].html"을 찾음.
        return "hello";
    }

}
