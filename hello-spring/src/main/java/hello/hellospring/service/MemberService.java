package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service

//JPA를 사용하려면 반드시 Service 객체에 @Transactional을 사용
@Transactional
public class MemberService {


    /* new 를 통해 객체를 만들면 다른 레피자토리를 만들어서 실행하는것과 같으므로 인스턴스를 통일시킨다*/
    // private final MemberRepository memberRepository =new MemoryMemberRepository();

    // 외부에서 repository의 값을 Service에 넣어준다. 이를 DI(Dependency Injection)이라고 함
    private final MemberRepository memberRepository;

    //MemberSerivce는 MemberRepository가 필요함 따라서 @Autowired로 연결
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }


    /*
    * 회원 가입
    * 서비스는 핵심 비즈니스 로직, repository에 데이터를 저장하거나 repository에 있는 데이터를 꺼내서 확인하는 실제 로직
    * */
    public Long join(Member member) {


        // 같은 이름이 있는 중복 회원 X
        // 1. 객체를 만든후 람다를 활용해 중복제거
//        Optional<Member> result = memberRepository.findByname(member.getName());
//        result.ifPresent(m-> {
//            throw new IllegalStateException("이미 존재하는 회원");
//        });
        //2. 리펙토링하여 메소드로 처리, ctrl + alt + m
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();


    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByname(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers() {
            return memberRepository.findAll();
    }


    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
