package lab.swim.pwr.android_zad4;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    private static String currentTheme = "Light";
    private TextView authorNameTextView;
    private TextView descriptionTextView;
    private ConstraintLayout layout;

    public static void start(Context context, String theme) {
        Intent starter = new Intent(context, AboutActivity.class);
        starter.putExtra("currentTheme", theme);
        context.startActivity(starter);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        descriptionTextView = findViewById(R.id.descriptionAboutTextView);
        authorNameTextView = findViewById(R.id.authorNameAboutTextView);
        layout = findViewById(R.id.aboutLayout);
        if (savedInstanceState != null) {
            currentTheme = savedInstanceState.getString("currentTheme");
        }

        if (getIntent().getStringExtra("currentTheme") != null) {
            currentTheme = getIntent().getStringExtra("currentTheme");
        }


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
        setTheme();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putString("currentTheme", currentTheme);
        super.onSaveInstanceState(bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setTheme() {
        if (currentTheme == null || currentTheme.equals("Light")) {
            layout.setBackgroundResource(R.color.listLight);
        } else {
            layout.setBackgroundResource(R.color.listDarkBottom);
            authorNameTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
            descriptionTextView.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        }
    }

}
