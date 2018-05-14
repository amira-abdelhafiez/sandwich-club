package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] JsonData = getResources().getStringArray(R.array.sandwich_details);
        length = JsonData.length;
        ArrayList<Sandwich> sandwiches = new ArrayList<>();
        for (String JsonSandwich:JsonData) {
            Sandwich sandwich = null;
            try {
                sandwich = JsonUtils.parseSandwichJson(JsonSandwich);
            }catch (JSONException e){
                e.printStackTrace();
            }
            sandwiches.add(sandwich);
        }
        ArrayAdapter<Sandwich> adapter = new ArrayAdapter<Sandwich>(this,
                android.R.layout.simple_list_item_1, sandwiches);
        CustomAdapter customAdapter = new CustomAdapter(this,sandwiches);
        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });

    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        startActivity(intent);
    }
    private class CustomAdapter extends ArrayAdapter<Sandwich> {
        private Context context;
        private ArrayList<Sandwich> sandwiches = new ArrayList<>();

        public CustomAdapter(Context context , ArrayList<Sandwich> list){
            super(context , 0 , list);
            this.context = context;
            this.sandwiches = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View ListItem = convertView;
            if(ListItem == null){
                ListItem  = LayoutInflater.from(context).inflate(R.layout.custom_lv_row,parent,false);
            }
            Sandwich currentSandwich = sandwiches.get(position);

            ImageView sandwichImageView = (ImageView) ListItem.findViewById(R.id.image_list_iv);
            Picasso.with(context)
                    .load(currentSandwich.getImage())
                    .error(R.drawable.noimage)
                    .into(sandwichImageView);
            TextView nameTextView = (TextView) ListItem.findViewById(R.id.name_list_iv);
            nameTextView.setText(currentSandwich.getMainName());
            TextView originTextView = (TextView) ListItem.findViewById(R.id.origin_list_iv);
            originTextView.setText(currentSandwich.getPlaceOfOrigin());
            return ListItem;
        }
    }
}
