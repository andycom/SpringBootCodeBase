package com.fancv.shardingdemo.service.impl;

import com.fancv.shardingdemo.mapper.SysDictMapper;
import com.fancv.shardingdemo.service.SysDictService;
import com.fancv.shardingdemo.model.SysDict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public SysDict getDictByCode(String dataCode) {
        return sysDictMapper.getDictByCode(dataCode);
    }

    @Override
    public int addDict(SysDict sysDict) {
        return sysDictMapper.addDict(sysDict);
    }
}
