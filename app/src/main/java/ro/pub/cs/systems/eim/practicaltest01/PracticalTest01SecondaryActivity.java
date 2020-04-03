package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    Button ok_button, cancel_button;
    TextView total_text;
    Intent intentFromParent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        intentFromParent = getIntent();
        ok_button = findViewById(R.id.ok_button);
        cancel_button = findViewById(R.id.cancel_button);
        total_text = findViewById(R.id.total_text);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, intentFromParent);
                finish();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, intentFromParent);
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Integer total = 0;
        total += intentFromParent.getIntExtra("left", 0);
        total += intentFromParent.getIntExtra("right", 0);
        total_text.setText(total.toString());
    }
}
