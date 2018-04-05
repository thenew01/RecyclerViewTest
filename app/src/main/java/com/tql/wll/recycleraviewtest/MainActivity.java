package com.tql.wll.recycleraviewtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<Fruit> fruitList = new ArrayList<>();

    private IntentFilter intentFilter;
    NetworkChangeReceiver netChangeReceiver;

    private IntentFilter myIntentFilter;
    MyBroadcastReceiver myBroadcastReceiver;

    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter localIntentFilter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Toast.makeText(MainActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Toast.makeText(MainActivity.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    Toast.makeText(MainActivity.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(netChangeReceiver, intentFilter);

        //直接在配置文件里静态注册收不到，就算加了权限

        myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.tql.wll.recycleraviewtest.MY_BROADCAST");
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, myIntentFilter);

        localReceiver = new LocalReceiver();
        localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.tql.wll.recycleraviewtest.LOCAL_BROADCAST");
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(localReceiver,localIntentFilter);

        Button btn = (Button) findViewById(R.id.send_broadcast);
        btn.setOnClickListener( new View.OnClickListener() {
           @Override
            public void onClick(View v){
               Intent intent = new Intent("com.tql.wll.recycleraviewtest.MY_BROADCAST");
               //intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
               //intent.addCategory("com.tql.wll.recycleraviewtest.MY_BROADCAST");
               //sendBroadcast(intent);
               sendOrderedBroadcast(intent,null);
               intent.setAction("com.tql.wll.recycleraviewtest.LOCAL_BROADCAST");
               localBroadcastManager.sendBroadcast(intent);
               //Toast.makeText(MainActivity.this, "com.tql.wll.recycleraviewtest.MY_BROADCAST sent", Toast.LENGTH_SHORT).show();
           }
        });
        initFruits();

        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        //LinearLayoutManager lm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(lm);

        FruitAdapter adapter = new FruitAdapter(fruitList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(netChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void initFruits()  {
        int j = 0;
        for ( int i=0; i<20; i++){
            String name = String.format("name %d", j++);
            Fruit apple = new Fruit( getRandomLengthName(name), R.mipmap.fruit_1);
            fruitList.add(apple);

            name = String.format("name %d", j++);
            apple = new Fruit(getRandomLengthName(name), R.mipmap.fruit_2);
            fruitList.add(apple);
        }
    }

    private String getRandomLengthName(String s){
        Random r = new Random();
        int length = r.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();
        for ( int i=0;i<length;i++){
            sb.append(s);
        }
        return sb.toString();
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager cm = (ConnectivityManager)getSystemService( Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if( netInfo != null && netInfo.isAvailable() ){
                Toast.makeText(context, "network is available" , Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "network is not available" , Toast.LENGTH_SHORT).show();
            }
        }
    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "local broadcast is received" , Toast.LENGTH_SHORT).show();
        }
    }
}
