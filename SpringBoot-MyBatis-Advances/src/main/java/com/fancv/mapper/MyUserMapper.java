package com.fancv.mapper;

import com.fancv.dao.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * replace 根据主键判断数据是否存在，若不存在则插入。存在则不变。
     *
     * 批量更新数据，如果数据库存在则更新，不存在则写入
     *
     * 分析唯一建
     * @param users
     * @return
     */
    int updateOrInsertClientInfo(List<User> users);


    int updateOrInsertClientInfoTwo(List<User> users);

    int updateOrInsertClientInfoSingle(User user);


}