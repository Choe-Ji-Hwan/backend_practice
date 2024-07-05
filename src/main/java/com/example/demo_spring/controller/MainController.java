package com.example.demo_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    // ----------------------------------------------------
    /**
     * MVC와 템플릿 엔진, 동적 전달 방식.
     */
    @GetMapping("hello-mvc")
    // @RequestParam 의 require가 기본적으로 true라서 값을 넘겨야만 에러가 나지 않음.
    // 일반적으로 value 값만 넣어서 사용하는.
    // ex. http://localhost:8080/hello-mvc?name=[name] 처럼. get 방식.
    public String helloMVC(@RequestParam(value = "name", required = true) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // ----------------------------------------------------
    /**
     * API 전달 방식, 가장 기본 예시고 별로 쓸일 없음.
     */
    // ResponseBody: http 통신 프로토콜 body 부분에 직접 넣는 의미, 뷰 없이(html 형식 없이) 값 그대로 들어감.
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring" <- spring 넣었을 때,
    }

    // ----------------------------------------------------
    /**
     * API 전달 방식, 하지만 객체 리턴은 json 형식 반환.
     */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloAPi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName("api");
        return hello;
    }

    // getter, setter <- 자바 빈 표준 방식, 프로퍼티 접근 방식
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    // ----------------------------------------------------
}
