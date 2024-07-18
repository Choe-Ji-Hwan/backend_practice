package com.example.demo_spring.service;

import com.example.demo_spring.domain.Member;
import com.example.demo_spring.repository.MemberRepository;
import com.example.demo_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 스프링 부트를 연결할 통합 테스트
 * SpringBootTest: 스프링 컨테이너와 함께 테스트 함.
 * Transactional: 이 어노테이션을 추가하면, beforeEach 해서 추가했던 걸 삭제할 필요 없이,
 *                  commit 이 오토로 되지 않는다. 테스트 끝난 다음, 롤백 한다.
 * 어노테이션 @Commit 하면 커밋됨.
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;

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