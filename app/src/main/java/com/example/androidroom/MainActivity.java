package com.example.androidroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidroom.adapter.RecyclerViewAdapter;
import com.example.androidroom.model.Contact;
import com.example.androidroom.model.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {

    private static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE =1;
    private static final String TAG = "tag";
    public static final String CONTACT_ID = "contact_id";
    private ContactViewModel contactViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Contact>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
        .getApplication())
                .create(ContactViewModel.class);


        contactViewModel.getAllContacts().observe(MainActivity.this, contacts -> {

            recyclerViewAdapter = new RecyclerViewAdapter(contacts, this, this);
            recyclerView.setAdapter(recyclerViewAdapter);

          /*  StringBuilder builder = new StringBuilder();
            for(Contact contact : contacts) {
                builder.append("-").append(contact.getName()).append(" ").append(contact.getOccupation());
                Log.d("Tag", "onChanged: " + contact.getName());
            }*/
        });


        FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(NewContact.NAME_REPLY);
            String occupation = data.getStringExtra(NewContact.OCCUPATION);
            Contact contact = new Contact(name, occupation);

            ContactViewModel.insert(contact);
        }
    }

    @Override
    public void onContactClick(int position) {
        Contact contact = contactViewModel.allContacts.getValue().get(position);
        Log.d(TAG, "onContactClick: " + contact.getName());
       // startActivity(new Intent(MainActivity.this, NewContact.class));

        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(CONTACT_ID, contact.getId());
        startActivity(intent);
    }
}