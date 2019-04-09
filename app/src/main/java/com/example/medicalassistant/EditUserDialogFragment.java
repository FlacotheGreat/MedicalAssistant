package com.example.medicalassistant;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.medicalassistant.db.AppDatabase;
import com.example.medicalassistant.db.entities.User;


import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserDialogFragment extends DialogFragment {

    private View root;
    private EditText editFName, editLName, editEContact, editEContactNum, editPhysicianName, editPhysicianNum;
    private ImageView editUserImage;
    private Button saveButton, existingImageButton, takePictureButton;
    private User user = null;

    Uri imageUri;
    private static final int PICKED_IMAGE = 100;

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

                            AppDatabase.getInstance(getContext()).userDAO().updateUser(user);
                            Log.d("TestUpdate", "updating contact:" + user.toString());
                        } else {
                            user = new User();
<<<<<<< Updated upstream
                            user.setUser_image(image);
=======
>>>>>>> Stashed changes
                            user.setFname(editFName.getText().toString());
                            user.setLname(editLName.getText().toString());
                            user.seteContactName(editEContact.getText().toString());
                            user.seteContactNum(editEContactNum.getText().toString());
                            user.setPhysicianName(editPhysicianName.getText().toString());
                            user.setPhysicianNum(editPhysicianNum.getText().toString());

                            Log.d("TestNewUser", "New user added+" + user.toString());

                            AppDatabase.getInstance(getContext()).userDAO().insertUser(user);
                            Log.d("TestInsert", "Adding unknown contact:" + user.toString());
                        }
                    }
                }).start();
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
                openCamera();
            }
        });

        return root;
    }

    private void openCamera() {

//        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePic.resolveActivity(getActivity().getPackageManager()) ! = null){
//
//            File photoFile = null;
//
//            try{
//
//                photoFile = createPhotoFile();
//            } catch (Exception e){
//
//            }
//        }


    }

//    private File createPhotoFile() {
//
//        String name = new SimpleDateFormat("yyyyMMdd_MMmmss").format(new Date());
//
//        File storageDir = getExternalStorageDirectory(Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(name, ".jpg",storageDir);
//        } catch (IOException ioe){
//
//            Log.d("TestCapture", "Exception thrown" + ioe.toString());
//        }
//
//        return image;
//    }

    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICKED_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICKED_IMAGE){
            imageUri = data.getData();
            editUserImage.setImageURI(imageUri);
        }
    }
}
