package com.hellowo.fullcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellowo.hellocal.HelloCalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
