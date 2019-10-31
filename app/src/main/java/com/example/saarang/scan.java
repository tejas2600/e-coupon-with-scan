package com.example.saarang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class scan extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1001;
    @BindView(R.id.sv_camera)
    SurfaceView cameraView;
    @BindView(R.id.ib_torch)
    ImageButton ib_torch;
    private CameraSource cameraSource;
    private boolean isDeviceHasFlash;
    private boolean isTorchOn = false;


    private static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        isDeviceHasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        setOnClickListeners();
        permissionCheck();


    }
    private void setOnClickListeners() {
        ib_torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashOnButton();
            }
        });
    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            create_camera_source();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                    create_camera_source();

                } else {
                    Toast.makeText(this, "Camera permission not granted!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    private void create_camera_source() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(scan.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    Intent intent = new Intent(scan.this,Pay.class);
                    intent.putExtra("barcode", barcodes.valueAt(0));
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                }

            }
        });

    }

    private void flashOnButton() {
        if (isDeviceHasFlash) {
            Camera camera = getCamera(cameraSource);
            if (camera != null) {
                try {
                    Camera.Parameters param = camera.getParameters();
                    param.setFlashMode(!isTorchOn ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(param);
                    isTorchOn = !isTorchOn;
                    if (isTorchOn) {
                        ib_torch.setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_on_black_24dp));
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            ib_torch.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.flash_button_on));
                        } else {
                            ib_torch.setBackground(ContextCompat.getDrawable(this, R.drawable.flash_button_on));
                        }
                    } else {
                        ib_torch.setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_on_white_24dp));
                        final int sdk = android.os.Build.VERSION.SDK_INT;
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            ib_torch.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.flash_button_off));
                        } else {
                            ib_torch.setBackground(ContextCompat.getDrawable(this, R.drawable.flash_button_off));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }



}
