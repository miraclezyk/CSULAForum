package com.csula.forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Questions extends ListActivity {
	String res;
	String[] result;
	String ans;
	private Button bt1;
	static Activity Questions;
	TextView tv = null;

	private List<HashMap<String, Object>> mydata = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Questions = this;

		setContentView(R.layout.display_question);
		bt1 = (Button) findViewById(R.id.button1);
		bt1.setOnClickListener(questionListener);

		SharedPreferences pref = getSharedPreferences(
				LoginActivity.MyPREFERENCES, 0);
		String ques = pref.getString("ques", null);
		String name = pref.getString("name", null);

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		if (bundle != null) {
			String addQuestion = bundle.getString("question");
			ques += addQuestion + ":" + name + ",";
			Editor editor = pref.edit();
			editor.putString("ques", ques);
			editor.commit();
			getIntent().removeExtra("question");
		}

		if (ques.isEmpty()) {
			return;
		}
		result = ques.split(",");

		String[] from = { "Text1", "Text2" };
		int[] to = { R.id.textView1, R.id.textView2 };
		mydata = new ArrayList<HashMap<String, Object>>();

		for (String tokens : result) {
			String[] token = tokens.split(":");
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("Text1", token[0]);
			hashMap.put("Text2", "posted by: " + token[1]);
			mydata.add(hashMap);
		}
		setListAdapter(new SimpleAdapter(Questions.this, mydata,
				R.layout.answers, from, to));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
        
		tv = (TextView) v.findViewById(R.id.textView1);
		String que = tv.getText().toString();

		try {
			// SharedPreferences pref =
			// getSharedPreferences(LoginActivity.MyPREFERENCES, 0);
			String link = "http://gaurav.byethost3.com/CSULAForum/getAnswers.php";
			String data = URLEncoder.encode("que", "UTF-8") + "="
					+ URLEncoder.encode(que, "UTF-8");
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			// Read Server Response
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				break;
			}

			res = sb.toString();
			result = res.split(";");
			SharedPreferences pref = getSharedPreferences(
					LoginActivity.MyPREFERENCES, 0);
			Editor editor = pref.edit();
			editor.putString("questionid", result[0]);
			if (result.length > 1) {
				editor.putString("ans", result[1]);
			} else {
				editor.putString("ans", "");
			}
			editor.commit();
			Intent i = new Intent(getApplicationContext(), Answers.class);
			startActivity(i);
			// Toast.makeText(getApplicationContext(), ans,
			// Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private OnClickListener questionListener = new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(Questions.this, AskQuestion.class));
		}
	};

}
