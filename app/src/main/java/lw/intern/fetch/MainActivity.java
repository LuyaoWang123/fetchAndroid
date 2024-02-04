package lw.intern.fetch;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The MainActivity class represents the main activity of the Android application.
 * It displays a button for data retrieval and a RecyclerView to show the fetched data.
 */
public class MainActivity extends AppCompatActivity {
    Button btnGetData; // a button to retrieve data
    RecyclerView itemView; // a list to show data to user
    RecyclerAdapter adapter; // an adapter to manage the list shown to user
    List<ItemInterface> itemList; // data list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign values to each control on the lay out
        btnGetData = findViewById(R.id.btn_getData);
        itemView = findViewById(R.id.lv_itemReport);
        itemView.setLayoutManager(new LinearLayoutManager(this));
        DataFetcher dataFetcher = new DataFetcher(this);

        // click listeners for each button
        String url="https://fetch-hiring.s3.amazonaws.com/hiring.json";

        btnGetData.setOnClickListener(v -> {
            dataFetcher.fetchData(url,new DataFetcher.DataResponseListener() {
            @Override
            public void onDataFetched(List<ItemInterface> data) {
                itemList = data;
                adapter.updateItems(itemList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(MainActivity.this, "Sorry, something wrong with the data source", Toast.LENGTH_SHORT).show();
            }
        });

        });
        // initialize the adapter with an empty list
        itemList = new ArrayList<>();
        adapter = new RecyclerAdapter(itemList);
        itemView.setAdapter(adapter);
    }
}