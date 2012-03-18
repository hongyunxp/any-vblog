package com.debugi.app.anyvblog.location;

public class StaticMap {
	/**
	 * 位置参数：
		center（不显示标记时为必需）定义地图的中心，到地图各边缘的距离相等。此参数用逗号分隔的 {纬度,经度} 对（例如，“40.714728,-73.998672”）或者字符串地址（例如，“city hall, new york, ny”）表示位置，可以唯一地标识地球表面上的具体位置。有关详细信息，请参见下面的位置。
		zoom（不显示标记时为必需）定义地图的缩放级别，可决定地图缩小和放大的级别。此参数采用的数字值与所需的区域缩放级别相符。有关详细信息，请参见下面的缩放级别。
	   地图参数：
		size（必需）定义地图图片的矩形尺寸。此参数采用了 valuexvalue 形式的字符串，其中前者表示水平像素，后者表示垂直像素。例如，500x400 定义了一幅宽为 500 像素、高为 400 像素的地图。如果您创建一幅宽为 100 像素或更小的静态地图，“由 Google 强力驱动”徽标的大小会自动缩小。
		format（可选）定义生成的图片的格式。默认情况下，静态 Google Maps API 创建 PNG 图像。有多种可用的格式，包含 GIF、JPEG 和 PNG 类型。使用哪种格式取决于您希望以什么方式显示图片。通常，JPEG 可提供更大的压缩率，而 GIF 和 PNG 可提供更多细节。有关详细信息，请参见图像格式。
		maptype（可选）定义要构造的地图类型。有多种可用的地图类型值，包含 roadmap、satellite、hybrid 和 terrain。有关详细信息，请参见下面的 静态 Google Maps API 的地图类型。
		mobile（可选）指定是否在移动设备上显示地图。有效值为 true 或 false。在移动设备上显示的地图可能会使用其他适用于这些设备的图块集。有关详细信息，请参见下面的移动设备地图。
		language（可选）定义在地图图块上显示标签时所用的语言。请注意，该参数仅支持部分国家/地区图块；如果图块集不支持请求的特定语言，则其将使用默认语言。
	   特征参数：
		markers（可选）定义要在指定位置附加到图像的一个或多个标记。此参数采用单个标记定义，该标记定义中的参数以竖线字符 (|) 分隔。多个标记只要样式相同，就可以放置在同一个 markers 参数中。如果需要添加其他不同样式的标记，您可以通过另外添加 markers 参数来实现。请注意，如果为地图提供标记，就无需指定（通常情况下必需的）center 和 zoom 参数。有关详细信息，请参见下面的静态地图标记。
		path（可选）定义指向指定位置图片上叠加层的两个或多个连接点的单条路径。此参数采用由竖线字符 (|) 分隔的点定义字符串作为参数。您可以通过另外添加 path 参数来提供其他路径。请注意，如果为地图提供标记，就无需指定（通常情况下必需的）center 和 zoom 参数。有关详细信息，请参见下面的静态地图路径。
		visible（可选）指定一个或多个即使不显示标记或其他指示器也应该在地图上保持可见的位置。使用此参数可确保在静态地图上显示某些特征或地图位置。
	   报告参数：
		sensor（必需）指定请求静态地图的应用程序是否使用传感器确定用户的位置。所有静态地图请求都需要使用此参数。有关详细信息，请参见下面的指示传感器的使用。
	 */
	private Center center;
	private int zoom;
	private String size;
	private String maptype;
	private boolean mobile;
	private boolean sensor;
	private String markers;
	
	
	public StaticMap(double lat, double lon, int zoom, String size) {
		this.center = new Center(lat, lon);
		this.zoom = zoom;
		this.size = size;
		if (size == null) {
			this.size = "360x200";
		}
		this.maptype = "roadmap";
		this.mobile = true;
		this.sensor = false;
		this.markers = "color:blue|label:A|" + center;
	}


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
		return "?center=" + center + "&zoom=" + zoom + "&size="
				+ size + "&maptype=" + maptype + "&mobile=" + mobile
				+ "&sensor=" + sensor + "&markers=" + markers;
	}
	
}
