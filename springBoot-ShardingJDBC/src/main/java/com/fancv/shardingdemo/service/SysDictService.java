package com.fancv.shardingdemo.service;


import com.fancv.shardingdemo.model.SysDict;

public interface SysDictService {

    SysDict getDictByCode(String dataCode);

    int addDict(SysDict sysDict);
}
