package com.ilovn.app.anyvblog.model.tencent;

import java.util.List;
import java.util.Map;

import com.ilovn.app.anyvblog.model.User;
import com.ilovn.app.anyvblog.model.UserAdapter;
import com.ilovn.app.anyvblog.utils.Constants;

public class UserInfo4Tencent implements User {
	private String name;	//用户帐户名
	private String openid;	//用户唯一id，与name相对应
	private String nick;	//用户昵称
	private String head;	//头像url
	private String location;	//所在地
	private int isvip;	//是否认证用户
	private int isent;	//是否企业机构
	private String introduction;	//个人介绍
	private String verifyinfo;	//认证信息
	private String email;	//邮箱
	private String birth_year;	//出生年
	private String birth_month;	//出生月
	private String birth_day;	//出生天 
	private String country_code;	//国家id
	private String province_code;	//地区id
	private String city_code;	//城市id
	private int sex;	//用户性别，1-男，2-女，0-未填写
	private int fansnum;	//听众数
	private int idolnum;	//收听的人数
	private int tweetnum;	//发表的微博数
	private List<Tag> tag;
	private List<Edu> edu;
	
	private List<Data4Tencent> tweet;//:用户最近发的一条微博
	
	private int ismyidol;	//是否为accesstoken用户收听的人
	private int ismyfans;	//是否为accesstoken 用户的听众
	private int ismyblack;	//是否在 accesstoken 用户的黑名单内
	@Override
	public UserAdapter conver2User() {
		UserAdapter adapter = new UserAdapter();
		adapter.setSp(Constants.SP_TENCENT);
		adapter.setName(name);
		adapter.setUser_id(name);
		adapter.setNick(nick);
		//adapter.setHead(null);//设置头像
		adapter.setHead_url(head + "/50");
		adapter.setCount_followers(fansnum);
		adapter.setCount_friends(idolnum);
		adapter.setCount_status(tweetnum);
		if (sex == 1) {
			adapter.setSex("男");
		} else if(sex == 2) {
			adapter.setSex("女");
		} else {
			adapter.setSex("未填写");
		}
		adapter.setDescription(introduction);
		adapter.setLocation(location);
		if (isvip == 1) {
			adapter.setIsvip(true);
		} else {
			adapter.setIsvip(false);
		}
		if (ismyidol == 1) {
			adapter.setFriend(true);
		} else {
			adapter.setFriend(false);
		}
		if (ismyfans == 1) {
			adapter.setFollower(true);
		} else {
			adapter.setFollower(false);
		}
		if (ismyblack == 1) {
			adapter.setBlack(true);
		} else {
			adapter.setBlack(false);
		}
		return adapter;
	}
	@Override
	public Map<String, Object> conver() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getIsvip() {
		return isvip;
	}
	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}
	public int getIsent() {
		return isent;
	}
	public void setIsent(int isent) {
		this.isent = isent;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getVerifyinfo() {
		return verifyinfo;
	}
	public void setVerifyinfo(String verifyinfo) {
		this.verifyinfo = verifyinfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(String birth_year) {
		this.birth_year = birth_year;
	}
	public String getBirth_month() {
		return birth_month;
	}
	public void setBirth_month(String birth_month) {
		this.birth_month = birth_month;
	}
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getFansnum() {
		return fansnum;
	}
	public void setFansnum(int fansnum) {
		this.fansnum = fansnum;
	}
	public int getIdolnum() {
		return idolnum;
	}
	public void setIdolnum(int idolnum) {
		this.idolnum = idolnum;
	}
	public int getTweetnum() {
		return tweetnum;
	}
	public void setTweetnum(int tweetnum) {
		this.tweetnum = tweetnum;
	}
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}
	public List<Edu> getEdu() {
		return edu;
	}
	public void setEdu(List<Edu> edu) {
		this.edu = edu;
	}
	public int getIsmyidol() {
		return ismyidol;
	}
	public void setIsmyidol(int ismyidol) {
		this.ismyidol = ismyidol;
	}
	public int getIsmyfans() {
		return ismyfans;
	}
	public void setIsmyfans(int ismyfans) {
		this.ismyfans = ismyfans;
	}
	public int getIsmyblack() {
		return ismyblack;
	}
	public void setIsmyblack(int ismyblack) {
		this.ismyblack = ismyblack;
	}
	public void setTweet(List<Data4Tencent> tweet) {
		this.tweet = tweet;
	}
	public List<Data4Tencent> getTweet() {
		return tweet;
	}
}
