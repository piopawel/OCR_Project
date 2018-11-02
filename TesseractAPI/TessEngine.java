package piotrek.projektpr.TesseractAPI;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;


public class TessEngine {

    private Context context;
    private TessEngine(Context context){
        this.context = context;
    }
    public static TessEngine Generate(Context context) {
        return new TessEngine(context);
    }

    public String detectText(Bitmap bitmap) {

        TessDataManager.initTessTrainedData(context);
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = TessDataManager.getTesseractFolder();
        tessBaseAPI.setDebug(true);
        tessBaseAPI.init(path,"eng");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,");
        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "");

        tessBaseAPI.setPageSegMode(TessBaseAPI.OEM_TESSERACT_CUBE_COMBINED);
        tessBaseAPI.setImage(bitmap);
        String inspection = tessBaseAPI.getUTF8Text();
        tessBaseAPI.end();
        System.gc();
        return inspection;
    }

}
