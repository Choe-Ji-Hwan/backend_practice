package com.example.demo_spring.service;

import com.example.demo_spring.domain.Member;
import com.example.demo_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        // di를 이용하여 의존성이 없게해서, afterEach때, repo를 직접 건들 수 있도록 함.
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        memberService.findOne(saveId)
                .ifPresentOrElse(
                        m -> assertThat(m.getName()).isEqualTo(member.getName()),
                        () -> fail("empty optional")
                );
    }

    @Test
    public void 중복_회원_예약() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 있는 회원");

        // try catch 방법.
        /* try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 있는 회원");
        }*/
    }

    @Test
    void findMembers() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        // when
        List<Member> memberList = memberService.findMembers();

        // then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        // given
        Member member = new Member();
        member.setName("spring");
        Long id = memberService.join(member);

        memberService.findOne(id)
                .ifPresentOrElse(m -> assertThat(m.getName()).isEqualTo(member.getName()),
                        () -> fail("can not find member")
                );
    }

}