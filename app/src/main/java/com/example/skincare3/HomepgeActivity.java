package com.example.skincare3;

import static android.provider.MediaStore.Images.Media.getBitmap;
import static java.lang.System.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skincare3.ml.SkinCare;
import com.example.skincare3.ml.SkinCare2;
import com.example.skincare3.ml.SkinCare2.Outputs;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.Processor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;


public class HomepgeActivity extends AppCompatActivity {
        private ImageView imgView;
        private Button capture, upload, submit;
        private TextView txtView;
        private Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepge);

        imgView = (ImageView) findViewById(R.id.imageView7);
        txtView = (TextView)  findViewById(R.id.textView4);
        capture = (Button)  findViewById(R.id.button);
        upload = (Button)  findViewById(R.id.button2);
        submit = (Button)  findViewById( R.id.button3);
//        String filename = getApplication().getAssets().open("labels.txt");


        //request for camera permission
        if(ContextCompat.checkSelfPermission(HomepgeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(HomepgeActivity.this, new String[]{
                    Manifest.permission.CAMERA
            },
                    100);
        }
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1000);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent. ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);


            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
             @SuppressLint({"SetTextI18n", "DefaultLocale"})
             @Override
            public void onClick(View v){

                  img= Bitmap.createScaledBitmap(img, 32, 32, true);
//                  img = img/255;

                 try {
                     Context context;
//                     SkinCare2 model = SkinCare2.newInstance(getApplicationContext());

                     SkinCare model = SkinCare.newInstance(getApplicationContext());

                     // Creates inputs for reference.
                     TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
                     TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                     tensorImage.load(img);
//                     Processor<TensorImage> imageProcessor = null;
//                     tensorImage = imageProcessor.process(tensorImage);


                     ByteBuffer byteBuffer = tensorImage.getBuffer();
                     inputFeature0.loadBuffer(byteBuffer);

                     // Runs model inference and gets result.
                     SkinCare.Outputs outputs = model.process(inputFeature0);
                     TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                     int max  = getMax(outputFeature0.getFloatArray());

                     String[] labels = {"Actinic keratosis","Basa cell carcinoma",
                             "Benign keratosis-like lesions","Dermatofibroma","Melanoma",
                             "Melanocity nevi","Vascular"};



                     txtView.setText("you may have: "+labels[max] + " skin cancer");
//                     txtView.setText("Actinic keratosis: "+ outputFeature0.getFloatArray()[0]+
//                             "\n"+" Basa cell carcinoma: "+ outputFeature0.getFloatArray()[1]+
//                             "\n"+ "Benign keratosis-like lesions: "+outputFeature0.getFloatArray()[2] +
//                             "\n"+"Dermatofibroma: "+ outputFeature0.getFloatArray()[3] +
//                             "\n"+"Melanoma: "+ outputFeature0.getFloatArray()[4] +
//                             "\n"+"Melanocity nevi: "+ outputFeature0.getFloatArray()[5] +
//                             "\n"+"Vascular: "+ outputFeature0.getFloatArray()[6]);
                     // Releases model resources if no longer used.
                     model.close();


                 } catch (IOException e) {
                     // TODO Handle the exception
                 }
             }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == 100)
        {

            imgView.setImageURI(data.getData());

            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == 1000)
        {
            //get capture image
            Bitmap captureImage = null;
            captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to imageview
            imgView.setImageBitmap(captureImage);
            img  = captureImage;

        }


    }
    public static int getMax(float[] arr){
        int ind = 0;
        float min = 0.0f;
        int i;
        for (i=0; i<7; i++)
        {
            ind = i;
            min = arr[i];
        }
        return ind;
    }
}