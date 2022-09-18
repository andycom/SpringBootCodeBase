package com.example.demo.mapper;

import com.example.demo.model.SysDict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDictMapper {

    SysDict getDictByCode(String dataCode);

    int addDict(SysDict sysDict);
}
