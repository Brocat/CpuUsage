package com.example.cpuusage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IView {
    private  CpuSampler cpuSampler;
    private  Thread workThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cpuSampler = new CpuSampler(this);
        start();

        Button startTaskBtn = (Button)findViewById(R.id.startTask);
        startTaskBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                    workThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i =0;i<Integer.MAX_VALUE;i++)
                            {
                                for (int j = 0; j< Integer.MAX_VALUE; j++)
                                {
                                    float f = 1/3;
                                }
                            }
                        }
                    });

                workThread.start();
            }
        });

        Button stopTaskBtn = (Button)findViewById(R.id.stopTask);
        stopTaskBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                workThread.interrupt();
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


    private  void start()
    {
        Thread thread = new Thread(runnable);
        thread.start();
    }

}