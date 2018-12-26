package com.example.yossi.storage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static  final int PICK_IMAGE_REQUEST=1;

    Button btnPick,btnAll;
    ImageView iv;

    StorageReference mStorageRef;
    DatabaseReference usersRef;

    Uri imageUri;
    String downloadUrl;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);

        btnPick = findViewById(R.id.btnPick);
        btnPick.setOnClickListener(this);

        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        progressDialog = new ProgressDialog(this);






    }

    @Override
    public void onClick(View v) {


        if(v == btnPick)
        {
            openFileChooser();
        }
        else if(v == btnAll)
        {
            Intent intent = new Intent(MainActivity.this, ShowAllImagesActivity.class);
            startActivity(intent);
        }

    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
        {

            imageUri = data.getData();
            uploadFile();
        }
    }

    private void uploadFile() {


        final String fileChildId = String.valueOf(System.currentTimeMillis());
        StorageReference fileRef = mStorageRef.child(fileChildId);

        progressDialog.setMessage("uploading image...");
        progressDialog.show();

        //uploading

        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "upload complete", Toast.LENGTH_SHORT).show();



                mStorageRef.child(fileChildId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        downloadUrl = uri.toString();

                        //create User object

                        String uid = usersRef.push().getKey();
                        User user = new User(uid,"popo",downloadUrl);
                        usersRef.child(uid).setValue(user);

                        //present thr image

                        Picasso.get().load(downloadUrl).into(iv);


                    }
                });

            }
        });

        //-------------------------------------



//downloading



    }
}
