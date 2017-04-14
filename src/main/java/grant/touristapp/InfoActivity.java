package grant.touristapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mImageView = (ImageView) findViewById(R.id.castleImage);

        mImageView.setImageResource(R.drawable.edinburgh_castle);
    }
}
