package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    // 생성자가 하나만 Autowired는 생략 가능
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {

        // insert는 queury를 짤 필요 없음.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result=jdbcTemplate.query("select * from member where id=?",memberRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByname(String name) {
        List<Member> result=jdbcTemplate.query("select * from member where name=?",memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member",memberRowMapper());
    }


    //람다로 변경 alt + Enter ==> return 쪽에서
    private RowMapper<Member> memberRowMapper() {
        return (rs, i) -> {
            Member member=new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }

    // 람다 쓰기전
//    private RowMapper<Member> test() {
//        return new RowMapper<Member>() {
//            @Override
//            public Member mapRow(ResultSet rs, int i) throws SQLException {
//                Member member=new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                return member;
//            }
//        };
//    }



}
