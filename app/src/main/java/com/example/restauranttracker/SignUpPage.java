package com.example.restauranttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUpPage extends AppCompatActivity {
    Button register_button;
    EditText password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        register_button = (Button) findViewById(R.id.register);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity = new UserEntity();
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(username.getText().toString());
                if (validateInput(userEntity)) {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all fields!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(SignUpPage.this, WelcomePage.class);

                startActivity(intent);

            }
        });
    }
    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getName().isEmpty() ||
        userEntity.getPassword().isEmpty() ||
        userEntity.getName().isEmpty()) {
    return false;
        }
        return true;
    }
}