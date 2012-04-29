package com.debugi.app.anyvblog;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debugi.app.anyvblog.asynctask.SendAsyncTask;
import com.debugi.app.anyvblog.asynctask.SendNoUpdateAsyncTask;
import com.debugi.app.anyvblog.exception.NormalException;
import com.debugi.app.anyvblog.exception.ResponseException;
import com.debugi.app.anyvblog.location.StaticMap;
import com.debugi.app.anyvblog.model.Counts;
import com.debugi.app.anyvblog.model.DataAdapter;
import com.debugi.app.anyvblog.model.SendCounts;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.netease.SendGetOne4Netease;
import com.debugi.app.anyvblog.model.netease.Status4Netease;
import com.debugi.app.anyvblog.model.sina.Counts4Sina;
import com.debugi.app.anyvblog.model.sina.SendGetOne4Sina;
import com.debugi.app.anyvblog.model.sina.Status4Sina;
import com.debugi.app.anyvblog.model.sohu.Counts4Sohu;
import com.debugi.app.anyvblog.model.sohu.SendGetOne4Sohu;
import com.debugi.app.anyvblog.model.sohu.Status4Sohu;
import com.debugi.app.anyvblog.model.tencent.GetOneData4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendGetOne4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.app.anyvblog.utils.MapsUtils;
import com.debugi.app.anyvblog.utils.MyHandler;
import com.debugi.app.anyvblog.utils.TextUtils;
import com.debugi.app.anyvblog.widget.AsyncImageView;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ShowOneDataActivity extends Activity implements OnClickListener {
	private static final String TAG = "ShowOneDataActivity";
	/** Called when the activity is first created. */
	private final Activity activity = this;
	private AsyncImageView show_one_icon;
	private TextView show_one_nick;
	private TextView show_one_name;
	private TextView show_one_status;
	private AsyncImageView show_one_image;
	private WebView map;
	private Button btn_back;
	private Button btn_forward;
	private Button btn_comment;
	private Button show_one_btn_forward;
	private Button show_one_btn_comment;
	private Button btn_more;
	private Button btn_home;
	private TextView source_show_one_nick;
	private TextView source_show_one_status;
	private AsyncImageView source_show_one_image;
	private SendData sendData;
	private String id;
	private Response response;
	private View source_show_one;
	private UserAdapter currentUser;
	private TextView source_show_one_from;
	private TextView show_one_from;
	private TextView show_one_time;
	private DataAdapter adapter;
	private LinearLayout progressbar;
	private String url;
	private AsyncTask<Void, Void, Response> asyncTask;
	private AsyncTask<Void, Void, Response> asyncTask4Counts;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_one_data);

		initViews();
		Intent intent = getIntent();
		id = intent.getExtras().getString("id");
		currentUser = Config.currentUser;
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case MyHandler.Handler_GetData:
					try {
						response = asyncTask.get();
						if (response == null || response.getCode() != 200) {
							if (response == null) {
								return;
							}
							String tip = new ResponseException(response,
									currentUser.getSp()).getMessage();
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						adapter = conver();
						show();
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				case MyHandler.Handler_GetData_Without_Update:
					try {
						Response count_response = asyncTask4Counts.get();
						if (count_response == null
								|| count_response.getCode() != 200) {
							if (count_response == null) {
								return;
							}
							String tip = new ResponseException(count_response,
									currentUser.getSp()).getMessage();
							Toast.makeText(activity, tip, Toast.LENGTH_SHORT)
									.show();
							return;
						}
						parseCounts(count_response);
					} catch (Exception e) {
						throw new NormalException(e.getMessage());
					}
					break;
				default:
					break;
				}
			}
		};
		getData();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Config.debug(TAG, "on newIntent");
		id = intent.getExtras().getString("id");
		currentUser = Config.currentUser;

		getData();
	}

	private void getData() {
		progressbar.setVisibility(View.VISIBLE);
		createSendData(currentUser, id);
		asyncTask = new SendAsyncTask(activity, sendData, url, HttpType.GET,
				currentUser, handler).execute();
		if (currentUser.getSp() == Constants.SP_SINA) {
			sendData = new SendCounts(id);
			url = Constants.Sina.COUNTS;
			asyncTask4Counts = new SendNoUpdateAsyncTask(activity, sendData,
					url, HttpType.GET, currentUser, handler).execute();
		}
		if (currentUser.getSp() == Constants.SP_SOHU) {
			sendData = new SendCounts(id);
			url = Constants.Sohu.COUNTS;
			asyncTask4Counts = new SendNoUpdateAsyncTask(activity, sendData,
					url, HttpType.GET, currentUser, handler).execute();
		}

	}

	/**
	 * 创建发送数据senddata
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	private void createSendData(UserAdapter user, String id) {
		switch (user.getSp()) {
		case Constants.SP_TENCENT:
			sendData = new SendGetOne4Tencent(id);
			url = Constants.Tencent.GET_ONE;
			break;
		case Constants.SP_SINA:
			sendData = new SendGetOne4Sina(id);
			url = String.format(Constants.Sina.GET_ONE, id);
			break;
		case Constants.SP_NETEASE:
			sendData = new SendGetOne4Netease(id);
			url = String.format(Constants.NetEase.GET_ONE, id);
			break;
		case Constants.SP_SOHU:
			sendData = new SendGetOne4Sohu(id);
			url = String.format(Constants.Sohu.GET_ONE, id);
			break;
		default:
			break;
		}
	}

	private void initViews() {
		show_one_icon = (AsyncImageView) findViewById(R.id.show_one_icon);
		show_one_nick = (TextView) findViewById(R.id.show_one_nick);
		show_one_name = (TextView) findViewById(R.id.show_one_name);
		show_one_status = (TextView) findViewById(R.id.show_one_status);
		show_one_image = (AsyncImageView) findViewById(R.id.show_one_image);
		map = (WebView) findViewById(R.id.map);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_forward = (Button) findViewById(R.id.btn_forward);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		btn_more = (Button) findViewById(R.id.btn_more);
		btn_home = (Button) findViewById(R.id.btn_home);
		source_show_one_nick = (TextView) findViewById(R.id.source_show_one_nick);
		source_show_one_image = (AsyncImageView) findViewById(R.id.source_show_one_image);
		source_show_one_status = (TextView) findViewById(R.id.source_show_one_status);
		source_show_one = findViewById(R.id.source_show_one);
		source_show_one_from = (TextView) findViewById(R.id.source_show_one_from);
		show_one_from = (TextView) findViewById(R.id.show_one_from);
		show_one_time = (TextView) findViewById(R.id.show_one_time);
		show_one_btn_forward = (Button) findViewById(R.id.show_one_btn_forward);
		show_one_btn_comment = (Button) findViewById(R.id.show_one_btn_comment);
		progressbar = (LinearLayout) findViewById(R.id.progressbar);
		show_one_icon.setOnClickListener(this);
		source_show_one_nick.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		btn_comment.setOnClickListener(this);
		btn_forward.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		map.getSettings().setJavaScriptEnabled(true);
		show_one_btn_comment.setOnClickListener(this);
		show_one_btn_forward.setOnClickListener(this);
		// 实现页面默认加载时适应屏幕（缩放）
		// map.getSettings().setLoadWithOverviewMode(true);
	}

	private DataAdapter conver() {
		DataAdapter data = null;
		try {
			Config.debug(TAG, response.getBody());
			Gson gson = new Gson();
			switch (currentUser.getSp()) {
			case Constants.SP_TENCENT:
				GetOneData4Tencent data4Tencent = gson.fromJson(
						response.getBody(), GetOneData4Tencent.class);
				if (data4Tencent.getRet() == 0) {
					data = data4Tencent.getData().conver2Data();
				}
				break;
			case Constants.SP_SINA:
				Status4Sina sina = gson.fromJson(response.getBody(),
						Status4Sina.class);
				data = sina.conver2Data();
				break;
			case Constants.SP_NETEASE:
				Status4Netease netease = gson.fromJson(response.getBody(),
						Status4Netease.class);
				data = netease.conver2Data();
				break;
			case Constants.SP_SOHU:
				Status4Sohu sohu = gson.fromJson(response.getBody(),
						Status4Sohu.class);
				Config.debug(TAG, sohu);
				data = sohu.conver2Data();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			throw new NormalException(e.getMessage());
		}
		return data;
	}

	private void show() {
		progressbar.setVisibility(View.GONE);
		if (adapter != null) {
			show_one_nick.setText(adapter.getNick());
			show_one_name.setText(adapter.getName());
			show_one_status.setText(TextUtils.parse(adapter.getStatus(),
					activity));
			/**
			 * 头像
			 */
			if (adapter.getHead_url() != null
					&& URLUtil.isNetworkUrl(adapter.getHead_url())) {
				show_one_icon.asyncLoadBitmapFromUrl(adapter.getHead_url(),
						Config.cachePath + "head_" + adapter.getUser_id()
								+ ".jpg");
			}
			/**
			 * 图片资源
			 */
			Config.debug(TAG, "imageUrl=" + adapter.getImage_m());
			Config.debug(TAG, adapter);
			if (adapter.getImage_m() != null) {
				show_one_image.setVisibility(View.VISIBLE);
				show_one_image.asyncLoadBitmapFromUrl(adapter.getImage_m(),
						Config.cachePath + "m_" + adapter.getStatus_id()
								+ ".jpg");
				// show_one_image.setTag(adapter.getImage_url());
				show_one_image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(activity,
								ImageActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("url", adapter.getImage_o());
						intent.putExtras(bundle);
						activity.startActivity(intent);

					}
				});
			} else {
				show_one_image.setVisibility(View.GONE);
			}
			if (adapter.getLat() != 0 && adapter.getLng() != 0) {
				Config.debug(TAG, "loading map");
				StaticMap staticMap = new StaticMap(adapter.getLat(),
						adapter.getLng(), 12, null);
				String requestUrl = MapsUtils.STATIC_MAP
						+ URLEncoder.encode(staticMap.toString());
				Config.debug(TAG, requestUrl);
				map.setVisibility(View.VISIBLE);
				String loadData = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><script>function onreadystatechange(){if (document.readyState==\"complete\") {document.getElementById(\"tips\").innerHTML = \"\";}}</script></head><body onload=\"onreadystatechange()\"><div id=\"tips\">地图载入中……请稍候!</div><img src=\""
						+ requestUrl + "\" /></body></html>";
				map.loadData(loadData, "text/html", "utf-8");
			} else {
				map.setVisibility(View.GONE);
			}
			if (adapter.isSource()) {
				source_show_one.setVisibility(View.VISIBLE);
				source_show_one_nick.setText(adapter.getSource_nick());
				source_show_one_status.setText(TextUtils.parse(
						adapter.getSource_status(), activity));
				source_show_one_from.setText(adapter.getSource_from());
				if (adapter.getSource_image_m() != null) {
					source_show_one_image.setVisibility(View.VISIBLE);
					source_show_one_image.asyncLoadBitmapFromUrl(
							adapter.getSource_image_m(), Config.cachePath
									+ "m_" + adapter.getStatus_id() + ".jpg");
					source_show_one_image
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(activity,
											ImageActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("url",
											adapter.getSource_image_o());
									intent.putExtras(bundle);
									activity.startActivity(intent);

								}
							});
				} else {
					source_show_one_image.setVisibility(View.GONE);
				}
			} else {
				source_show_one.setVisibility(View.GONE);
			}
			show_one_from.setText(adapter.getFrom());
			show_one_time.setText(adapter.getTime());
			if ("0".equals(show_one_btn_comment.getText().toString())
					&& "0".equals(show_one_btn_forward.getText().toString())) {
				show_one_btn_comment.setText(adapter.getComment_count() + "");
				show_one_btn_forward.setText(adapter.getForward_count() + "");
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (adapter == null) {
			Config.debug(TAG, "adaptor is null");
			return;
		}
		Intent intent = null;
		switch (v.getId()) {
		case R.id.show_one_icon:
			intent = new Intent(activity, UserInfoActivity.class);
			intent.putExtra("user_id", adapter.getUser_id());
			activity.startActivity(intent);
			break;
		case R.id.source_show_one_nick:
			intent = new Intent(activity, UserInfoActivity.class);
			intent.putExtra("user_id", adapter.getSource_user_id());
			activity.startActivity(intent);
			break;
		case R.id.btn_back:
			Config.debug(TAG, "click back");
			activity.finish();
			break;
		case R.id.btn_home:
			intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;
		case R.id.btn_forward:
			intent = new Intent(activity, ForwardActivity.class);
			intent.putExtra("id", adapter.getStatus_id());
			if (adapter.isSource()) {
				intent.putExtra("status", "||@" + adapter.getName() + ":"
						+ adapter.getStatus());
			}
			startActivity(intent);
			break;
		case R.id.btn_comment:
			intent = new Intent(activity, CommentActivity.class);
			intent.putExtra("id", adapter.getStatus_id());
			Config.debug(TAG, "comment at " + adapter.getStatus_id());
			intent.putExtra("type", Constants.COMMENT_TYPE_COMMENT);
			if (adapter.isSource()) {
				intent.putExtra("status", "||@" + adapter.getName() + ":"
						+ adapter.getStatus());
			}
			startActivity(intent);
			break;
		case R.id.show_one_btn_forward:
			intent = new Intent(activity, ForwardActivity.class);
			intent.putExtra("id", adapter.getStatus_id());
			if (adapter.isSource()) {
				intent.putExtra("status", "||@" + adapter.getName() + ":"
						+ adapter.getStatus());
			}
			startActivity(intent);
			break;
		case R.id.show_one_btn_comment:
			intent = new Intent(activity, CommentActivity.class);
			intent.putExtra("id", adapter.getStatus_id());
			Config.debug(TAG, "comment at " + adapter.getStatus_id());
			intent.putExtra("type", Constants.COMMENT_TYPE_COMMENT);
			if (adapter.isSource()) {
				intent.putExtra("status", "||@" + adapter.getName() + ":"
						+ adapter.getStatus());
			}
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private void parseCounts(Response response) {
		if (response == null || response.getCode() != 200) {
			if (response != null) {
				Config.debug(TAG, response.getCode() + " " + response.getBody());
			}
			return;
		}
		Config.debug(TAG, response.getBody());
		Gson gson = new Gson();
		List<Counts> counts = new ArrayList<Counts>();
		switch (currentUser.getSp()) {
		case Constants.SP_SINA:
			Type typeSina = new TypeToken<List<Counts4Sina>>() {
			}.getType();
			List<Counts4Sina> counts4Sinas = gson.fromJson(response.getBody(),
					typeSina);
			for (Counts4Sina counts4Sina : counts4Sinas) {
				counts.add(counts4Sina.conver());
			}
			break;
		case Constants.SP_SOHU:
			Type typeSohu = new TypeToken<List<Counts4Sohu>>() {
			}.getType();
			List<Counts4Sohu> counts4Sohus = gson.fromJson(response.getBody(),
					typeSohu);
			for (Counts4Sohu counts4Sohu : counts4Sohus) {
				counts.add(counts4Sohu.conver());
			}
			break;
		default:
			break;
		}
		if (counts.size() > 0) {
			Counts count = counts.get(0);
			if ("0".equals(show_one_btn_comment.getText().toString())
					&& "0".equals(show_one_btn_forward.getText().toString())) {
				show_one_btn_comment.setText(count.getCount_c() + "");
				show_one_btn_forward.setText(count.getCount_f() + "");
			}
		}
	}
}