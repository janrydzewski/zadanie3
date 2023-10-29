package com.example.zadanie3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import com.example.zadanie3.R;

import java.util.UUID;

public class TaskFragment extends Fragment {

    private Task task;
    private EditText editText;
    private Button button;
    private CheckBox checkBox;
    public static final String ARG_TASK_ID = "task_id";

    public TaskFragment() {
    }

    public static TaskFragment newInstance(UUID taskId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        task = TaskStorage.getInstance().getTask(taskId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        {
            View view = inflater.inflate(R.layout.fragment_task, container, false);

            checkBox = view.findViewById(R.id.task_done);
            checkBox.setChecked(task.isDone());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> task.setDone(isChecked));

            editText = view.findViewById(R.id.task_name);
            editText.setText(task.getName());
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    task.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            button = view.findViewById(R.id.task_date);
            button.setText(task.getDate().toString());
            button.setEnabled(false);

            return view;
        }
    }
}
