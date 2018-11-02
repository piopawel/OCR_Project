package piotrek.projektpr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import piotrek.projektpr.R;

public class TextMenu extends Activity {
    private Bitmap bmp;
    private String result;
    private Button sendButton;
    private RequestQueue queue;
    private EditText textField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_menu);

        queue = Volley.newRequestQueue(this);

        bmp = Bitmap.createBitmap(CameraActivity.bmp);
        ImageView img= (ImageView) findViewById(R.id.imageView);
        img.setImageBitmap(bmp);
        result = new String(CameraActivity.result);
        textField = (EditText) findViewById(R.id.editText);
        textField.setText(result);

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
    }

    private void getRequest(){
        String name = String.valueOf(textField.getText());
        final JSONObject object = new JSONObject();
        try {
            object.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://192.168.0.12:8099/medicines/" + name;


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, object,
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                        String nameResult = null;
                        String doseResult = null;
                        String producerResult = null;
                        String priceResult = null;
                        String substanceResult = null;
                        String packingResult = null;
                        String refundResult = null;
                        try {
                            nameResult = response.getString("name");
                            doseResult = response.getString("dose");
                            producerResult = response.getString("producer");
                            priceResult = response.getString("price");
                            substanceResult = response.getString("substance");
                            packingResult = response.getString("packing");
                            refundResult = response.getString("refund");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        Intent goToResult = new Intent(TextMenu.this, Results.class);
                        goToResult.putExtra("name", nameResult);
                        goToResult.putExtra("dose", doseResult);
                        goToResult.putExtra("producer", producerResult);
                        goToResult.putExtra("price", priceResult);
                        goToResult.putExtra("substance", substanceResult);
                        goToResult.putExtra("packing", packingResult);
                        goToResult.putExtra("refund", refundResult);

                        TextMenu.this.startActivity(goToResult);

                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "someError");
                    }
                }
        );
        queue.add(getRequest);
    }
}
