package com.efrobot.weixin.baseapi.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author wangxx
 * @since 2015-08-19
 * @Description 会员信息
 *
 */

public class User implements Serializable{

	private static final long serialVersionUID = -1481886440669642674L;
	
	private String id;
	private int age;			//年龄
	private int gender;			//性别
	private String realName;	//真实姓名
	private Integer memberGrade;	//会员等级
	private String email;		//电子邮箱
	private String phone;		//电话号码
	private String openid;		//用户的唯一标识 (用与微信的绑定)
	private String password;	//用户密码
	private String weiXinHao;	//微信号
	private String userGender;
	private int source;			//会员来源。0:现场，1:微信
	private String remark;		//备注
	private Date addDate;		//成为用户日期
	private String referrerId;			//推荐人信息(查看时使用)
	private String isSubscribe;    //是否关注   Y-关注，N-未关注
	
	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWeiXinHao() {
		return weiXinHao;
	}

	public void setWeiXinHao(String weiXinHao) {
		this.weiXinHao = weiXinHao;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
