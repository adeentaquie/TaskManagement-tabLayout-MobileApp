package com.example.taskmanagement_tablayout_ass3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class ProfileFragment extends Fragment {

    private TextView textFullName, textPhone, textGender, textDOB;
    private FloatingActionButton fabEditBiodata;
    private SharedPrefManager sharedPrefManager;

    private String selectedDOB = "";
    private Switch switchTheme;


    public ProfileFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textFullName = view.findViewById(R.id.text_fullname);
        textPhone = view.findViewById(R.id.text_phone);
        textGender = view.findViewById(R.id.text_gender);
        textDOB = view.findViewById(R.id.text_dob);
        fabEditBiodata = view.findViewById(R.id.fab_edit_biodata);
        switchTheme = view.findViewById(R.id.switch_theme);  // Add this line

        sharedPrefManager = new SharedPrefManager(getContext());

        loadBiodata();

        fabEditBiodata.setOnClickListener(v -> openEditBiodataDialog());

        // 📍 Now here: Set switch based on saved theme
        if ("dark".equals(sharedPrefManager.getTheme())) {
            switchTheme.setChecked(true);
        } else {
            switchTheme.setChecked(false);
        }

        // 📍 Now here: Set switch listener to change theme
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sharedPrefManager.saveTheme("dark");
            } else {
                sharedPrefManager.saveTheme("light");
            }
            ThemeManager.applyTheme(getActivity());
            getActivity().recreate(); // Restart activity to apply theme
        });

        return view;
    }

    private void loadBiodata() {
        String fullName = sharedPrefManager.getFirstName() + " " + sharedPrefManager.getLastName();
        textFullName.setText("Full Name: " + fullName);
        textPhone.setText("Phone: " + sharedPrefManager.getPhone());
        textGender.setText("Gender: " + sharedPrefManager.getGender());
        textDOB.setText("DOB: " + sharedPrefManager.getDOB());
    }

    private void openEditBiodataDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_biodata_profile, null);

        EditText editFirstName = dialogView.findViewById(R.id.edit_first_name);
        EditText editLastName = dialogView.findViewById(R.id.edit_last_name);
        EditText editPhone = dialogView.findViewById(R.id.edit_phone);
        Spinner spinnerGender = dialogView.findViewById(R.id.spinner_gender);
        Button btnPickDOB = dialogView.findViewById(R.id.btn_pick_dob);
        TextView textSelectedDOB = dialogView.findViewById(R.id.text_selected_dob);

        // Set previous data
        editFirstName.setText(sharedPrefManager.getFirstName());
        editLastName.setText(sharedPrefManager.getLastName());
        editPhone.setText(sharedPrefManager.getPhone());
        textSelectedDOB.setText(sharedPrefManager.getDOB());
        selectedDOB = sharedPrefManager.getDOB(); // in case no change

        // Setup gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Set gender if already selected
        String currentGender = sharedPrefManager.getGender();
        if (currentGender.equals("Male")) {
            spinnerGender.setSelection(0);
        } else if (currentGender.equals("Female")) {
            spinnerGender.setSelection(1);
        } else if (currentGender.equals("Other")) {
            spinnerGender.setSelection(2);
        }

        // DOB Picker
        btnPickDOB.setOnClickListener(v -> openDatePicker(textSelectedDOB));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Bio-data");
        builder.setView(dialogView);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String firstName = editFirstName.getText().toString().trim();
            String lastName = editLastName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            sharedPrefManager.saveFirstName(firstName);
            sharedPrefManager.saveLastName(lastName);
            sharedPrefManager.savePhone(phone);
            sharedPrefManager.saveGender(gender);
            sharedPrefManager.saveDOB(selectedDOB);

            loadBiodata();
            Toast.makeText(getContext(), "Bio-data Updated", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void openDatePicker(TextView textSelectedDOB) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDOB = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    textSelectedDOB.setText(selectedDOB);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
