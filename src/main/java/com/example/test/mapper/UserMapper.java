package com.example.test.mapper;

import com.example.test.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User selectById(@Param("id") int id);
}
