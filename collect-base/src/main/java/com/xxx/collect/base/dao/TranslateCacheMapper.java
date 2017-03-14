package com.xxx.collect.base.dao;

import com.xxx.collect.base.model.TranslateCache;
import com.xxx.collect.base.model.TranslateCacheExample;
import com.xxx.collect.base.model.TranslateCacheKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TranslateCacheMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int countByExample(TranslateCacheExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int deleteByExample(TranslateCacheExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int deleteByPrimaryKey(TranslateCacheKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int insert(TranslateCache record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int insertSelective(TranslateCache record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    List<TranslateCache> selectByExampleWithRowbounds(TranslateCacheExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    List<TranslateCache> selectByExample(TranslateCacheExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    TranslateCache selectByPrimaryKey(TranslateCacheKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByExampleSelective(@Param("record") TranslateCache record, @Param("example") TranslateCacheExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByExample(@Param("record") TranslateCache record, @Param("example") TranslateCacheExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByPrimaryKeySelective(TranslateCache record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table translate_cache
     *
     * @mbggenerated Fri Feb 17 11:19:30 CST 2017
     */
    int updateByPrimaryKey(TranslateCache record);
}