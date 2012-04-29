package com.debugi.app.anyvblog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.debugi.app.anyvblog.adapter.UserSelectAdapter;
import com.debugi.app.anyvblog.asynctask.SendAsyncTask;
import com.debugi.app.anyvblog.model.SendData;
import com.debugi.app.anyvblog.model.UserAdapter;
import com.debugi.app.anyvblog.model.tencent.GetInviteList4Tencent;
import com.debugi.app.anyvblog.model.tencent.InviteData4Tencent;
import com.debugi.app.anyvblog.model.tencent.SendGetInviteList4Tencent;
import com.debugi.app.anyvblog.utils.Config;
import com.debugi.app.anyvblog.utils.Constants;
import com.debugi.open.oauth.model.HttpType;
import com.debugi.open.oauth.model.Response;
import com.google.gson.Gson;

public class UserSelectActivity extends Activity {
	private static final String TAG = "UserSelectActivity";
	private AutoCompleteTextView search_input;
	private Button search;
	final Activity activity = this;
	private SendData sendData;
	private List<UserAdapter> users;
	private ListView userlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userselect);
		
		initViews();
		getInviteUsers();
		
		List<Map<String, Object>> cdata = new ArrayList<Map<String,Object>>();
		for (UserAdapter user : users) {
			cdata.add(user.conver()); 
		}
		
		search_input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				Config.debug(TAG, s.toString());
				userlist.setFilterText(s.toString());
			}
		});
		
		UserSelectAdapter adapter = new UserSelectAdapter(activity, cdata, R.layout.userselect_item);
		userlist.setAdapter(adapter);
	}
	private void initViews() {
		search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		search = (Button) findViewById(R.id.search);
		userlist = (ListView) findViewById(R.id.userlist);
	}
	
	private void getInviteUsers() {
		sendData = new SendGetInviteList4Tencent();
		users = new ArrayList<UserAdapter>();
		Response response = null;
		try {
			response = new SendAsyncTask(activity, sendData, Constants.Tencent.GET_INVITE_LIST, HttpType.GET, Config.currentUser, null).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (response != null && response.getCode() == 200) {
			Gson gson = new Gson();
			GetInviteList4Tencent list4Tencent = gson.fromJson(response.getBody(), GetInviteList4Tencent.class);
			if (list4Tencent != null && list4Tencent.getRet() == 0) {
				Config.debug(TAG ,"互听总数:" + list4Tencent.getData().getTotalnum());
				for (InviteData4Tencent data4Tencent : list4Tencent.getData().getInfo()) {
					users.add(data4Tencent.conver2User());
				}
			}
		} else {
			Config.debug(TAG, response.getCode() + "  " + response.getBody());
		}
	}
}
