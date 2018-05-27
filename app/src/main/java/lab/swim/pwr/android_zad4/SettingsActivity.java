package lab.swim.pwr.android_zad4;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.custom_bmi_menu_layout, null);
        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setCustomView(customView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        ImageButton addContent = customView.findViewById(R.id.back_arrow);
        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
