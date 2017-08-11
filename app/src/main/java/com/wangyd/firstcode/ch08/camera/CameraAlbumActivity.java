package com.wangyd.firstcode.ch08.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangyd.firstcode.Utils.DBG;
import com.wangyd.firstcode.R;

import java.io.File;
import java.io.IOException;

public class CameraAlbumActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CameraAlbumActivity";
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri uri;
    private Activity aty = CameraAlbumActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_album);
        findViewById(R.id.camera).setOnClickListener(this);
        findViewById(R.id.camera_b).setOnClickListener(this);
        findViewById(R.id.album).setOnClickListener(this);
        picture = (ImageView) findViewById(R.id.picture);
    }

    @Override
    public void onClick(View v) {
        File outputImage;
        Intent intent;
        switch (v.getId()) {
            case R.id.camera:
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
                //startActivityForResult(intent, TAKE_PHOTO);//开启相机，传入上面的Intent对象


                DBG.log(TAG, getExternalCacheDir().getAbsolutePath());
                //File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                outputImage = new File("/sdcard/", "output_image.jpg");
                if (outputImage.exists())
                    outputImage.delete();
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    uri = uri.fromFile(outputImage);
                } else {
                    uri = FileProvider.getUriForFile(aty, "com.wangyd.firstcode.fileprovider", outputImage);
                }
                //uri = FileProvider.getUriForFile(aty, "com.wangyd.firstcode.fileprovider", outputImage);
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1); //调用前置摄像头
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;

            case R.id.camera_b:
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
                //startActivityForResult(intent, TAKE_PHOTO);//开启相机，传入上面的Intent对象


                DBG.log(TAG, getExternalCacheDir().getAbsolutePath());
                //File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                outputImage = new File("/sdcard/", "output_image.jpg");
                if (outputImage.exists())
                    outputImage.delete();
                try {
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    uri = uri.fromFile(outputImage);
                } else {
                    uri = FileProvider.getUriForFile(aty, "com.wangyd.firstcode.fileprovider", outputImage);
                }
                //uri = FileProvider.getUriForFile(aty, "com.wangyd.firstcode.fileprovider", outputImage);
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;

            case R.id.album:
                if (ContextCompat.checkSelfPermission(aty, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(aty, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(aty, "you denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {

//                    Bundle extras = data.getExtras();//从Intent中获取附加值
//                    Bitmap bitmap = (Bitmap) extras.get("data");//从附加值中获取返回的图像
//                    picture.setImageBitmap(bitmap);//显示图像

                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //handleImageBeforeKitKat(data);
                    //handleImageOnKitKat(data);
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        DBG.log(TAG, "on");
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        DBG.log(TAG, "before");
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        DBG.log(TAG, "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
