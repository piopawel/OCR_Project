package piotrek.projektpr.Camera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangle {
    private Rect rectangle;
    public Rectangle(int width, int height){
        this.rectangle = new Rect(width / 5, height / 5 * 2, width / 5 * 4, height / 5 * 3);
    }

    public Bitmap bitmapToDraw(){
        Bitmap bitmap = Bitmap.createBitmap(rectangle.left * 5, rectangle.top * 5 / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        canvas.drawRect(rectangle, paint);

        return  bitmap;
    }
}
