package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PracticalTest01Service extends Service {

    ProcessingThread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String left_number = intent.getStringExtra("left");
        String right_number = intent.getStringExtra("right");
        thread = new ProcessingThread(this, left_number, right_number);
        thread.start();
        return Service.START_REDELIVER_INTENT;
    }

    public void onDestroy() {
        thread.stopThread();
    }
}
