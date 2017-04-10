package com.hellowo.fullcalendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OS캘린더 데이타 제공자
 */
public class OsCalendarDataProvider {
    private static OsCalendarDataProvider instance = new OsCalendarDataProvider();

    public static OsCalendarDataProvider getInstance(Context context) {
        if (instance.cr == null) {
            instance.cr = context.getContentResolver();
        }
        return instance;
    }

    private OsCalendarDataProvider() {
    }

    private static String[] CALENDAR_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL
    };

    private static final String[] EVENT_PROJECTION = new String[]{
            Events._ID,
            Events.TITLE,
            Events.DTSTART,
            Events.DTEND,
            Events.ALL_DAY,
            Events.CALENDAR_COLOR,
            Events.EVENT_COLOR,
            Events.EVENT_LOCATION,
            Events.DESCRIPTION,
            Events.DURATION,
            Events.RRULE,
            Events.RDATE,
            Events.HAS_ALARM,
            Events.HAS_ATTENDEE_DATA,
            Events.EVENT_TIMEZONE,
            Events.CALENDAR_ID,
    };

    private static final String[] INSTANCE_PROJECTION = new String[]{
            Instances.EVENT_ID,
            Instances.TITLE,
            Instances.BEGIN,
            Instances.END,
            Instances.ALL_DAY,
            Instances.CALENDAR_COLOR,
            Instances.EVENT_COLOR,
            Instances.EVENT_LOCATION,
            Instances.DESCRIPTION,
            Instances.DURATION,
            Instances.RRULE,
            Instances.RDATE,
            Instances.HAS_ALARM,
            Instances.HAS_ATTENDEE_DATA,
            Instances.EVENT_TIMEZONE,
            Instances.CALENDAR_ID,
            Instances.DTSTART,
            Instances.DTEND,
            Instances.ORIGINAL_INSTANCE_TIME
    };

    private static final String[] ALARM_PROJECTION = new String[]{
            CalendarContract.Reminders.MINUTES
    };

    private static final String[] ATTENDEE_PROJECTION = new String[]{
            CalendarContract.Attendees.ATTENDEE_NAME,
            CalendarContract.Attendees.ATTENDEE_EMAIL,
            CalendarContract.Attendees.ATTENDEE_STATUS,
            CalendarContract.Attendees.ATTENDEE_RELATIONSHIP
    };

    private ContentResolver cr;

    /**
     * 기간과 키워드로 Instance 가져오기
     * @param keyWord 키워드
     * @param startMillis 시작시간
     * @param endMillis 종료 시간
     * @return 타임블럭 리스트
     */
    List<Block> getInstances(String keyWord, long startMillis, long endMillis) {
        List<Block> instance_list = new ArrayList<>();
        String selection;
        String[] selectionArgs;
        Cursor cur;

        if (TextUtils.isEmpty(keyWord)) {
            selection = "(" + Instances.VISIBLE + " = ?)";
            selectionArgs = new String[]{"1"};
        } else {
            selection = "(" + Instances.VISIBLE + " = ?) AND ((" +
                    Instances.TITLE + " LIKE ?) OR (" + Instances.DESCRIPTION + " LIKE ?))";
            selectionArgs = new String[]{"1", "%" + keyWord + "%", "%" + keyWord + "%"};
        }

        Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        cur = cr.query(builder.build(), INSTANCE_PROJECTION, selection, selectionArgs,
                Instances.BEGIN + " asc");

        Log.i("aaa", "????" + selection);

        if (cur != null && cur.getCount() > 0) {
            Log.i("aaa", "aaaaaaaaa" + selection);
            while (!cur.isLast()) {
                cur.moveToNext();
                try {
                    Block block = new Block();
                    block.title = cur.getString(1);
                    block.dtStart = cur.getLong(2);
                    block.dtEnd = cur.getLong(3);
                    block.color = cur.getInt(5);
                    instance_list.add(block);
                    //getAlarms(block); // 퍼포먼스 이슈
                    //getAttendees(block);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        }

        if (cur != null) {
            cur.close();
        }
        return instance_list;
    }

    public void getAlarms(Block timeBlock) {
        Cursor cur;
        cur = cr.query(CalendarContract.Reminders.CONTENT_URI, ALARM_PROJECTION,
                CalendarContract.Reminders.EVENT_ID + "=?",
                new String[]{String.valueOf(timeBlock.getId())},
                null);

        if (cur != null && cur.getCount() > 0) {
            while (!cur.isLast()) {
                cur.moveToNext();
                try {
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    public void getAttendees(Block timeBlock) {
        Cursor cur;
        cur = cr.query(CalendarContract.Attendees.CONTENT_URI, ATTENDEE_PROJECTION,
                CalendarContract.Attendees.EVENT_ID + "=?",
                new String[]{String.valueOf(timeBlock.getId())},
                null);

        if (cur != null && cur.getCount() > 0) {
            while (!cur.isLast()) {
                cur.moveToNext();
                try {
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
    }

    /**
     * Os캘린더 리스트 가져오기
     */
    public List<Calendar> getCalendarList() {
        List<Calendar> categoryList = new ArrayList<>();
        Cursor cur = cr.query(
                CalendarContract.Calendars.CONTENT_URI,
                CALENDAR_PROJECTION,
                null, null, null
        );

        if (cur != null && cur.getCount() > 0) {
            while (!cur.isLast()) {
                cur.moveToNext();
                try{

                }catch (Exception ignore){
                    ignore.printStackTrace();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return categoryList;
    }

    public Block getBlockById(long timeBlockId) {
        Block block = null;

        Cursor cur = cr.query(Events.CONTENT_URI, EVENT_PROJECTION,
                Events._ID + "=?", new String[]{String.valueOf(timeBlockId)}, null);

        if (cur != null && cur.getCount() > 0) {
            cur.moveToNext();
            try{
                getAlarms(block);
                getAttendees(block);
            }catch (Exception ignore){
                ignore.printStackTrace();
            }
        }

        if (cur != null) {
            cur.close();
        }

        return block;
    }
}
