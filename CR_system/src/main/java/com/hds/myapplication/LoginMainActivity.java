package com.hds.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import static com.hds.myapplication.ViewUtil.SHARE_NAME;

/**
 * Created by ouyangshen on 2016/9/24.
 */
public class LoginMainActivity extends AppCompatActivity implements OnClickListener {
	

	private TextView tv_password;
	private EditText et_password;
	private Button btn_change;
	private CheckBox ck_remember;
	private Button btn_login;

	private int mRequestCode = 0;
	private int mType = 0;//选择用户类型
	private boolean bRemember = false;
	private String beforePassword = "666666";
	private String mVerifyCode;
	private SharedPreferences sps;
	private Map<String, Object> mapParam;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		tv_password = (TextView) findViewById(R.id.tv_password);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_change = (Button) findViewById(R.id.btn_change);
		ck_remember = (CheckBox) findViewById(R.id.ck_remember);
		btn_login = (Button) findViewById(R.id.btn_login);

		ck_remember.setOnCheckedChangeListener(new CheckListener());
		et_password.addTextChangedListener(new HideTextWatcher(et_password));
		btn_change.setOnClickListener(this);
		btn_login.setOnClickListener(this);

		ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
				R.layout.item_select, typeArray);
		typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
		Spinner sp_type = (Spinner) findViewById(R.id.sp_type);
		sp_type.setPrompt("请选择用户类型");
		sp_type.setAdapter(typeAdapter);
		sp_type.setSelection(mType);
		sp_type.setOnItemSelectedListener(new TypeSelectedListener());

		beforePassword = getPassword();

	}

	private String[] typeArray = {"管理员", "机组成员"};
	class TypeSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mType = arg2;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
	
	private class CheckListener implements CompoundButton.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.ck_remember) {
				bRemember = isChecked;
			}
		}
	}

	private class HideTextWatcher implements TextWatcher {
		private EditText mView;
		private int mMaxLength;
		private CharSequence mStr;

		public HideTextWatcher(EditText v) {
			super();
			mView = v;
			mMaxLength = ViewUtil.getMaxLength(v);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mStr = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (mStr == null || mStr.length() == 0)
				return;
			if (mStr.length() == 6 && mMaxLength == 6) {
				ViewUtil.hideOneInputMethod(LoginMainActivity.this, mView);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_change){
			Intent intent = new Intent(this , LoginForgetActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("beforePassword" , beforePassword);
			intent.putExtras(bundle);
			startActivity(intent);
		}

		if(v.getId() == R.id.btn_login){
			String inputPassword = et_password.getText().toString();
			if(inputPassword.length()<=0){
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			}else if(beforePassword.equals(inputPassword) ){
				Toast.makeText(this, "login2 "+ inputPassword , Toast.LENGTH_SHORT).show();
				LoginSuccess();
			}else{
				LoginFailed();
			}
		}

	}


	//从修改密码页面返回登录页面，要清空密码的输入框
	@Override
	protected void onRestart() {
		et_password.setText("");
		beforePassword = getPassword();
		super.onRestart();
	}

	private void LoginSuccess(){

		Intent intent = new Intent(this , ManagerActivity.class);
		startActivity(intent);

	}
	private void LoginFailed(){

		Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
		et_password.setText("");
	}

	private String getPassword(){

		sps = getSharedPreferences(SHARE_NAME , MODE_PRIVATE);
		mapParam = (Map<String, Object>) sps.getAll();
		if(mapParam==null || mapParam.size()<=0){
			return beforePassword;
		}else{
			return sps.getString("passWord","");
		}
	}

}
