package com.chen.cache.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author jim-chen
 * @email 2101185637@qq.com
 * @date 2022-03-09 19:39:12
 */
@Data
@TableName("course")
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer cno;
	/**
	 * 
	 */
	private String cname;
	/**
	 * 
	 */
	private Integer credit;

	@TableLogic
	private Integer isDeleted;

}
