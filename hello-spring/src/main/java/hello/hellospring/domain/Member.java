package hello.hellospring.domain;



import javax.persistence.*;

//JPA가 관리하는 Entity ==> @Entity
@Entity
public class Member {
    //JPA : Id랑 Pk 매핑, DB가 알아서 PK 생성 : strategy = GenerationType.IDENTITY
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //만약 테이블에 컬럼명이 username이면 알아서 매핑됨
//    @Column(name="username")
    private String name;

    //getter, setter all 단축키 alt+insert
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
