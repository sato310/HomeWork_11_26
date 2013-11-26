package com.sato310.homework_11_26;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NextActivity extends Activity {
	
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
//			imageData();
			break;			
		case 3:
			// 連絡先の処理
//			phoneData();
			break;
		}
	}

	// Android内部のサウンドの処理
	private void audioData(ContentResolver resolver) {

		String[] audioproject = new String[]{
				MediaStore.Audio.AudioColumns.TITLE
		};
		
		Cursor cursor = resolver.query(
				MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
				audioproject,
				null,
				null,
				null);
		// NullPointerExceptionが起きる可能性がある
		// cursor.getCount() データが１件以上あれば実行される
		if (cursor != null && 0 < cursor.getCount()) {
			// カーソルを1番目に移動させる
			cursor.moveToFirst();
			int nameIndex = cursor.getColumnIndex(audioproject[0]);

			// ListView表示用のArrayAdapterを準備
			ListView liView = (ListView)findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(NextActivity.this, android.R.layout.simple_list_item_1);

			// do内を一度は必ず通過する、その後while文を実行する
			do {
				//上で取得した値から文字列を取得
				adapter.add(cursor.getString(nameIndex));
			} while (cursor.moveToNext());
			
			cursor.close();
			// リストビューに表示
			liView.setAdapter(adapter);
			
		}
				
		
	}
}
