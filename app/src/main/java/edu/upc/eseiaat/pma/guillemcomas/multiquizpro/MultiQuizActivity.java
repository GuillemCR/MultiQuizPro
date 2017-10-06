package edu.upc.eseiaat.pma.guillemcomas.multiquizpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MultiQuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    private int correct_ans;
    private int current_quest;
    private int[] answer;
    private String[] all_questions;
    private TextView text_question;
    private Boolean[] num_correct;
    private RadioGroup group;
    private Button btn_next;
    private Button btn_prev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_quiz);

        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.answer_group);
        btn_next = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);

        all_questions = getResources().getStringArray(R.array.all_questions);
        num_correct= new Boolean[all_questions.length];
        answer= new int[all_questions.length];
        for (int i=0; i<answer.length;i++) {
            answer[i]=-1;
        }
        current_quest=0;
        showQuest();
        //TODO: commando per llistat de coses a fer!
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();

                if(current_quest < all_questions.length-1){
                    current_quest++;
                    showQuest();
                } else {
                    int correct=0, incorrect=0;
                    for (boolean b: num_correct) {
                        if (b) correct++;
                        else incorrect++;
                    }

                    String num_correct = getResources().getString(R.string.corrects);
                    String num_incorrect = getResources().getString(R.string.incorrects);
                    Toast.makeText(MultiQuizActivity.this, num_correct+": "+correct+
                            " -- "+num_incorrect+": "+incorrect, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
                if (current_quest>0) {
                    current_quest--;
                    showQuest();
                }
            }
        });
    }

    private void showQuest() {
        String quest = all_questions[current_quest];
        String[] parts = quest.split(";");

        text_question.setText(parts[0]);

        group.clearCheck();

        for (int i=0; i<ids_answers.length; i++) {
            RadioButton rb1 = (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if (ans.charAt(0) == '*') {
                correct_ans = i;
                ans = ans.substring(1);
            }
            rb1.setText(ans);
            if (answer[current_quest] == i) {
                rb1.setChecked(true);
            }
        }
        if (current_quest==0){
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }

        if (current_quest== all_questions.length-1) {
            btn_next.setText(R.string.finish);
        } else {
            btn_next.setText(R.string.next);
        }
    }

    private void verify(){
        int id= group.getCheckedRadioButtonId();
        int ans=-1;

        for (int i=0; i<ids_answers.length; i++) {
            if(ids_answers[i] == id) ans=i;
        }

        num_correct[current_quest]= (ans == correct_ans);
        answer[current_quest] = ans;
    }
}

