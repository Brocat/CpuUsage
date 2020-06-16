package com.example.cpuusage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements IView {
    private  CpuSampler cpuSampler;
    private  Thread[] workThreads;
    private boolean isWorking = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cpuSampler = new CpuSampler(this);
        startMonitor();

        Button startTaskBtn = (Button)findViewById(R.id.startTask);
        startTaskBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isWorking == false)
                {
                    EditText editText = (EditText)findViewById(R.id.threadCount);
                    String numStr = editText.getText().toString();
                    int count = Integer.valueOf(numStr);
                    if(count<0)
                    {
                        count =0;
                    }
                    workThreads = new Thread[count];
                    for (int i = 0;i<workThreads.length;i++)
                    {
                        workThreads[i] = new working();
                        workThreads[i].start();
                    }
                    isWorking = true;
                }
            }
        });

        Button stopTaskBtn = (Button)findViewById(R.id.stopTask);
        stopTaskBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isWorking ==true)
                {
                    for (int i = 0 ;i<workThreads.length;i++)
                    {
                        workThreads[i].interrupt();
                        workThreads[i]=null;
                    }
                    workThreads = null;
                    isWorking = false;
                }
            }
        });
    }

    @Override
    public void UpdateUsage(double cpuRate)
    {
        TextView textView = (TextView)findViewById(R.id.label);
        int usage = (int) (cpuRate *100);
        String content =  "cpu:"+ usage + "%";
        textView.setText(content);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update here!
                            cpuSampler.doSamplerAction();
                        }
                    });
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
            }
        }
    };


    private  void startMonitor()
    {
        Thread thread = new Thread(runnable);
        thread.start();
    }
    private class working extends Thread
    {
        @Override
        public void run() {
            super.run();
            while (isInterrupted() == false)
            {
                for (int i = 0;i<Short.MAX_VALUE;i++)
                {
                    float f = 1/3;
                }
            }
        }
    }
}