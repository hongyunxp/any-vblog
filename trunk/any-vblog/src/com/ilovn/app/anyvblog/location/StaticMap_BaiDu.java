package com.ilovn.app.anyvblog.location;

public class StaticMap_BaiDu {
	private int width;//	否	400	图片宽度。取值范围：(0, 1024]。
	private int height;//	否	300	图片高度。取值范围：(0, 1024]。
	private Center center;//	否	北京	地图中心点位置，参数可以为经纬度坐标或名称。
	private int zoom;//	否	11	地图级别。取值范围：[1, 18]。
	private String markers;//	否	null	标注，可通过经纬度或地址/地名描述；多个标注之间用竖线分隔。
	private String markerStyles;//	否	null	分隔。与points有索引对应关系。其中单个值是自定义风格时，逗号分隔的参数值则由-1、url、阴影组成。
	private String labels;//	否	null	标签，可通过经纬度或地址/地名描述；多个标签之间用竖线分隔。
	private String labelStyles;//	否	null	标签样式 content,border,fontSize,fontColor,bgColor,fontWeight。
	
	public StaticMap_BaiDu(double lat, double lon, int zoom) {
		this.width = 360;
		this.height = 200;
		this.center = new Center(lat, lon);
		this.zoom = zoom;
		this.markers = center.toString();
		this.markerStyles = "m,A,blue";
		
	}

	//bbox	否	null	地图视野范围。格式：minX,minY,maxX,maxY。
	//paths	否	null	折线，可通过经纬度或地址/地名描述；多个折线用竖线"|"分隔；每条折线的点用分号";"分隔；点坐标用逗号","分隔。
	//pathStyles	否	null	折线样式 color,weight,opacity[,fillColor]。
	
	
	
	
	class Center {
		private double lat;
		private double lon;
		/**
		 * 
		 * @param lat 纬度
		 * @param lon 经度
		 */
		public Center(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
			
		}
		@Override
		public String toString() {
			return lat + "," + lon;
		}
	}

	@Override
	public String toString() {
		return "?width=" + width + "&height=" + height
				+ "&center=" + center + "&zoom=" + zoom + "&markers="
				+ markers + "&markerStyles=" + markerStyles;
	}
	
}
