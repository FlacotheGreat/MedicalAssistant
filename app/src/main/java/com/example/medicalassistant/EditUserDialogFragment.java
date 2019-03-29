package com.example.medicalassistant;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDialogFragment extends DialogFragment {

    private View root;
    private EditText editFName, editLName, editEContact, editEContactNum, editPhysicianName, editPhysicianNum;
    private Button saveButton, existingImageButton, takePictureButton;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_user_dialog, container, false);

        editFName = root.findViewById(R.id.edit_firstname);
        editLName = root.findViewById(R.id.edit_lastname);
        editEContact = root.findViewById(R.id.edit_econtactname);
        editEContactNum = root.findViewById(R.id.edit_econtactnum);
        editPhysicianName = root.findViewById(R.id.edit_physicianName);
        editPhysicianNum = root.findViewById(R.id.edit_physicianNum);

        saveButton = root.findViewById(R.id.edit_savebutton);
        existingImageButton = root.findViewById(R.id.edit_existingimage);
        takePictureButton = root.findViewById(R.id.edit_takepicture);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(user != null){
                            user.setFname(editFName.getText().toString());
                            user.setLname(editLName.getText().toString());
                            user.seteContactName(editEContact.getText().toString());
                            user.seteContactNum(editEContactNum.getText().toString());
                            user.setPhysicianName(editPhysicianName.getText().toString());
                            user.setPhysicianNum(editPhysicianNum.getText().toString());

                            AppDatabase.getInstance(getContext()).userDAO().updateUser(user);
                        } else {
                            user.setFname(editFName.getText().toString());
                            user.setLname(editLName.getText().toString());
                            user.seteContactName(editEContact.getText().toString());
                            user.seteContactNum(editEContactNum.getText().toString());
                            user.setPhysicianName(editPhysicianName.getText().toString());
                            user.setPhysicianNum(editPhysicianNum.getText().toString());

                            AppDatabase.getInstance(getContext()).userDAO().insertUser(user);
                        }
                    }
                }).start();
            }
        });

        return root;
    }

}
