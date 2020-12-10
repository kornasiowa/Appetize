package com.kornasdominika.appetize.cookbook.showrecipe.preparation.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.model.Step;

import java.util.List;

public class StepsListAdapter extends ArrayAdapter<Step> {

    private final Activity context;
    private final List<Step> stepsList;

    private TextView tvCount,
            tvTime,
            tvDescription;
    private ImageView ivSetTimer;

    public StepsListAdapter(@NonNull Activity context, @NonNull List<Step> objects) {
        super(context, R.layout.list_element_step, objects);
        this.context = context;
        this.stepsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_element_step, null, true);

        findComponentsByIds(view);
        setComponents(position);
        setOnClick(position);

        return view;
    }

    private void findComponentsByIds(View view) {
        tvCount = view.findViewById(R.id.step_count);
        tvDescription = view.findViewById(R.id.description);
        tvTime = view.findViewById(R.id.step_time);
        ivSetTimer = view.findViewById(R.id.set_timer);
    }

    private void setComponents(int position) {
        tvCount.setText(String.format("%s", stepsList.get(position).getStep()));
        tvDescription.setText(String.format("%s", stepsList.get(position).getDescription()));
        tvTime.setText(String.format("%s", stepsList.get(position).getDuration()));
    }

    private void setOnClick(int position) {
        ivSetTimer.setOnClickListener(view -> {
            createTimerDialog(stepsList.get(position).getDuration());
        });
    }

    private void createTimerDialog(int cookingTime) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Set timer");
        dialog.setMessage("Do you want to start the cooking time counting down " + cookingTime + " minutes?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            setTimer(cookingTime);
        });

        dialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void setTimer(int cookingTime) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_LENGTH, cookingTime)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        context.startActivity(intent);
    }
}
