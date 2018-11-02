package piotrek.projektpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import piotrek.projektpr.Camera.CameraEngine;
import piotrek.projektpr.Camera.Rectangle;
import piotrek.projektpr.TesseractAPI.TessEngine;


public class CameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener,
        Camera.PictureCallback, Camera.ShutterCallback {

    Button shutterButton;
    SurfaceView cameraFrame;
    CameraEngine cameraEngine;
    public static String result;
    public static Bitmap bmp;
    private ImageView rectangleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        rectangleView = (ImageView) findViewById(R.id.rectangleView);
        Rectangle rectangle = new Rectangle(windowWidth(), windowHeight());
        rectangleView.setImageBitmap(rectangle.bitmapToDraw());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if (cameraEngine != null && !cameraEngine.isOn()) {
            cameraEngine.start();
        }

        if (cameraEngine != null && cameraEngine.isOn()) {
            return;
        }

        cameraEngine = CameraEngine.New(holder);
        cameraEngine.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        cameraFrame = (SurfaceView) findViewById(R.id.camera_frame);
        shutterButton = (Button) findViewById(R.id.shutter_button);

        shutterButton.setOnClickListener(this);

        SurfaceHolder surfaceHolder = cameraFrame.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraFrame.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (cameraEngine != null && cameraEngine.isOn()) {
            cameraEngine.stop();
        }

        SurfaceHolder surfaceHolder = cameraFrame.getHolder();
        surfaceHolder.removeCallback(this);
    }

    @Override
    public void onClick(View v) {
        if(v == shutterButton){
            if(cameraEngine != null && cameraEngine.isOn()){
                cameraEngine.takeShot(this, this, this);
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        if (data == null) {
            return;
        }
        Bitmap unscaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bmp = Bitmap.createBitmap(unscaledBitmap, unscaledBitmap.getWidth() / 5, unscaledBitmap.getHeight() / 5 * 2,
                unscaledBitmap.getWidth() /5 * 3, unscaledBitmap.getHeight() / 5);
        TessEngine tessEngine =  TessEngine.Generate(this);
        result = tessEngine.detectText(bmp);
        Intent textMenu = new Intent(this, TextMenu.class);
        startActivity(textMenu);
    }

    @Override
    public void onShutter() {

    }

    private int windowHeight(){
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    private int windowWidth(){
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

}