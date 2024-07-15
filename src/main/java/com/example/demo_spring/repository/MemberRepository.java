package com.example.demo_spring.repository;

import com.example.demo_spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    // Optional : java 8 에서 들어간, findById 시, null 대신 optional로 감싸서 리턴
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
