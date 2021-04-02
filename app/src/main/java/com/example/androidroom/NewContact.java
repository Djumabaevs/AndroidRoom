package com.example.androidroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.database.DatabaseUtilsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.androidroom.databinding.ActivityNewContactBinding;
import com.example.androidroom.model.Contact;
import com.example.androidroom.model.ContactViewModel;

public class NewContact extends AppCompatActivity {

    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    public ActivityNewContactBinding binding;

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
    }
}