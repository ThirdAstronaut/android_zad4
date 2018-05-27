package lab.swim.pwr.android_zad4;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView authorNameTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
       authorNameTextView =  findViewById(R.id.authorNameAboutTextView);
       descriptionTextView =  findViewById(R.id.descriptionAboutTextView);

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
