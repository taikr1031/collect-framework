package com.xxx.collect.base.model;

import java.io.Serializable;

public class SpiderItemInfoKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.site
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String site;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table spider_item_info
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.site
     *
     * @return the value of spider_item_info.site
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getSite() {
        return site;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.site
     *
     * @param site the value for spider_item_info.site
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.id
     *
     * @return the value of spider_item_info.id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.id
     *
     * @param id the value for spider_item_info.id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
}