package com.example.onlineshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;


public class Dashboard extends AppCompatActivity implements ItemsAdapter.OnItemListener {

    TextView displayUsername;
    Button btnLogout;
    String dUsername,iName,iPrice,iDesc;
    private RecyclerView recyclerView;
    List<Items> itemsList=new ArrayList<>();
    String item;
    Integer iImageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        displayUsername=findViewById(R.id.getUsername);
        btnLogout=findViewById(R.id.logout);

        dUsername=getIntent().getStringExtra("USERNAME");
        displayUsername.setText(dUsername);

        recyclerView=findViewById(R.id.recyclerView);
        getItems();
        recyclerView.setAdapter(new ItemsAdapter(itemsList,getApplicationContext(),this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent logout=new Intent(Dashboard.this,MainActivity.class);
                startActivity(logout);
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Items items1 = itemsList.get(position);
        iName=items1.getItemName();
        iPrice=itemsList.get(position).getItemPrice();
        iDesc=itemsList.get(position).getItemDesc();
        iImageName=itemsList.get(position).getItemImage();
        Intent openDescription=new Intent(Dashboard.this,DisplayClickedItem.class);
        openDescription.putExtra("name",iName);
        openDescription.putExtra("price",iPrice);
        openDescription.putExtra("desc",iDesc);
        openDescription.putExtra("image",iImageName);
        startActivity(openDescription);
    }
    private  void getItems()
    {
        try {
            FileInputStream fileInputStream=openFileInput("items.txt");
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            while ((item=bufferedReader.readLine())!=null)
            {
                String[] itemDetails=item.split("->");
                String itemImage=itemDetails[2];
                int res=getResources().getIdentifier(itemImage,"drawable",getPackageName());
                itemsList.add(new Items(itemDetails[0],itemDetails[1],res,itemDetails[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
