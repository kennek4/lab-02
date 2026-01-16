package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    int currCityCount;
    int currCityIndex;

    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;


    private void setupButtons() {
        Button createButton = findViewById(R.id.create_button);
        Button deleteButton = findViewById(R.id.delete_button);

        createButton.setText(R.string.create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText cityEdit = findViewById(R.id.create_city_edit);
                Button submitButton = findViewById(R.id.submit_button);
                Button cancelButton = findViewById(R.id.cancel_button);

                boolean isSubmitVisible = submitButton.getVisibility() == View.VISIBLE;
                if (isSubmitVisible) {
                    cityEdit.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.GONE);
                } else {
                    cityEdit.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.VISIBLE);
                }

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newCityName = cityEdit.getText().toString();
                        if (newCityName.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No city name was provided!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dataList.add(newCityName);
                        cityEdit.setText("");
                        cityList.setAdapter(cityAdapter);
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cityEdit.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                    }
                });
            }
        });

        deleteButton.setText(R.string.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currCityCount <= 0) {
                    Toast.makeText(MainActivity.this, "No cities in list!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (currCityIndex == -1 || (currCityIndex < 0 || currCityIndex > dataList.size())) {
                    Toast.makeText(MainActivity.this, "No city was selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataList.remove(currCityIndex);
                currCityIndex=-1;

                cityList.setAdapter(cityAdapter);
            }
        });
    }

    private void setupCityListView() {
        cityList = findViewById(R.id.city_list);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currCityIndex = position;
                Toast.makeText(MainActivity.this, "Selected: " + dataList.get(currCityIndex), Toast.LENGTH_SHORT).show();
            }
        });

        String[] DEFAULT_CITIES = {"Edmonton", "Vancouver", "Toronto", "Tokyo", "Seoul", "Manila"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(DEFAULT_CITIES));

        currCityIndex = -1;
        currCityCount = dataList.size();

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupButtons();
        setupCityListView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
