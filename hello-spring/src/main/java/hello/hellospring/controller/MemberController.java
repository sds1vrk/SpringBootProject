package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // new를 하게 되면 여러 controller에서 실행할떄마다 service를 new로 실행해줘야 함
    // 따라서 다른 방법 사용
//    private final MemberService memberService =new MemberService();

    // memberserivce를 하나만 만들어놓고 공용으로 사용
    // 스프링 컨테이너에 등록을 하고 사용
    private final MemberService memberService;

    // autowired를 하면 Controller를 Serivce에 연결(wired)
    // 이때 Service는 Annotation Service를 해줘야 함, 그래야지 스프링이 찾을수 있음
    // Controller -> Service -> Respository로 연결 (Autowired)
    // Dependency Injection (DI) - 생성자 주입, setter 주입, setter 주입
    // 생성자 DI 주입 (가장 많이 사용)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //setter DI, 누군가가 setter로 값을 변경할 수 있어 문제가 발생
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService=memberService
//    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }


    //createMemberForm.html ==> /members/new로 값을 넘겨줌
    // members/new에 post로 넣어줌, form에 name에 파라미터가 전달되고 이를, member.setName에 저장함
    // 저장된 값을 Serivce join과 넘겨주고 회원가입 완료
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member=new Member();
        member.setName(form.getName());
        memberService.join(member);

        System.out.println("member_name:"+member.getName());

        // 회원가입이 다 되면 home으로 redirect
        return "redirect:/";
    }


    /* 전체 회원 조회
    *  Get으로 /members가 요청이 들어오면
    *  List형태로 받고 이를 Model에 넘겨줌, Model은 처리된 로직을 받아서 memberList.html (View)에 넘겨줌
    * */
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";

    }



}
