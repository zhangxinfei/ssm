package com.soecode.lyf.mapper;

import com.soecode.lyf.entity.Dialogue;

public interface DialogueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dialogue record);

    int insertSelective(Dialogue record);

    Dialogue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dialogue record);

    int updateByPrimaryKeyWithBLOBs(Dialogue record);

    int updateByPrimaryKey(Dialogue record);
}