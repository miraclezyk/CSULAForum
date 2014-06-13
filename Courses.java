package com.csula.forum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.annotation.SuppressLint;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Courses extends ListActivity {
	String res;
	String[] result;
	String questions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			SharedPreferences pref = getSharedPreferences(
					LoginActivity.MyPREFERENCES, 0);
			String maj = pref.getString("major", null);
			String link = "http://gaurav.byethost3.com/CSULAForum/getCourses.php";//"http://10.0.2.2:80/CSULAForum/getCourses.php";
			String data = URLEncoder.encode("major", "UTF-8") + "="
					+ URLEncoder.encode(maj, "UTF-8");
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
			result = res.split(",");

			// Toast.makeText(getApplicationContext(), res,
			// Toast.LENGTH_LONG).show();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ListView lv = getListView();
		setListAdapter(new MyAdapter(this, R.layout.courses, result));
		// addListenerOnRatingBar();

	}

	class MyAdapter extends ArrayAdapter {
		private String[] myList;
		private Context context;
		private RatingBar ratingBar;

		public MyAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			myList = objects;
			this.context = context;
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TextView label;
			// reuse converView if you can to speed up scrolling on listview
			if (convertView == null) {
				// inflate the listview
				LayoutInflater inflator = ((Activity) context)
						.getLayoutInflater();
				
				convertView = inflator.inflate(R.layout.courses, parent, false);
				//convertView.setBackgroundResource(R.color.grey);
				convertView.setTag(R.id.textView1,convertView.findViewById(R.id.textView1));

			}
			if(position % 2 == 0){
				convertView.setBackgroundColor(R.color.white);
			}
			else{
				convertView.setBackgroundColor(R.color.yellow);
			}
			
			final int x = position;
			label = (TextView) convertView.getTag(R.id.textView1);
			label.setText(myList[position]);
			    //label.setBackgroundResource(R.color.white);}
			label.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String coursename = label.getText().toString();
					// Toast.makeText(getApplicationContext(), coursename,
					// Toast.LENGTH_LONG).show();
					try {
						SharedPreferences pref = getSharedPreferences(
								LoginActivity.MyPREFERENCES, 0);
						Editor editor = pref.edit();

						String link ="http://gaurav.byethost3.com/CSULAForum/getQuestions.php";// "http://10.0.2.2:80/CSULAForum/getQuestions.php";
						String data = URLEncoder.encode("coursename", "UTF-8")
								+ "=" + URLEncoder.encode(coursename, "UTF-8");
						URL url = new URL(link);
						URLConnection conn = url.openConnection();
						conn.setDoOutput(true);
						OutputStreamWriter wr = new OutputStreamWriter(conn
								.getOutputStream());
						wr.write(data);
						wr.flush();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(conn.getInputStream()));
						StringBuilder sb = new StringBuilder();
						String line = null;
						// Read Server Response
						while ((line = reader.readLine()) != null) {
							sb.append(line);
							break;
						}

						questions = sb.toString();
						result = questions.split(";");

						editor.putString("courseid", result[0]);
						if (result.length > 1) {
							editor.putString("ques", result[1]);
						} else {
							editor.putString("ques", "");
						}
						editor.commit();

						Intent i = new Intent(getApplicationContext(),
								Questions.class);
						startActivity(i);
						// Toast.makeText(getApplicationContext(), res,
						// Toast.LENGTH_LONG).show();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			return convertView;
		}

	}

}
