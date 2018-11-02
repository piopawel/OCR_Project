package piotrek.projektpr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import piotrek.projektpr.R;

public class Results extends AppCompatActivity {

    TextView nameView;
    TextView doseView;
    TextView producerView;
    TextView priceView;
    TextView substanceView;
    TextView packingView;
    TextView refundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        nameView = (TextView) findViewById(R.id.nameResult);
        doseView = (TextView) findViewById(R.id.doseResult);
        producerView = (TextView) findViewById(R.id.producerResult);
        priceView= (TextView) findViewById(R.id.priceResult);
        substanceView = (TextView) findViewById(R.id.substanceResult);
        packingView = (TextView) findViewById(R.id.packingResult);
        refundView = (TextView) findViewById(R.id.refundResult);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nameView.setText("Nazwa : " + bundle.getString("name"));
            doseView.setText("Dawka : " + bundle.getString("dose"));
            producerView.setText("Producent : " + bundle.getString("producer"));
            priceView.setText("Cena : " + bundle.getString("price"));
            substanceView.setText("Substancja : " + bundle.getString("substance"));
            packingView.setText("Opakowanie : " + bundle.getString("packing"));
            refundView.setText("Refundacja: " + bundle.getString("refund"));
        }
    }

    public void goBack(View v){
        Intent goBack = new Intent(this, StartMenu.class);
        startActivity(goBack);
    }
}
