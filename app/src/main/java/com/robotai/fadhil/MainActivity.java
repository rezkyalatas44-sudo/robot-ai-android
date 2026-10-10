
package com.robotai.fadhil;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.Settings;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(28, 40, 28, 28);
        layout.setBackgroundColor(Color.rgb(18, 24, 38));

        TextView title = new TextView(this);
        title.setText("🤖 ROBOT AI");
        title.setTextColor(Color.WHITE);
        title.setTextSize(28);
        layout.addView(title);

        TextView info = new TextView(this);
        info.setText("Asisten AI versi 0.1");
        info.setTextColor(Color.LTGRAY);
        layout.addView(info);

        Button start = new Button(this);
        start.setText("Aktifkan robot mengambang");
        layout.addView(start);

        Button ask = new Button(this);
        ask.setText("Tanya AI (belum aktif)");
        layout.addView(ask);

        start.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent i = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
                );
                startActivity(i);
            } else {
                startService(new Intent(this, RobotService.class));
                Toast.makeText(this, "Robot diaktifkan!", Toast.LENGTH_SHORT).show();
            }
        });

        ask.setOnClickListener(v ->
            new AlertDialog.Builder(this)
                .setTitle("Robot AI")
                .setMessage("Fitur AI akan ditambahkan setelah robot dasar berhasil.")
                .setPositiveButton("Oke", null)
                .show()
        );

        setContentView(layout);
    }
}
