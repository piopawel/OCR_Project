package piotrek.projektpr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import piotrek.projektpr.R;


public class StartMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    public void goToCameraActivity(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
    public void goToNewMedicineMenu(View view){
        Intent intent = new Intent(this, NewMedicineMenu.class);
        startActivity(intent);
    }
}
