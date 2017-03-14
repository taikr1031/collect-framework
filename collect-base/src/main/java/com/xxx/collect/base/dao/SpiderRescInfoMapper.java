package com.xxx.collect.base.dao;

import com.xxx.collect.base.model.SpiderRescInfo;
import com.xxx.collect.base.model.SpiderRescInfoExample;
import com.xxx.collect.base.model.SpiderRescInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SpiderRescInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int countByExample(SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int deleteByExample(SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int deleteByPrimaryKey(SpiderRescInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int insert(SpiderRescInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int insertSelective(SpiderRescInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    List<SpiderRescInfo> selectByExampleWithBLOBsWithRowbounds(SpiderRescInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    List<SpiderRescInfo> selectByExampleWithBLOBs(SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    List<SpiderRescInfo> selectByExampleWithRowbounds(SpiderRescInfoExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    List<SpiderRescInfo> selectByExample(SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    SpiderRescInfo selectByPrimaryKey(SpiderRescInfoKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByExampleSelective(@Param("record") SpiderRescInfo record, @Param("example") SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") SpiderRescInfo record, @Param("example") SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByExample(@Param("record") SpiderRescInfo record, @Param("example") SpiderRescInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByPrimaryKeySelective(SpiderRescInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(SpiderRescInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table spider_resc_info
     *
     * @mbggenerated Fri Feb 17 11:22:45 CST 2017
     */
    int updateByPrimaryKey(SpiderRescInfo record);
}