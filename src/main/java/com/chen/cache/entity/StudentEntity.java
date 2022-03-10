package com.chen.cache.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * 
 * 
 * @author jim-chen
 * @email 2101185637@qq.com
 * @date 2022-03-09 19:39:12
 */
@Data
@TableName("student")
@ToString
public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer sno;
	/**
	 * 
	 */
	private String sname;
	/**
	 * 
	 */
	private String ssex;
	/**
	 * 
	 */
	private Integer sage;
	/**
	 * 
	 */
	private String sdept;

	@TableLogic
	private int isDeleted;

}
