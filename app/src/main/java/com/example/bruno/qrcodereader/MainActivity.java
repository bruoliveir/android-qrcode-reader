package com.example.bruno.qrcodereader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    private ImageView i;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonProcess = (Button) findViewById(R.id.buttonProcess);
        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);

        i = (ImageView) findViewById(R.id.image);
        t = (TextView) findViewById(R.id.text);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qrcode);
                i.setImageBitmap(bitmap);

                BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

                if (!barcodeDetector.isOperational()) {
                    t.setText("Could not set up the detector");
                    return;
                }

                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

                Barcode barcode = barcodes.valueAt(0);
                t.setText(barcode.rawValue);
            }
        });
    }
}
