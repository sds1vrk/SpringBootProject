package hello.hellospring.service;


import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberSerivceIntegrationTest {


    //필드기반 연결
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;




    // @Commit이 있으면 @Transactional의 의해 Rollback 되지않고 저장됨
    @Test
//    @Commit
    void join() {
        //given - 무언가가 주어졌을때 (데이터)
        Member member=new Member();
        member.setName("spring2222");

        //when - 실행앴을때
        Long saveId=memberService.join(member);

        //then - 결과가 나와야 함 , 검증
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    /*테스트는 예외 케이스도 중요하기 때문에 thorws에 걸리는것도 확인하는 것이 중요
     * */
    @Test
    public void 중복_회원_예외() {
        //given
        Member member1=new Member();
        member1.setName("spring");

        Member member2=new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        /* 1. try ~ catch를 이용해서 잡는 방법
        try {
            memberService.join(member2);
            fail("이미 존재하는 회원");
        }catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 123123123");
        }*/

        /* 2. assertThorws를 이용
         *
         * */
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");


        //then

    }

}
