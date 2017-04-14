package grant.touristapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_map, btn_journals, btn_media, btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_map = (Button) findViewById(R.id.btn_map);
        btn_journals = (Button) findViewById(R.id.btn_journals);
        btn_media = (Button) findViewById(R.id.btn_media);
        btn_info = (Button) findViewById(R.id.btn_info);

        btn_map.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(myIntent);
            }
        });

        btn_journals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, JournalEntryActivity.class);
                startActivity(myIntent);
            }
        });

        btn_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, MediaActivity.class);
                startActivity(myIntent);
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
