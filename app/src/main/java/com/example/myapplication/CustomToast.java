package com.example.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

public class CustomToast {

    public CustomToast(Context context, String message, String type) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayoutCompat layout = (LinearLayoutCompat) inflater.inflate(R.layout.custom_toast, null);
        AppCompatTextView textView = layout.findViewById(R.id.message);

        if (type.equals("Success")) {
            layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.custom_toast_success));
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            textView.setTextSize(16f);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(message);

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }else if (type.equals("Fail")){
            layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.custom_toast_fail));
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            textView.setTextSize(16f);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(message);

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } else {
            layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.custom_toast_normal));
            textView.setTextColor(ContextCompat.getColor(context, R.color.black));
            textView.setTextSize(16f);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(message);

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

    }
}
