package com.example.alex.nuryschedulev2;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by alex on 17.06.15.
 */
public class MyOnItemClickListener implements AdapterView.OnItemClickListener {

    private Context context;

    public MyOnItemClickListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, LessonItemFragmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
