package org.esisalama.mobile.workdata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView login;
    private TextView name;
    private TextView id;
    private EditText editNumber;
    private Button btnSoumettre;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setEcouteurEvenement();
    }

    private void setEcouteurEvenement() {
        btnSoumettre.setOnClickListener(view -> {
            String idText = editNumber.getText().toString();
            if (idText.isEmpty()) {
                Toast.makeText(this, "L'id ne peux pas être vide", Toast.LENGTH_SHORT).show();
            } else {
                int idInt = Integer.parseInt(idText);
                recupererUser(idInt);
            }
        });
    }

    private void recupererUser(int idInt) {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<User> callBack = userService.getUser(idInt);
        callBack.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    login.setText("Login : " + user.getLogin());
                    name.setText("Name : " + user.getName());
                    id.setText("Id : " + user.getId());
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void initialization() {
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        editNumber = findViewById(R.id.editNumber);
        btnSoumettre = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
    }
}