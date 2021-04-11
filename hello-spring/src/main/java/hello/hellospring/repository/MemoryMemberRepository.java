package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store=new HashMap<>();
    private static long sequence=0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Null인것을 방지
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByname(String name) {
        // 람다 사용, 루프를 돌리면서 파라미터로 온 것과 같으면 반환, findAny는 하나라도 찾으면 Optinal로 반환, 없으면 Null 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
