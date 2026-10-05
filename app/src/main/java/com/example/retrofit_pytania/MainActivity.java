package com.example.retrofit_pytania;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button buttonNastepne;
    RadioButton radioButtonA, radioButtonB, radioButtonC;
    RadioGroup radioGroupPytanie;
    TextView textViewtresc;
    List<Pytanie> listaPytan;

    int index = 0;
    int ostatecznyWynik = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNastepne = findViewById(R.id.button);
        radioButtonA = findViewById(R.id.radioButton1);
        radioButtonB = findViewById(R.id.radioButton2);
        radioButtonC = findViewById(R.id.radioButton3);
        textViewtresc = findViewById(R.id.textViewPytanie);
        radioGroupPytanie = findViewById(R.id.radioGroupPytanie);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/mario08031999-ux/retrofit_pytania_matematyka/main/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<List<Pytanie>> call = jsonPlaceHolder.getPytania();
        call.enqueue(
                new Callback<List<Pytanie>>() {
                    @Override
                    public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        listaPytan = response.body();
//                        textViewtresc.setText(listaPytan.get(0).getTrescPytania());
                        wypiszPytania(0);
                    }

                    @Override
                    public void onFailure(Call<List<Pytanie>> call, Throwable t) {

                    }
                }

        );
        buttonNastepne.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (index < listaPytan.size()) {

                            if (radioButtonA.isChecked()) {
                                if (listaPytan.get(index).getPoprawna() == 0) {
                                    Toast.makeText(MainActivity.this, "POPRAWNA", Toast.LENGTH_SHORT).show();
                                    ostatecznyWynik++;
                                } else {
                                    Toast.makeText(MainActivity.this, "NIE POPRAWNA", Toast.LENGTH_SHORT).show();
                                }
                            } else if (radioButtonB.isChecked()) {
                                if (listaPytan.get(index).getPoprawna() == 1) {
                                    Toast.makeText(MainActivity.this, "POPRAWNA", Toast.LENGTH_SHORT).show();
                                    ostatecznyWynik++;
                                } else {
                                    Toast.makeText(MainActivity.this, "NIE POPRAWNA", Toast.LENGTH_SHORT).show();
                                }
                            } else if (radioButtonC.isChecked()) {
                                if (listaPytan.get(index).getPoprawna() == 2) {
                                    Toast.makeText(MainActivity.this, "POPRAWNA", Toast.LENGTH_SHORT).show();
                                    ostatecznyWynik++;
                                } else {
                                    Toast.makeText(MainActivity.this, "NIE POPRAWNA", Toast.LENGTH_SHORT).show();
                                }
                            }
                            radioGroupPytanie.clearCheck();
                            boolean ekranKoncowy = false;
                            if (index < listaPytan.size() - 1) {
                                index++;
                                wypiszPytania(index);
                            } else {
                                ekranKoncowy = true;
                            }
                            if (ekranKoncowy) {
                                textViewtresc.setText("Wynik: " + ostatecznyWynik);
                            }
                        }
                    }
                }
        );


    }

    private void wypiszPytania(int nrPytania){
        radioGroupPytanie.clearCheck();
        textViewtresc.setText(listaPytan.get(nrPytania).getTrescPytania());
        radioButtonA.setText(listaPytan.get(nrPytania).getOdpA());
        radioButtonB.setText(listaPytan.get(nrPytania).getOdpB());
        radioButtonC.setText(listaPytan.get(nrPytania).getOdpC());

    }

}