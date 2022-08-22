package com.example.kiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment_Menu fragment_menu;
    private Fragment_Order fragment_order;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragment_menu = new Fragment_Menu();
        fragment_order = new Fragment_Order();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_frame, fragment_menu).commitAllowingStateLoss();
    }

    public void clickHandler(View view)
    {
        fragmentTransaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.btn_go_order:
                fragmentTransaction.replace(R.id.layout_frame, fragment_order).commitAllowingStateLoss();
                break;

            case R.id.btn_back:
                fragmentTransaction.replace(R.id.layout_frame, fragment_menu).commitAllowingStateLoss();
                break;

            case R.id.btn_submit_order:
                Intent intent = new Intent(getApplicationContext(), PopupFinish.class);
                startActivity(intent);
                break;
        }
    }
}