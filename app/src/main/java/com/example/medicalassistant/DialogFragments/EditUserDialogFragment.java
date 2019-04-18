package com.example.medicalassistant.DialogFragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.medicalassistant.R;
import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.User;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDialogFragment extends DialogFragment {

    private View root;
    private EditText editFName, editLName, editEContact, editEContactNum, editPhysicianName, editPhysicianNum;
    private ImageView editUserImage;
    private Button saveButton, existingImageButton, takePictureButton;
    private User user = null;
    private returnuser mCallback;
    String pathToFile;

    Uri imageUri;
    private static final int PICKED_IMAGE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public interface returnuser{

        void returnUser(User user);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{

            mCallback = (returnuser) activity;
        } catch (ClassCastException ccs){
            Log.e("ClassCastException", "Must implement returnuser" + ccs.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_user_dialog, container, false);

        Toolbar toolbar = root.findViewById(R.id.Usertoolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        toolbar.setTitle("Add User");


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        editFName = root.findViewById(R.id.edit_firstname);
        editLName = root.findViewById(R.id.edit_lastname);
        editEContact = root.findViewById(R.id.edit_econtactname);
        editEContactNum = root.findViewById(R.id.edit_econtactnum);
        editPhysicianName = root.findViewById(R.id.edit_physicianName);
        editPhysicianNum = root.findViewById(R.id.edit_physicianNum);
        editUserImage = root.findViewById(R.id.medication_image);

        saveButton = root.findViewById(R.id.edit_savebutton);
        existingImageButton = root.findViewById(R.id.edit_existingimage);
        takePictureButton = root.findViewById(R.id.medication_takepicture);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String image = imageUri.toString();

                        if(user != null){
                            user.setUser_image(image);
                            user.setFname(editFName.getText().toString());
                            user.setLname(editLName.getText().toString());
                            user.seteContactName(editEContact.getText().toString());
                            user.seteContactNum(editEContactNum.getText().toString());
                            user.setPhysicianName(editPhysicianName.getText().toString());
                            user.setPhysicianNum(editPhysicianNum.getText().toString());

                            mCallback.returnUser(user);
                            AppDatabase.getInstance(getContext()).userDAO().updateUser(user);
                            Log.d("TestUpdate", "updating contact:" + user.toString());
                        } else {
                            user = new User();

                            user.setUser_image(image);
                            user.setFname(editFName.getText().toString());
                            user.setLname(editLName.getText().toString());
                            user.seteContactName(editEContact.getText().toString());
                            user.seteContactNum(editEContactNum.getText().toString());
                            user.setPhysicianName(editPhysicianName.getText().toString());
                            user.setPhysicianNum(editPhysicianNum.getText().toString());
                            user.setCurrent(true);

                            Log.d("TestNewUser", "New user added+" + user.toString());

                            mCallback.returnUser(user);
                            AppDatabase.getInstance(getContext()).userDAO().insertUser(user);
                            Log.d("TestInsert", "Adding unknown contact:" + user.toString());
                        }
                    }
                }).start();

                dismiss();
            }
        });

        existingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallery();
            }
        });

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    private File createPhotoFile() {

        String name = new SimpleDateFormat("yyyyMMdd_MMmmss").format(new Date());

        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg",storageDir);
        } catch (IOException ioe){

            Log.d("TestCapture", "Exception thrown" + ioe.toString());
        }

        return image;
    }

    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICKED_IMAGE);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            photoFile = createPhotoFile();

            if(photoFile != null){
                pathToFile =photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(getContext(),"com.example.medicalassistant",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                imageUri = photoUri;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICKED_IMAGE){
            imageUri = data.getData();
            //editUserImage.setImageURI(imageUri);
        } else if(resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            editUserImage.setImageURI(imageUri);
        }
    }

}
