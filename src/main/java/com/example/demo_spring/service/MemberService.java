package com.example.demo_spring.service;

import com.example.demo_spring.domain.Member;
import com.example.demo_spring.repository.MemberRepository;
import com.example.demo_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

/**
 * 멤버 관련 서비스 (멤버 함수는 비즈니스 용어로 사용 하는 것이 쉬움)
 */

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        // 같은 이름의 중복 회원은 안된다.
        validateDuplicateMember(member);    // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 있는 회원");
                        });
    }

    /**
     * 전체 회원 조회
     */
    private List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 멤버 id로 멤버 반환.
     */
    private Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
