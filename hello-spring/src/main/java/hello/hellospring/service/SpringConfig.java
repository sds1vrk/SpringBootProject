package hello.hellospring.service;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/* 컴포넌트 스캔을 사용하지 않고 Serivce를 Bean에 등록하는 방법
    Controller는 그대로 두고 Controller가 MemberSerivce를 찾기 위해 MemberSerivce Bean을 스프링에 등록한다
    MemberService와 연결되어 있는 MemberRepository Bean을 스프링에 등록한다
    ==> MemberSerivce에 실행될때 MemberRepository를 생성자로 등록하기 때문에 연결됨.

*/

@Configuration
public class SpringConfig {

//    private DataSource dataSource;

//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }


    // JPA
//    private EntityManager em;
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em=em;
//    }

    //    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);

//    }

    //SpringDataJpaRepository
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }


    // AOP를 Bean에 등록
//    @Bean
//    public TimeTraceAop TimeTraceAop() {
//        return new TimeTraceAop();
//    }






}
