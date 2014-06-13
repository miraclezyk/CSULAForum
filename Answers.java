package com.csula.forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Answers extends ListActivity {
	/*
	 * String res; String[] result; String ans; private Button bt2; static
	 * Activity Answers;
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.display_reply); bt2 = (Button)
	 * findViewById(R.id.button2); bt2.setOnClickListener(replyListener);
	 * 
	 * Answers = this;
	 * 
	 * SharedPreferences pref = getSharedPreferences(
	 * LoginActivity.MyPREFERENCES, 0); String ans = pref.getString("ans",
	 * null);
	 * 
	 * Bundle bundle = new Bundle(); bundle = getIntent().getExtras(); if
	 * (bundle != null) { String addReply = bundle.getString("reply"); ans +=
	 * addReply + ","; Editor editor = pref.edit(); editor.putString("ans",
	 * ans); editor.commit(); }
	 * 
	 * result = ans.split(","); ListView lv = getListView(); setListAdapter(new
	 * MyAdapter(this, R.layout.answers, result)); }
	 * 
	 * private OnClickListener replyListener = new OnClickListener() { public
	 * void onClick(View v) { startActivity(new Intent(Answers.this,
	 * ReplyQuestion.class)); } };
	 * 
	 * class MyAdapter extends ArrayAdapter { private String[] myList; private
	 * Context context;
	 * 
	 * public MyAdapter(Context context, int resource, String[] objects) {
	 * super(context, resource, objects); myList = objects; this.context =
	 * context; }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { final TextView label; if (convertView == null) { LayoutInflater
	 * inflator = ((Activity) context) .getLayoutInflater(); convertView =
	 * inflator.inflate(R.layout.answers, parent, false);
	 * convertView.setTag(R.id.textView1,
	 * convertView.findViewById(R.id.textView1));
	 * 
	 * } // final int x = position; label = (TextView)
	 * convertView.getTag(R.id.textView1); label.setText(myList[position]);
	 * 
	 * return convertView; }
	 * 
	 * }
	 */

	String[] result;
	String ans;
	private Button bt2;
	static Activity Answers;

	private List<HashMap<String, Object>> data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Answers = this;

		setContentView(R.layout.display_reply);
		bt2 = (Button) findViewById(R.id.button2);
		bt2.setOnClickListener(replyListener);

		SharedPreferences pref = getSharedPreferences(
				LoginActivity.MyPREFERENCES, 0);
		String ans = pref.getString("ans", null);
		String name = pref.getString("name", null);

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		if (bundle != null) {
			String addReply = bundle.getString("reply");
			ans += addReply + ":" + name + ",";
			Editor editor = pref.edit();
			editor.putString("ans", ans);
			editor.commit();
			getIntent().removeExtra("reply");
		}

		if (ans.isEmpty()) {
			return;
		}
		result = ans.split(",");

		String[] from = { "Text1", "Text2" };
		int[] to = { R.id.textView1, R.id.textView2 };
		data = new ArrayList<HashMap<String, Object>>();
		
			for (String tokens : result) {
				String[] token = tokens.split(":");
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("Text1", token[0]);
				hashMap.put("Text2", "posted by: " + token[1]);
				data.add(hashMap);
			}
		setListAdapter(new SimpleAdapter(Answers.this, data, R.layout.answers,
				from, to));
	}

	private OnClickListener replyListener = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Answers.this, ReplyQuestion.class));
		}
	};

}
