package com.hds.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import static com.hds.myapplication.ViewUtil.SHARE_NAME;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class LoginForgetActivity extends AppCompatActivity implements OnClickListener {

	private EditText et_password_first;
	private EditText et_password_second;
	private EditText et_verifycode;
	private String defaultPassword;
	private String mPhone;
	private SharedPreferences sps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_forget);
		et_password_first = (EditText) findViewById(R.id.et_password_first);
		et_password_second = (EditText) findViewById(R.id.et_password_second);
		et_verifycode = (EditText) findViewById(R.id.et_verifycode);
		findViewById(R.id.btn_confirm).setOnClickListener(this);
		mPhone = getIntent().getStringExtra("phone");
		sps = getSharedPreferences(SHARE_NAME , MODE_PRIVATE);

		Bundle bundle = getIntent().getExtras();
		defaultPassword = bundle.getString("beforePassword");
//		Toast.makeText(this, "原密码是"+ defaultPassword , Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
			String password_first = et_password_first.getText().toString();
			String password_second = et_password_second.getText().toString();


			if (password_first==null || password_first.length()<6 ||
					password_second==null || password_second.length()<6) {
				Toast.makeText(this, "请输入正确的新密码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password_first.equals(password_second) != true) {
				Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
				return;
			}
			if (et_verifycode.getText().toString().equals(defaultPassword) != true) {
				Toast.makeText(this, "原密码错误"+defaultPassword, Toast.LENGTH_SHORT).show();
				return;
			} else {

				SharedPreferences.Editor editor = sps.edit();
				editor.putString("passWord", password_first);
				editor.commit();
				Toast.makeText(this, "密码修改成功" , Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}


}
