package com.xxx.collect.base.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 备份表
 * Created by Tony on 2017/1/16.
 */
public interface QueryBakResTableMapper {

  @Update("CREATE TABLE gei_db5_bak_pd.bak_cat_item_${time} AS SELECT * FROM cat_item")
  void bakCatItem(@Param("time") String time);

  @Update("CREATE TABLE gei_db5_bak_pd.bak_cat_info_161222 AS SELECT * FROM cat_info")
  void bakCatInfo();

}
