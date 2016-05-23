package com.dataart.devicehive.androidsensors.ui.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dataart.devicehive.androidsensors.R;
import com.dataart.devicehive.androidsensors.ui.adapter.RecyclerViewArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSensorActivity extends AppCompatActivity implements SensorEventListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView mSensorsList;

    private long lastUpdate;
    private SensorManager mSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        Log.e("Sensor manager", (mSensorManager == null) + " ");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mSensorsList.setLayoutManager(layoutManager);


        List<Sensor> mSensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        ArrayList<String> fakeData = new ArrayList<>();

        for (Sensor sensor : mSensorList) {
            fakeData.add(sensor.getName());
        }


        RecyclerViewArrayAdapter listAdapter = new RecyclerViewArrayAdapter(fakeData, this);


        mSensorsList.setAdapter(listAdapter);

        mSensorsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.d("REC", "Dy=" + dy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                getAccelerometer(event, mSensorsList);
                break;
            case Sensor.TYPE_LIGHT:
//                Snackbar.make(mCollapsingToolbarLayout, "Light:" + event.values[0], Snackbar.LENGTH_SHORT)
//                        .show();
                break;
            default:
                break;


        }
    }

    private void getAccelerometer(SensorEvent event, View view) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];


        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Snackbar.make(view, "Device was shuffled " + accelationSquareRoot, Snackbar.LENGTH_SHORT)
                    .show();

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        adapter.addFragment(FragmentTest.newInstance(), "ONE");
//        adapter.addFragment(FragmentTest.newInstance(), "TWO");
//        adapter.addFragment(FragmentTest.newInstance(), "THREE");
//        viewPager.setAdapter(adapter);
//    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
