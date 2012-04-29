package com.ilovn.app.anyvblog.model.tencent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.ilovn.app.anyvblog.model.Data;
import com.ilovn.app.anyvblog.model.DataAdapter;
import com.ilovn.app.anyvblog.utils.Constants;

public class Data4Tencent implements Data {
	private String text;	//微博内容
	private String origtext;	//原始内容
	private int count;	//微博被转次数
	private int mcount;	//点评次数
	private String from;	//来源
	private String fromurl;	//来源url
	private String id;	//微博唯一id
	private String jing;
	private String wei;
	private List<String> image;	//图片url列表
	private Video video;	//视频信息
	private Music music;	//音频信息
	private String name;	//发表人账户名
	private String openid;	//用户唯一id,与name相对应
	private String nick;	//发表人昵称
	private int self;	//是否自已发的的微博，0-不是，1-是
	private String timestamp;	//发表时间
	private int type;	//微博类型，1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
	private String head;	//发表者头像url
	private String location;	//发表者所在地
	private String country_code;	//国家码（其他时间线一样）
	private String province_code;	//省份码（其他时间线一样）
	private String city_code;	//城市码（其他时间线一样）
	private int isvip;	//是否微博认证用户，0-不是，1-是
	private String geo;	//发表者地理信息
	private int status;	//微博状态，0-正常，1-系统删除，2-审核中，3-用户删除，4-根删除
	private String emotionurl;	//心情图片url
	private int emotiontype;	//心情类型
	private Data4Tencent source;	//source:当type=2时，source即为源tweet
	//以下参数在私信中有效
	private String tohead;
	private int toisvip;
	private String toname;
	private String tonick;
	
	public int isToisvip() {
		return toisvip;
	}
	public void setToisvip(int toisvip) {
		this.toisvip = toisvip;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	public String getTonick() {
		return tonick;
	}
	public void setTonick(String tonick) {
		this.tonick = tonick;
	}
	@Override
	public DataAdapter conver2Data() {
		DataAdapter adapter = new DataAdapter();
		adapter.setSp(Constants.SP_TENCENT);
		adapter.setName(name);
		adapter.setNick(nick);
		adapter.setHead_url(head + "/50");
		adapter.setStatus_id(id);
		if (isvip == 1) {
			adapter.setIsvip(true);
		} else {
			adapter.setIsvip(false);
		}
		adapter.setType(type);
		adapter.setTime(coverTime(timestamp));
		adapter.setOrigtime(timestamp);
		adapter.setStatus(text);
		adapter.setFrom(from);
		if (image != null && image.size() > 0) {
			adapter.setImage_url(image.get(0) + "/460");
			adapter.setImage_s(image.get(0) + "/160");
			adapter.setImage_m(image.get(0) + "/460");
			adapter.setImage_o(image.get(0) + "/2000");
		}
		adapter.setForward_count(count);
		adapter.setComment_count(mcount);
		if (jing != null) {
			adapter.setLat(Double.parseDouble(wei));
			adapter.setLng(Double.parseDouble(jing));
		}
		adapter.setUser_id(name);
		if (source != null && !"null".equals(source) && !"".equals(source)) {
			adapter.setSource(true);
			adapter.setSource_name(source.getName());
			adapter.setSource_nick(source.getNick());
			adapter.setSource_status_id(source.getId());
			if (source.getIsvip() == 1) {
				adapter.setSource_isvip(true);
			} else {
				adapter.setSource_isvip(false);
			}
			adapter.setSource_time(coverTime(source.getTimestamp()));
			adapter.setSource_status(source.getText());
			adapter.setSource_from(source.getFrom());
			if (source.getImage() != null && source.getImage().size() > 0) {
				adapter.setSource_image_url(source.getImage().get(0) + "/460");
				adapter.setSource_image_s(source.getImage().get(0) + "/160");
				adapter.setSource_image_m(source.getImage().get(0) + "/460");
				adapter.setSource_image_o(source.getImage().get(0) + "/2000");
			}
			adapter.setSource_forward_count(source.getCount());
			adapter.setSource_comment_count(source.getMcount());
			adapter.setSource_user_id(source.getName());
		} else {
			adapter.setSource(false);
		}
		if (self == 0) {
			adapter.setSelf(false);
		} else if (self == 1) {
			adapter.setSelf(true);
		}
		adapter.setTohead_url(tohead);
		if (toisvip == 0) {
			adapter.setToisvip(false);
		} else {
			adapter.setToisvip(true);
		}
		adapter.setToname(toname);
		adapter.setTonick(tonick);
		return adapter;
	}
	@Override
	public Map<String, Object> conver() {
		return null;
	}
	
	private String coverTime(String timeString) {
		Timestamp stamp = new Timestamp(Long.parseLong(timeString) * 1000);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
		return format.format(stamp);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getOrigtext() {
		return origtext;
	}
	public void setOrigtext(String origtext) {
		this.origtext = origtext;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getMcount() {
		return mcount;
	}
	public void setMcount(int mcount) {
		this.mcount = mcount;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromurl() {
		return fromurl;
	}
	public void setFromurl(String fromurl) {
		this.fromurl = fromurl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Music getMusic() {
		return music;
	}
	public void setMusic(Music music) {
		this.music = music;
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
	public int getSelf() {
		return self;
	}
	public void setSelf(int self) {
		this.self = self;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getIsvip() {
		return isvip;
	}
	public void setIsvip(int isvip) {
		this.isvip = isvip;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmotionurl() {
		return emotionurl;
	}
	public void setEmotionurl(String emotionurl) {
		this.emotionurl = emotionurl;
	}
	public int getEmotiontype() {
		return emotiontype;
	}
	public void setEmotiontype(int emotiontype) {
		this.emotiontype = emotiontype;
	}
	public Data4Tencent getSource() {
		return source;
	}
	public void setSource(Data4Tencent source) {
		this.source = source;
	}

	public String getTohead() {
		return tohead;
	}
	public void setTohead(String tohead) {
		this.tohead = tohead;
	}
	public int getToisvip() {
		return toisvip;
	}
	public String getJing() {
		return jing;
	}
	public void setJing(String jing) {
		this.jing = jing;
	}
	public String getWei() {
		return wei;
	}
	public void setWei(String wei) {
		this.wei = wei;
	}
}
