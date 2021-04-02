package com.example.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.androidroom.model.Contact;
import com.example.androidroom.model.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactViewModel contactViewModel;

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_id);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
        .getApplication())
                .create(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(MainActivity.this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                StringBuilder builder = new StringBuilder();
                for(Contact contact : contacts) {
                    builder.append("-").append(contact.getName()).append(" ").append(contact.getOccupation());
                    Log.d("Tag", "onChanged: " + contact.getName());
                }
                textView.setText(builder.toString());
            }
        });
        
    }
}