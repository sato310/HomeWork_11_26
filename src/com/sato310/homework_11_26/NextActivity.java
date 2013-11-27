package com.sato310.homework_11_26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NextActivity extends Activity {

	private ListView listView;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);

		Intent intent = getIntent();
		String data = intent.getStringExtra("data");
		int num = 0;
		if (data.equals("sound")) {
			num = 1;
		} else if (data.equals("photo")) {
			num = 2;
		} else {
			num = 3;
		}

		ContentResolver resolver = getContentResolver();
		switch (num) {
		case 1:
			// Android内部のサウンドの処理
			audioData(resolver);
			break;
		case 2:
			// SDカード内の写真の処理
			imageData(resolver);
			break;
		case 3:
			// 連絡先の処理
			 phoneData(resolver);
			break;
		}
	}

	// Android内部のサウンドの処理
	private void audioData(ContentResolver resolver) {

		String[] audioproject = new String[] { MediaStore.Audio.AudioColumns.TITLE };

		Cursor cursor = resolver.query(
				MediaStore.Audio.Media.INTERNAL_CONTENT_URI, audioproject,
				null, null, null);
		// NullPointerExceptionが起きる可能性がある
		// cursor.getCount() データが１件以上あれば実行される
		if (cursor != null && 0 < cursor.getCount()) {
			// カーソルを1番目に移動させる
			cursor.moveToFirst();
			int nameIndex = cursor.getColumnIndex(audioproject[0]);

			listView = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					NextActivity.this, android.R.layout.simple_list_item_1);

			// do内を一度は必ず通過する、その後while文を実行する
			do {
				// 上で取得した値から文字列を取得
				adapter.add(cursor.getString(nameIndex));
			} while (cursor.moveToNext());

			cursor.close();
			// リストビューに表示
			listView.setAdapter(adapter);

		}
	}

	// SDカード内の写真
	private void imageData(ContentResolver resolver) {
		String[] imageproject = new String[] {
				MediaStore.Images.ImageColumns.TITLE,
				MediaStore.Images.ImageColumns.SIZE };

		Cursor cursor = resolver.query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageproject,
				null, null, null);

		if (cursor != null && 0 < cursor.getCount()) {
			cursor.moveToFirst();
			int titleIndex = cursor.getColumnIndex(imageproject[0]);
			int sizeIndex = cursor.getColumnIndex(imageproject[1]);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			do {
				map.put("title", cursor.getString(titleIndex));
				map.put("size", cursor.getString(sizeIndex));
				list.add(map);
			} while (cursor.moveToNext());
			
			SimpleAdapter adapter = new SimpleAdapter(NextActivity.this, list,
					android.R.layout.simple_list_item_2, new String[] {
							"title", "size" }, new int[] { android.R.id.text1,
							android.R.id.text2 });

			cursor.close();
			listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(adapter);
		}
	}
	
	// 連絡先の処理
	private void phoneData(ContentResolver resolver) {
		String[] phoneProject = new String[]{
		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
		ContactsContract.CommonDataKinds.Phone.NUMBER
		};
		Cursor cursor = resolver.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				phoneProject,
				null,null, null);
		
		if (cursor != null && 0 < cursor.getCount()) {
			cursor.moveToFirst();
			int nameIndex = cursor.getColumnIndex(phoneProject[0]);
			int numberIndex = cursor.getColumnIndex(phoneProject[1]);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();
			do {
				map.put("name", cursor.getString(nameIndex));
				map.put("number", cursor.getString(numberIndex));
				list.add(map);
			} while (cursor.moveToNext());
			
			SimpleAdapter adapter = new SimpleAdapter(NextActivity.this, list,
					android.R.layout.simple_list_item_2, new String[] {
							"name", "number" }, new int[] { android.R.id.text1,
							android.R.id.text2 });

			cursor.close();
			listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(adapter);
				
		
		
		
	}
	}

}
