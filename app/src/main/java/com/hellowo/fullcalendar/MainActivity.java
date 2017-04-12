package com.hellowo.fullcalendar;

import android.Manifest;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hellowo.hellocal.BlockAdapter;
import com.hellowo.hellocal.HelloCalendarView;

import java.util.*;
import java.util.Calendar;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            //Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_CALENDAR);

        if(permission != PERMISSION_GRANTED) {

            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage(".")
                    .setDeniedMessage(".")
                    .setPermissions(Manifest.permission.READ_CALENDAR)
                    .check();

        }

        final HelloCalendarView helloCalendarView = (HelloCalendarView)findViewById(R.id.calendar);
        Button next = (Button)findViewById(R.id.next);
        Button prev = (Button)findViewById(R.id.prev);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloCalendarView.nextMonth();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helloCalendarView.prevMonth();
            }
        });

        java.util.Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        long start = calendar.getTimeInMillis();
        calendar.add(Calendar.MONTH, 2);
        long end = calendar.getTimeInMillis();

        List<Block> blockList = OsCalendarDataProvider
                .getInstance(this)
                .getInstances("", start, end);

        for(Block block : blockList) {
            Log.i("aaa", block.toString());
        }
    }
}
