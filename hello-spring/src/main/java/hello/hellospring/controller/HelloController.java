package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    /*MVC*/
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        return "hello";
    }
    /*MVC GET*/
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name",name);
        return "hello-template";
    }

    /*API
    * @ResponseBody를 이용하면
    * viewResolver 대신에 (templates폴더에 추가되는 것이 아니라)
    * HttpMessageConver가 바로 그대로 body의 text를 보여줌
    * request로 String이 오면 String 값 return
    *  */
    @GetMapping("hello-spring")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello "+name;
    }

    /*API 전달 받은 request가 객체가 오면 default로 JSONConverter 즉 json으로 응답
    JSON(key-value) Response body로 전달 {"name":"spring@@@@@"}
    객체를 Json으로 변경 : MappingJackson2HttpMessageConverter
     */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name")String name) {
        Hello hello=new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{
        private String name;

        //getter, setter - ctrl+space
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
