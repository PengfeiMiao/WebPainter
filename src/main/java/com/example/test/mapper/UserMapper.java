package com.example.test.mapper;

import com.example.test.entity.User;
import com.example.test.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {
    @Insert("insert into tb_user values(#{id},#{username},#{password},#{login},#{loginHost},#{auth})")
    @SelectKey(statement = "select count(*) as id from tb_user", keyProperty = "id", before = true, statementType = StatementType.STATEMENT, resultType = String.class)
    int addUser(User user);

    @Select("select * from tb_user where username = #{username}")
    User selectByUsername(String username);
}
