package com.example.demo_spring.repository;

import com.example.demo_spring.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {

    // static 공유 변수기 때문에, concurrent HashMap 써야 되는데 예제라, 기본 HashMap 사용.
    private static Map<Long, Member> store = new HashMap<>();
    // 키 값 생성. 얘도 concurrent 고려 하긴 해야 됨.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return store.values().stream().toList();
    }

    public void clearStore() {
        store.clear();
    }
}
