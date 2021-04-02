package com.example.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.DatabaseUtilsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.androidroom.databinding.ActivityNewContactBinding;
import com.example.androidroom.model.Contact;
import com.example.androidroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    private ActivityNewContactBinding binding;

    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                NewContact.this.getApplication()).
                create(ContactViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);

        binding.saveButton.setOnClickListener(view -> {

            if(!TextUtils.isEmpty(binding.nameText.getText()) && !TextUtils.isEmpty(binding.occupationText.getText())) {
                Contact contact = new Contact(binding.nameText.getText().toString(),
                        binding.occupationText.getText().toString());
                ContactViewModel.insert(contact);

            } else {
                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show();
            }
        });
    }
}