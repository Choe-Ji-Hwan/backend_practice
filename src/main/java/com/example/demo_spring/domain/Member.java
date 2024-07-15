package com.example.demo_spring.domain;

public class Member {

    // 요구 사항: 도메인 식발자 id, 이름만 존재
    private Long id;
    private String name;

    // getter, setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
