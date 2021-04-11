package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository =new MemoryMemberRepository();



    // 테스트가 끝날때마다 repository를 지워주는 코드 입력,
    // 이때 MemoryMemberRespoitory에 메소드를 하나 만든다.
    // 테스트는 서로 순서와 관계없이 이용해야된다. 따라서 clear를 통해 만든 메모리를 지운다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member=new Member();
        // 바로 아래로 내리기 ctrl + shift + enter
        member.setName("spring");
        repository.save(member);

        // 뒤에 get()은 Optinal로 가져올때 사용
        Member result=repository.findById(member.getId()).get();
        // 인텔리제이는 sout으로 sysout 활용
        // System.out.println("result="+(result==member));

        // 1. assert Unit
        // 출력되는 거 없이 확인 하는 방법, 정상 동작 - 초록불, 다르면 X 표시
        // Assertions.assertEquals(member,null);

        //2. asert J
        // import
        // 테스트 케이스의 장점은 두 메소드간 각각이 정상동작하는지 확인 가능
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 =new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 =new Member();
        member2.setName("spring1");
        repository.save(member2);

        Member result=repository.findByname("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1=new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2=new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }


}
