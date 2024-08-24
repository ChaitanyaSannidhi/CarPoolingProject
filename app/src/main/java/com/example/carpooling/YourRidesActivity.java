package com.example.carpooling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class YourRidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_your_rides);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_rides);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_inbox) {
                    Toast.makeText(YourRidesActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(YourRidesActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.navigation_home) {
                    Toast.makeText(YourRidesActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Toast.makeText(YourRidesActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ProfileMenuActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private Context context;
        private ArrayList<User> userArrayList;

        public MyAdapter(Context context, ArrayList<User> userArrayList) {
            this.context = context;
            this.userArrayList = userArrayList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User user = userArrayList.get(position);
            holder.textView.setText(user.getName());
        }

        @Override
        public int getItemCount() {
            return userArrayList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}