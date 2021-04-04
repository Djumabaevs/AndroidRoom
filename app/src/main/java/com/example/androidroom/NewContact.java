package com.example.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import com.example.androidroom.databinding.ActivityNewContactBinding;
import com.example.androidroom.model.Contact;
import com.example.androidroom.model.ContactViewModel;
import com.google.android.material.snackbar.Snackbar;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    public ActivityNewContactBinding binding;
    private int contactId = 0;
    private Boolean isEdit = false;


    private ContactViewModel contactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contactViewModel = new ViewModelProvider.AndroidViewModelFactory(
                NewContact.this.getApplication()).
                create(ContactViewModel.class);

        Bundle data = getIntent().getExtras();
        if(getIntent().hasExtra(MainActivity.CONTACT_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.CONTACT_ID, 0);
            contactViewModel.get(contactId).observe(this, contact -> {
                if(contact!=null) {
                    binding.nameText.setText(contact.getName());
                    binding.occupationText.setText(contact.getOccupation());
                }
            });
            isEdit = true;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);

        binding.saveButton.setOnClickListener(view -> {

            Intent replyIntent = new Intent();


            if(!TextUtils.isEmpty(binding.nameText.getText()) && !TextUtils.isEmpty(binding.occupationText.getText())) {
                String name = binding.nameText.getText().toString();
                String occupation = binding.occupationText.getText().toString();

                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(OCCUPATION, occupation);
                setResult(RESULT_OK, replyIntent);

            } else {
//                Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();
        });

        binding.updateButton.setOnClickListener(view -> {
            int id = contactId;
            String name = binding.nameText.getText().toString().trim();
            String occupation = binding.occupationText.getText().toString().trim();

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
                Snackbar.make(binding.nameText, R.string.empty, Snackbar.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setOccupation(occupation);
                ContactViewModel.update(contact);
                finish();
            }
        });

        if(isEdit) {
            binding.saveButton.setVisibility(View.GONE);
        } else {
            binding.updateButton.setVisibility(View.GONE);
            binding.deleteButton.setVisibility(View.GONE);
        }

        binding.deleteButton.setOnClickListener(view -> {
            int id = contactId;
            String name = binding.nameText.getText().toString().trim();
            String occupation = binding.occupationText.getText().toString().trim();

            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
                Snackbar.make(binding.nameText, R.string.empty, Snackbar.LENGTH_SHORT).show();
            } else {
                Contact contact = new Contact();
                contact.setId(id);
                contact.setName(name);
                contact.setOccupation(occupation);

                ContactViewModel.delete(contact);
                finish();
            }
        });
    }
}