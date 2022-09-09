package com.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.SQlite.MySQLiteOpenHelper;
import com.example.myapplication.R;
import com.firstpage.firstpage;

/**
 * 用户登录界面的控件设置
 */
public class login extends AppCompatActivity {
    private MySQLiteOpenHelper MS;
    private Button myBtnLogin;
    private String password;
    private String email;
    private Boolean isSignin;
    private Boolean isSignup;
    private Button signinButton = (Button) findViewById(R.id.SigninButton);
    private Button signupButton = (Button) findViewById(R.id.SignupButton);
    private EditText mailEditText=(EditText) findViewById(R.id.Email);
    private EditText passwordEditText=(EditText) findViewById(R.id.password);

    public login() {
        this.isSignin=false;
        this.isSignup=false;
        MS = new MySQLiteOpenHelper((this));
    }
    //private MySQLiteOpenHelper mySQLiteOpenHelper=new MySQLiteOpenHelper();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //找到控件
        myBtnLogin = findViewById(R.id.SigninButton);

    }

    /**
     * 登录与注册按钮的带年纪效果，点击后将窗口的两个字符串读取进来
     * @param view 按钮的点击操作
     */
     @SuppressLint("NonConstantResourceId")
     public void getInformation(View view) {
         this.email = String.valueOf(this.mailEditText.getText());
         this.password = String.valueOf(this.passwordEditText.getText());
         switch (view.getId()){
             case R.id.SigninButton:
                 this.isSignin=true;
                 this.isSignup=false;
                 Intent intent= new Intent(login.this, firstpage.class);
                 startActivity(intent);
                 break;
             case R.id.SignupButton:
                 this.isSignup=true;
                 this.isSignup=false;
                 break;
         }
     }


    /**
     * 用户输入信息，验证是否正确,使用类的内部变量
     * @return Boolean
     */
    Boolean judge() {

        return false;
    }
}
