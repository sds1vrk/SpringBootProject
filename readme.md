# 스프링부트 
## 인프런 강의 

인프런 강의를 바탕으로 한 스프링부트 Project 정리 (김영한 강사님)
1. https://www.inflearn.com/course
2. https://docs.spring.io/spring-boot/docs/2.3.10.BUILD-SNAPSHOT/reference/html/spring-boot-features.html#boot-features
## 환경 설정 
 - 필요한 라이브러리 관리 툴
 - maven
 - gradle

현재는 gradle을 사용
java version은 11은 사용
# 에러 사항 발생
Settings - Gradle을 Intellij IDEA로 변경


 -  Spring boot는 Tomcat을 내장하고 있음
 -  tymeleaf 타임 리프

 - 현업은 print로 출력 안하고 log로 출력
 - 실무에선 log를 사용
 - 로그는 logback, slf4j를 사용

 -  테스트는 JUnit 5버전을 사용

## 스프링 부트는 크게 3가지 
 - static : static은 정적 페이지 ==> html
 - MVC (viewResolver)
 - API (요청받은 것이 객체)

## tymeleaf 템플릿 엔진
- templates폴더에서 동작하도록 함, Controller에서 넘겨온 데이터를 return 해주는 곳
- copy -> Absolute Path 를 하면 -> 웹에서 페이지를 바로 볼 수 있음

## Get Method로 요청
하면 HelloController 실행 
Model에 value를 hello.html에 값에 치환해서 동작


## 빌드
명령 프롬프트로 프로젝트 경로 접속 후에
gradlew build 엔터치면 build 폴더 생성 
cd build
java -jar hello-spring-0.0.1-SNAPSHOT.jar 실행
==> 해당 프로젝트만 서버에 넣어서 실행

## 정적컨텐츠
 - 파일을 그대로 고객에게 전달
 - 어떤 프로그램을 할 수는 없고 파일을 그대로 전달
 - /static/hello-static.html
 - 웹브라우저에서 요청, 내장 톰켓 서버 확인 - hello-static 관련 controller가 없으면 static 폴더 검색 후에 return

## MVC와 템플릿 엔진
 - html을 동적으로 바꿔서 전달(템플릿 엔진)
 - 서버에서 변형을 해서 바꾸는 방식
 - Model View Controller
 - 과거에는 JSP (View)에서 모든 것을 처리 ==> MVC Model 1 방식 
 - 현재는 Controller와 View를 분리
 - View는 화면을 그리는 곳 
 - Controller는 내부로직 처리, 서버 비즈니스 로직을 Model에 담아서 View로 Return


## API
 - 요청에 따라 JSON/XML등을 반환해주는 것
 - View, React
- 객체를 반환해주어 JSON, XML등으로 반환해주는것

## REST
 - GET 방식
 - http://localhost:8080/hello-mvc?name=spring!!!!!!!!!!!!!!!
 - ? 를 이용해 값을 전달
 -  ResponseBody  : body부의 파라미터를 직접 넘겨줌

--------------------------------------------------------------------------------------
## 회원 관리 예제 - 백엔드 개발
비즈니스 요구사항 정리
 - 데이터 : 회원ID, 이름
 - 기능 : 회원등록, 조회
 - 아직 데이터 저장소가 선정되지 않음 (가상의 시나리오)

 - 일반적인 구조 : 4+1(DB)
 - 컨트롤러 : 웹 MVC의 컨트롤러 역할
 - 서비스 : 핵심 비즈니스 로직 구현
 - 리포지토리 : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
 - 도메인 : 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등을 주로 데이터베이스 저장하고 관리됨 

클래스 의존관계
MemberSerivce -> Interface(MemberRepository) <- MemoryMemberRepository
-  데이터 저장소는 RDB, NoSQL등등 다양한 저장소를 고민중인 상황으로 과정
- 개발 초기에는 가벼운 메모리 기반의 데이터 저장소 사용

- 테스트 케이스 작성
- MemoryMemberRepository에 내용이 실제 동작하는 확인
- 개발한 Serivce, Repository 테스트


## 스프링 빈과 의존관계

- 스프링 빈을 등록하고, 의존관계 설정하기
서비스를 통해 레파지토리에 저장하고, 레파지토리에서 꺼낼수 있음

- Controller가 서비스를 의존한다. 

- 스프링이 실행되면 스프링 컨테이너에 Controller bean이 관리되고 실행됨 
- Autowired는 Controller -> Service -> Repository의 Component를 연결시켜줌

- 스프링 빈을 등록하는 2가지 방법
1. 컴포넌트 스캔과 자동 의존관계 설정
 : Annotation을 이용한 방법, 자동 의존관계는 생성자를 통해 주입
 : @Component 가 있으면 스프링 빈으로 자동 등록된다
 : @Controller, @Service, @Repository는 @Component가 포함됨

