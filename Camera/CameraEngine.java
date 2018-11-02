package piotrek.projektpr.Camera;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;


public class CameraEngine {

    boolean on;
    Camera camera;
    SurfaceHolder surfaceHolder;

    public boolean isOn() {
        return on;
    }

    private CameraEngine(SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
    }

    public static CameraEngine New(SurfaceHolder surfaceHolder){
        return  new CameraEngine(surfaceHolder);
    }

    public static Camera getCamera() {
        try {
            return Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start() {

        this.camera = getCamera();

        if (this.camera == null)
            return;

        try {
            this.camera.setPreviewDisplay(this.surfaceHolder);
            this.camera.setDisplayOrientation(0);
            this.camera.startPreview();
            on = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){

        if(camera != null){
            camera.release();
            camera = null;
        }
        on = false;
    }

    public void takeShot(final Camera.ShutterCallback shutterCallback,
                         final Camera.PictureCallback rawPictureCallback,
                         final Camera.PictureCallback jpegPictureCallback ){
        if(isOn()){
            camera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if(success) camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
                }
            });
        }
    }

}
