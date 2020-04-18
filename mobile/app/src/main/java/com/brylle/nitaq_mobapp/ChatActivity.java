package com.brylle.nitaq_mobapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.hypelabs.hype.Hype;
import com.hypelabs.hype.Instance;
import com.hypelabs.hype.Message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    private String subject;
    private String topic;
    private String adventure;
    private ArrayList<String> concepts;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> correct_answers;
    private ArrayList<String> titles;

    public static String INTENT_EXTRA_STORE = "com.hypelabs.store";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);

        // Retrieve details from intent
        Bundle extras = getIntent().getExtras();
        subject = extras.getString("pkgSubject");
        topic = extras.getString("pkgTopic");
        adventure = extras.getString("pkgModule");
        concepts = (ArrayList<String>) extras.get("pkgLessons");
        questions = (ArrayList<String>) extras.get("pkgQuestions");
        answers = (ArrayList<String>) extras.get("pkgAnswers");
        correct_answers = (ArrayList<String>) extras.get("pkgCorrectAnswers");
        titles = (ArrayList<String>) extras.get("pkgNext");

        Log.d("DEBUG", subject);
        Log.d("DEBUG", topic);
        Log.d("DEBUG", adventure);
        Log.d("DEBUG", String.valueOf(concepts));
        Log.d("DEBUG", String.valueOf(questions));
        Log.d("DEBUG", String.valueOf(answers));
        Log.d("DEBUG", String.valueOf(correct_answers));
        Log.d("DEBUG", String.valueOf(titles));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                scrollView.scrollTo(0, scrollView.getBottom());
                if(!messageText.equals("")){
                    addMessageBox(messageText, 2);
                    messageArea.setText("");

                    new CountDownTimer(2000, 1000) {
                        public void onFinish() {
                            addMessageBox("I'm a bot!", 3);

                            new CountDownTimer(2000, 1000) {
                                public void onFinish() {
                                    addMessageBox("I'm another player!", 1);
                                }

                                public void onTick(long millisUntilFinished) {
                                    // millisUntilFinished    The amount of time until finished.
                                }
                            }.start();
                        }

                        public void onTick(long millisUntilFinished) {
                            // millisUntilFinished    The amount of time until finished.
                        }
                    }.start();
                }
            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(3,3,3,3);
        lp2.weight = 7.0f;
        textView.setPadding(25,15,25,25);
        textView.setTextSize(20);

        // type 1 for other players, type2 for your own message, type 3 for GM
        if(type == 1) {
            TextView textView1 = new TextView(ChatActivity.this);
            textView1.setText("Other Player");
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
            textView.setTextColor(Color.WHITE);
            textView1.setTextColor(Color.GRAY);
            textView1.setLayoutParams(lp2);
            layout.addView(textView1);
        }
        else if (type==2){
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
            textView.setTextColor(Color.WHITE);
        }
        else{
            TextView textView1 = new TextView(ChatActivity.this);
            textView1.setText("Game Master");
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_gm);
            textView.setTextColor(Color.WHITE);
            textView1.setTextColor(Color.GRAY);
            textView1.setLayoutParams(lp2);
            layout.addView(textView1);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.scrollTo(0, scrollView.getBottom());
    }

    protected Message sendMessage(String text, Instance instance, boolean acknowledge) throws UnsupportedEncodingException {

        // When sending content there must be some sort of protocol that both parties
        // understand. In this case, we simply send the text encoded in UTF-8. The data
        // must be decoded when received, using the same encoding.
        byte[] data = text.getBytes("UTF-8");

        return Hype.send(data, instance, acknowledge);
    }

}
