package com.example.readwritefile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String FILE_NAME = "data.txt";

    private EditText etText;
    private Button bSave;
    private Button bLoad;
    private Button bDelete;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etText = findViewById(R.id.et_text);
        bSave = findViewById(R.id.b_save);
        bLoad = findViewById(R.id.b_load);
        bDelete = findViewById(R.id.b_delete);
        tvResult = findViewById(R.id.tv_result);

        bSave.setOnClickListener(this);
        bLoad.setOnClickListener(this);
        bDelete.setOnClickListener(this);

        // Все файлы в директории
        for (String file : fileList()) {
            System.out.println(file);
        }
        ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_save: onSaveClick();
                break;
            case R.id.b_load: onLoadClick();
                break;
            case R.id.b_delete: onDeleteClick();
                break;
        }
    }

    private void onDeleteClick() {
        boolean isDelete = deleteFile(FILE_NAME);
        if (isDelete)
            showToast("Удаление прошло успешно");
        else
            showToast("Ошибка удаление файла");
    }

    private void onLoadClick() {

        try {
            FileInputStream in  = openFileInput(FILE_NAME);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            String s = new String(buffer);
            tvResult.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Файл не найден");
        }

    }


    private void onSaveClick(){
        String text = etText.getText().toString();
        try {
            FileOutputStream out = openFileOutput(FILE_NAME, MODE_APPEND);
            out.write(text.getBytes());
            showToast("Файл добавлен");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Ошибка при записа файла");
        }
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
