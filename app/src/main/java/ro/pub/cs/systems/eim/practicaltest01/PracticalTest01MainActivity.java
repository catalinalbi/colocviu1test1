package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    Button left_button, right_button, secondary_activity_button;
    TextView left_text, right_text;
    Intent secondary;
    Boolean service_status = false;
    final private static int ANOTHER_ACTIVITY_REQUEST_CODE = 2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        left_button = findViewById(R.id.left_button);
        right_button = findViewById(R.id.right_button);
        secondary_activity_button = findViewById(R.id.secondary_activity_button);
        left_text = findViewById(R.id.left_text);
        right_text = findViewById(R.id.right_text);

        secondary = new Intent(this, PracticalTest01SecondaryActivity.class);

        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number = Integer.parseInt(left_text.getText().toString());
                number++;
                left_text.setText(number.toString());
                if (Integer.valueOf(right_text.getText().toString()) + Integer.valueOf(left_text.getText().toString()) >=
                        Constants.THRESHHOLD && !service_status) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("left", Integer.valueOf(left_text.getText().toString()));
                    intent.putExtra("right", Integer.valueOf(right_text.getText().toString()));
                    getApplicationContext().startService(intent);
                    service_status = true;
                }
            }
        });

        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer number = Integer.parseInt(right_text.getText().toString());
                number++;
                right_text.setText(number.toString());
                if (Integer.valueOf(right_text.getText().toString()) + Integer.valueOf(left_text.getText().toString()) >=
                        Constants.THRESHHOLD && !service_status) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                    intent.putExtra("left", left_text.getText().toString());
                    intent.putExtra("right", right_text.getText().toString());
                    getApplicationContext().startService(intent);
                    service_status = true;
                }
            }
        });

        secondary_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bundle bundle = new Bundle();
                //bundle.putInt("left", Integer.parseInt(left_text.getText().toString()));
                //bundle.putInt("right", Integer.parseInt(right_text.getText().toString()));
                //secondary.putExtras(bundle);
                secondary.putExtra("left", Integer.parseInt(left_text.getText().toString()));
                secondary.putExtra("right", Integer.parseInt(right_text.getText().toString()));
                startActivityForResult(secondary, ANOTHER_ACTIVITY_REQUEST_CODE);

            }
        });

    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("broadcast", intent.getStringExtra("data"));
        }
    }

    protected void onSaveInstanceState(Bundle savedInstance) {
        savedInstance.putString("left", left_text.getText().toString());
        savedInstance.putString("right", right_text.getText().toString());
        super.onSaveInstanceState(savedInstance);
    }

    protected void onRestoreInstanceState(Bundle savedInstance) {
        left_text.setText(savedInstance.getString("left"));
        right_text.setText(savedInstance.getString("right"));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case -1:
                Toast.makeText(this, "OK",
                        Toast.LENGTH_LONG).show();
                break;
            case 0:
                Toast.makeText(this, "CANCEL",
                        Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
