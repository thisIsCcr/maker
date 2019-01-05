package com.crsm.maker.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Ccr
 * @since 2018-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录账号
     */
    private String usrName;

    /**
     * 用户名
     */
    private String usrAccount;

    /**
     * 电话号码
     */
    private String usrPhone;

    /**
     * 电子邮箱
     */
    private String usrEmail;

    /**
     * 用户密码
     */
    private String usrPassword;

    /**
     * 年龄
     */
    private Integer usrAge;

    /**
     * 用户头像
     */
    private String usrHeadimage;

    /**
     * 性别（1：男、2：女）
     */
    private Integer usrSex;

    /**
     * 是否冻结（1：是、2：否）
     */
    private Integer isFreeze;

    /**
     * 更新时间
     */
    private LocalDate updateDate;

    /**
     * 创建时间
     */
    private LocalDate cartData;

    /**
     * 盐值
     */
    private String usrSalt;

    public String getCredentialsSalt() {
        return this.usrName + this.usrSalt;
    }

}
