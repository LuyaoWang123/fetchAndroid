package lw.intern.fetch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * The MainActivity class represents the main activity of the Android application.
 * It displays a button for data retrieval and a RecyclerView to show the fetched data.
 */
public class MainActivity extends AppCompatActivity {
    Button btnGetData; // a button to retrieve data
    RecyclerView itemView; // a list to show data to user
    RecyclerAdapter adapter; // an adapter to manage the list shown to user
    // data list, singleton--avoid being recreated, multiple activities
    // view model--independent from main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize view model
        MainViewModel viewModel=new ViewModelProvider(this).get(MainViewModel.class);

        // assign values to each control on the lay out
        btnGetData = findViewById(R.id.btn_getData);
        itemView = findViewById(R.id.lv_itemReport);
        itemView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(new ArrayList<>());
        itemView.setAdapter(adapter);

        // using view model to observe if there is an error, and report error message
        // to user
        viewModel.getErrorLiveData().observe(this, error-> Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show());

        // using view model to observe if:
        // 1. data changes in view model
        // 2. lifecycle-aware
        viewModel.getItems().observe(this, items->{
            adapter.updateItems(items);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();
        });

        // click listeners for each button
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        btnGetData.setOnClickListener(v-> viewModel.fetchData(url, new DataFetcher(this)));
    }
}