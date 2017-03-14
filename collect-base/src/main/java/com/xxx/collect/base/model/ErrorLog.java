package com.xxx.collect.base.model;

import java.io.Serializable;

public class ErrorLog implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column error_log.CUR_DATE
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String curDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column error_log.TASK_NAME
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String taskName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column error_log.INFO_ID
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String infoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column error_log.LOG_DEV
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private String logDev;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table error_log
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column error_log.CUR_DATE
     *
     * @return the value of error_log.CUR_DATE
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getCurDate() {
        return curDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column error_log.CUR_DATE
     *
     * @param curDate the value for error_log.CUR_DATE
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setCurDate(String curDate) {
        this.curDate = curDate == null ? null : curDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column error_log.TASK_NAME
     *
     * @return the value of error_log.TASK_NAME
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column error_log.TASK_NAME
     *
     * @param taskName the value for error_log.TASK_NAME
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column error_log.INFO_ID
     *
     * @return the value of error_log.INFO_ID
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getInfoId() {
        return infoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column error_log.INFO_ID
     *
     * @param infoId the value for error_log.INFO_ID
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setInfoId(String infoId) {
        this.infoId = infoId == null ? null : infoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column error_log.LOG_DEV
     *
     * @return the value of error_log.LOG_DEV
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public String getLogDev() {
        return logDev;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column error_log.LOG_DEV
     *
     * @param logDev the value for error_log.LOG_DEV
     *
     * @mbggenerated Fri Feb 17 11:19:29 CST 2017
     */
    public void setLogDev(String logDev) {
        this.logDev = logDev == null ? null : logDev.trim();
    }
}