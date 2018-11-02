package piotrek.projektpr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import piotrek.projektpr.R;

public class NewMedicineMenu extends AppCompatActivity {

    private EditText name;
    private EditText dose;
    private EditText producer;
    private EditText price;
    private EditText substance;
    private EditText packing;
    private EditText refund;
    private Button sendButton;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine_menu);
        name = (EditText) findViewById(R.id.name);
        dose = (EditText) findViewById(R.id.dose);
        producer = (EditText) findViewById(R.id.producer);
        price = (EditText) findViewById(R.id.price);
        substance = (EditText) findViewById(R.id.substance);
        packing = (EditText) findViewById(R.id.packing);
        refund = (EditText) findViewById(R.id.refund);
        sendButton = (Button) findViewById(R.id.sendPOST);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPOST();
            }
        });
        queue = Volley.newRequestQueue(this);
    }

    private JSONObject createJSON(){
        JSONObject object = new JSONObject();
        try {
            object.put("name", name.getText());
            object.put("dose", dose.getText());
            object.put("producer", producer.getText());
            object.put("price", price.getText());
            object.put("substance", substance.getText());
            object.put("packing", packing.getText());
            object.put("refund", refund.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private void sendPOST(){
        JSONObject object = createJSON();
        String url = "http://192.168.0.12:8099/medicines/"+name.getText();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", "Error");
                    }
                }
        );
        Toast.makeText(NewMedicineMenu.this, "Lek " + name.getText() + " został dodany!", Toast.LENGTH_SHORT).show();
        queue.add(getRequest);
    }

    public void goBack(View v){
        Intent goBack = new Intent(this, StartMenu.class);
        startActivity(goBack);
    }
}
