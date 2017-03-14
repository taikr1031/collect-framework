package com.xxx.collect.base.model;

import java.io.Serializable;
import java.util.Date;

public class SpiderItemInfo extends SpiderItemInfoKey implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.resc_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String rescId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.seq
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private Integer seq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.name
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.cat
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String cat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.tags
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String tags;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.down_file_path
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String downFilePath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.file_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private Integer fileId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column spider_item_info.create_time
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table spider_item_info
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.resc_id
     *
     * @return the value of spider_item_info.resc_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getRescId() {
        return rescId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.resc_id
     *
     * @param rescId the value for spider_item_info.resc_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setRescId(String rescId) {
        this.rescId = rescId == null ? null : rescId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.seq
     *
     * @return the value of spider_item_info.seq
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.seq
     *
     * @param seq the value for spider_item_info.seq
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.name
     *
     * @return the value of spider_item_info.name
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.name
     *
     * @param name the value for spider_item_info.name
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.cat
     *
     * @return the value of spider_item_info.cat
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getCat() {
        return cat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.cat
     *
     * @param cat the value for spider_item_info.cat
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setCat(String cat) {
        this.cat = cat == null ? null : cat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.tags
     *
     * @return the value of spider_item_info.tags
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getTags() {
        return tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.tags
     *
     * @param tags the value for spider_item_info.tags
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.down_file_path
     *
     * @return the value of spider_item_info.down_file_path
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getDownFilePath() {
        return downFilePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.down_file_path
     *
     * @param downFilePath the value for spider_item_info.down_file_path
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setDownFilePath(String downFilePath) {
        this.downFilePath = downFilePath == null ? null : downFilePath.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.file_id
     *
     * @return the value of spider_item_info.file_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.file_id
     *
     * @param fileId the value for spider_item_info.file_id
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column spider_item_info.create_time
     *
     * @return the value of spider_item_info.create_time
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column spider_item_info.create_time
     *
     * @param createTime the value for spider_item_info.create_time
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}