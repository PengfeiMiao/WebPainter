package com.example.test.mapper;

import com.example.test.entity.CommonFile;
import com.example.test.mapper.base.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

@Component
public interface FileMapper extends BaseMapper<CommonFile> {
    @Insert("insert into tb_common_file values(#{id},#{filename},#{filepath},#{uploadtime},#{creator})")
    @SelectKey(statement="select count(*) as id from tb_common_file", keyProperty="id", before=true, statementType=StatementType.STATEMENT,resultType=String.class)
    void save(CommonFile file);
}
