package com.se.classmategalaxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.se.classmategalaxy.entity.Evaluate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wyx20
 * @version 1.0
 * @title EvaluateMapper
 * @description
 * @create 2024/1/12 2:20
 */
@Mapper
public interface EvaluateMapper extends BaseMapper<Evaluate> {
    @Insert("INSERT INTO evaluate (evaluate_id,score,reason,resource_id,user_id) values (#{evaluateId},#{score},#{reason},#{resourceId},#{userId})")
    int addNewEvaluate(Evaluate evaluate);

    @Select("SELECT score from evaluate where resource_id=#{resourceId}")
    List<Float> selectByResource(Integer resourceId);

    @Select("SELECT * from evaluate where resource_id=#{resourceId}")
    List<Evaluate> selectAllByResource(int resourceId);
}
