package com.example.demo_spring.repository;

import com.example.demo_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring-Data JPA 사용 하기 위해, JpaRepository 를 상속 받아야 한다. <엔티티 클래스, id 형식>
// 기본적인 CRUD 제공, 페이징 기능 제공.
// 사용자가 직접 구현체를 넣어주는 게 아니라, SpringData JPA가 알아서 구현체를 만들어 넣어준다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL을 select m from Member m where m.name = ?  <- 이런 형태로 만들어 준다. 포맷을 맞춰서 이름을 지으면 됨.
    @Override
    Optional<Member> findByName(String name);
}
