package com.robotai.fadhil;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class RobotService extends Service {
    private WindowManager wm;
    private Button robot;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!Settings.canDrawOverlays(this)) {
            stopSelf();
            return;
        }

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        robot = new Button(this);
        robot.setText("🤖");
        robot.setTextSize(22);
        robot.setTextColor(Color.WHITE);
        robot.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(25, 130, 220)
            )
        );

        robot.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(this, robot);
            menu.getMenu().add("Tanya AI");
            menu.getMenu().add("Bantu belajar");
            menu.getMenu().add("Matikan robot");

            menu.setOnMenuItemClickListener(item -> {
                String pilihan = item.getTitle().toString();

                if (pilihan.equals("Matikan robot")) {
                    stopSelf();
                } else {
                    Toast.makeText(
                        this,
                        pilihan + " akan kita aktifkan berikutnya!",
                        Toast.LENGTH_SHORT
                    ).show();
                }
                return true;
            });

            menu.show();
        });

        int jenisJendela = Build.VERSION.SDK_INT >= 26
            ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            : WindowManager.LayoutParams.TYPE_PHONE;

        WindowManager.LayoutParams params =
            new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                jenisJendela,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            );

        params.gravity = Gravity.TOP | Gravity.END;
        params.x = 20;
        params.y = 180;

        wm.addView(robot, params);
    }

    @Override
    public void onDestroy() {
        if (wm != null && robot != null) {
            try {
                wm.removeView(robot);
            } catch (Exception ignored) {
            }
        }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(
        Intent intent, int flags, int startId
    ) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