* 스프링은 스프링 컨테이너에 스프링 빈을 등록할떄, 기본적으로 싱글톤으로 등록
* 싱글톤이란 유일하게 하나만 등록해서 공유
==> 따라서 같은 스프링 빈이면 모두 같은 인스턴스 (메모리 절약 등의 장점)
즉 memberController -> memberService -> memberRepository
는 각 하나만 Spring에 등록해서 autowired로 공유
예를 들어 orderSerivce가 memberRepository를 달라고 하면 memberSerivce와 같은 객체를 넘겨 싱글톤을 유지함
따라서 같은 스프링 빈이면 모두 같은 인스턴스 , 설정으로 싱글톤이 아니게 설정할수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용


2. 자바코드로 직접 스프링 빈 등록하기
SpringConfig 클래스를 만들고
@Configuration - Spring이 올라갈때 Bean을 확인하는 부분
Serivce Bean 객체를 만든다 이를 - Repository랑 연결한다.
==> Controller - Service - Repository가 연결됨

* DI에는 필드 주입, 생성자 주입, setter 주입 3가지 있음 
필드주입은 값을 변경할 수 없어 많이 사용하지 않음
* 생성자 주입을 권장


* 실무에서는 (1). 컴포넌트 스캔을 사용
* 정형화되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈을 등록 
==> 임시 메모리 레포지토리를 실제 DB에 연결하고자 할때 코드의 재 변경을 하지 않기 위해 사용 

---------------------------------------------------------------------------------------------------

## 스프링 DB 접근 기술
- H2 데이터베이스 설치
- 순수 JDBC
- 스프링 JDBC Template
- JPA : JPA를 쓰면 쿼리 없이 DB에 저장가능
- 스프링 데이터 JPA


1. JDBC
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
runtimeOnly 'com.h2database:h2'
을 build.gradle에 추가 

- 스프링은 객체 지향에 특화되어 있음
SpringConfig를 수정한것만으로 기존 코드 수정이 필요없음.

- OCP, Open-Closed Principle 개방폐쇄원칙
 확장에는 열려있고, 수정(변경)에는 닫혀있다. 
 - 기능을 변경해도 기존 코드는 손대지 않음. 
 - 다형성이 객체지향프로그래밍에 큰 장점
 - 스프링의 DI을 사용하면 기존 코드를 손대지 않고, 설정만으로 구현 클래스를 변경할 수있다. 

- 
@SpringBootTest : 스프링 컨테이너와 함께 실행한다. 
@Transactional - 
Test에서 Transactional을 하면 테스트를 한 후에 항상 Rollback을 해준다. 따라서 데이터가 실제 DB에 남지 않아 지속적인 테스트가 가능하다. 

Service에 붙으면 정상적으로 commit하고 이상이 있을경우에만 Rollback


- 단위테스트는 자바 코드만 붙어 service별 테스트 - 테스트 시간이 짧다 
- 종합테스트는 스프링과 같이 붙여 DB까지 연결하여 테스트 

좋은 테스트는 단위 테스트를 잘 만든 테스트 케이스 


2. 스프링 JDBCTemplate
- Mybatis랑 비슷하며 JDBC API에서 본 반복 코드 대부분을 제거한다. 하지만 SQL은 직접 작성해야한다. 

3. JPA
- 쿼리를 넣을 필요 없이 사용
- JPA를 사용하면 개발 생산성을 크게 높일 수 있음 
- JPA - 표준 인터페이스
- Hibernate - JPA 구현해주는 라이브러리
- ORM 오브젝트 릴레이션 매핑 


4. 스프링 데이터 JPA
- 리포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료 가능, CRUD 
- 조금이라도 단순하고 반복이라고 생각했던 개발 코드들이 확연하게 줄어줌
- 핵심 비즈니스 로직을 개발하는데 집중 가능
- JPA를 먼저 학습한 후에 스프링 데이터 JPA학습이 필요함
- 상속받아 사용하기 때문에 기본적인 CRUD는 자동으로 들어감
- 이름으로 찾거나 이메일로 찾는 등의 행위만 따로 만들어주면 됨
- 실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용하고, 복잡한 쿼리는 Querydsl이라는 라이브러리를 사용함
- 또는 JdbcTemplate를 같이 사용

----------------------------------------------------------------------------------------------------------


## AOP (Aspect Oriented Programming)

1. AOP가 필요한 상황
- 모든 메소드의 호출 시간을 측정하고 싶다면?
- 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern)
- 회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?

2. 회원가입, 회원 조회에 대한 시간을 측정하는 기능은 핵심 관심 사항이 아님
- 시간을 측정하는 로직은 공통 관심 사항이다.
- 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들기 매우 어렵다.
- 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

TimeTraceAop ==> 원하는 곳에 공통 관심 사항 적용


3. AOP로 해결
- 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
- 핵심 관심 사항을 깔끔하게 유지할 수 있다.
- 변경이 필요하면 이 로직만 변경하면 된다.
- 원하는 적용 대상을 선택할 수 있다.

4. Controller - Service - Repository
에서 프록시를 각자만들어서 넘겨줌 



