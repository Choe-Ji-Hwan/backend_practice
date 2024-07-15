package com.example.demo_spring;

import com.example.demo_spring.repository.MemberRepository;
import com.example.demo_spring.repository.MemoryMemberRepository;
import com.example.demo_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
