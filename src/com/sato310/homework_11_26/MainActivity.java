package com.sato310.homework_11_26;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Android内部のサウンド
		Button soundButon = (Button) findViewById(R.id.button1);
		soundButon.setOnClickListener(this);
		// SDカード内の写真
		Button photoButton = (Button) findViewById(R.id.button2);
		photoButton.setOnClickListener(this);
		// 連絡先
		Button phoneButton = (Button) findViewById(R.id.button3);
		phoneButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			intent = new Intent(this, NextActivity.class);
			intent.putExtra("data", "sound");
			startActivity(intent);
			break;

		case R.id.button2:
			intent = new Intent(this, NextActivity.class);
			intent.putExtra("data", "photo");
			startActivity(intent);
			break;

		case R.id.button3:
			intent = new Intent(this, NextActivity.class);
			intent.putExtra("data", "phone");
			startActivity(intent);
			break;
		}
	}
}
