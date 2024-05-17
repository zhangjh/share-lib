package me.zhangjh.share.user.service;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * @author njhxzhangjihong@126.com
 * @date 15:14 2024/5/17
 * @Description
 */
@Data
@TableName("tbl_account")
public class TblAccount {

    private Long id ;
    private Date createTime ;
    private Date modifyTime ;
    private Integer isDeleted ;

    private String name;

    private String avatar;

    private Integer extType;

    private String extId;

    private String mobile;

    private String productType;
}
