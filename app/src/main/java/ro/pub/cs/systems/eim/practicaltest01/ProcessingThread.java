package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {
    Context context;
    Integer left_number, right_number;
    Boolean isRunning = true;

    static final String[] actionsList = { "data", "media_artimetica", "media_geometrica"};
    Random rnd = new Random();

    public ProcessingThread(Context context, String left_number, String right_number) {
        this.context = context;
        this.left_number = Integer.valueOf(left_number);
        this.right_number = Integer.valueOf(right_number);

    }

    @Override
    public void run() {
        while(isRunning) {
            try {
                sendMessage();
                sleep(10 * 100);
            }
            catch (Exception e) {

            }
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(actionsList[rnd.nextInt(actionsList.length)]);
        intent.putExtra("data",new Date(System.currentTimeMillis()) + " " + (((double)left_number + right_number) / 2) + " " + (Math.sqrt((double)left_number * left_number + (double)right_number * right_number)));
        this.context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
