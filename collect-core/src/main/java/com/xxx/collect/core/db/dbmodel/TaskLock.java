package com.xxx.collect.core.db.dbmodel;

import java.io.Serializable;
import java.util.Date;

public class TaskLock implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.id
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.unique_key
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private String uniqueKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.create_time
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.host
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private String host;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.port
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private Integer port;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_lock.remark
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table task_lock
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.id
     *
     * @return the value of task_lock.id
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.id
     *
     * @param id the value for task_lock.id
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.unique_key
     *
     * @return the value of task_lock.unique_key
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public String getUniqueKey() {
        return uniqueKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.unique_key
     *
     * @param uniqueKey the value for task_lock.unique_key
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey == null ? null : uniqueKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.create_time
     *
     * @return the value of task_lock.create_time
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.create_time
     *
     * @param createTime the value for task_lock.create_time
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.host
     *
     * @return the value of task_lock.host
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public String getHost() {
        return host;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.host
     *
     * @param host the value for task_lock.host
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.port
     *
     * @return the value of task_lock.port
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public Integer getPort() {
        return port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.port
     *
     * @param port the value for task_lock.port
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_lock.remark
     *
     * @return the value of task_lock.remark
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_lock.remark
     *
     * @param remark the value for task_lock.remark
     *
     * @mbggenerated Thu Aug 25 10:46:02 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}