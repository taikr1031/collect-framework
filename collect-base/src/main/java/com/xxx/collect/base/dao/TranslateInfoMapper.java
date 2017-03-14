package com.xxx.collect.base.dao;

import com.xxx.collect.base.model.TranslateInfo;
import com.xxx.collect.base.model.TranslateInfoExample;
import com.xxx.collect.base.model.TranslateInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TranslateInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int countByExample(TranslateInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int deleteByExample(TranslateInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int deleteByPrimaryKey(TranslateInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int insert(TranslateInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int insertSelective(TranslateInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    List<TranslateInfo> selectByExampleWithRowbounds(TranslateInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    List<TranslateInfo> selectByExample(TranslateInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    TranslateInfo selectByPrimaryKey(TranslateInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByExampleSelective(@Param("record") TranslateInfo record, @Param("example") TranslateInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByExample(@Param("record") TranslateInfo record, @Param("example") TranslateInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByPrimaryKeySelective(TranslateInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_info
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByPrimaryKey(TranslateInfo record);
}