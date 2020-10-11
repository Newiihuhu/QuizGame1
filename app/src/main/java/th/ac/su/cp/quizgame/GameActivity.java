package th.ac.su.cp.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.cp.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];

    private String mAnswerWord;
    private Random mRandom;
    private List<WordItem> mItemList;
    private int countScore = 0;
    private TextView score;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));

        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);


        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        mRandom = new Random();
        //Ctrl+Alt+m = refacter
        newQuiz(mItemList, mRandom);
    }

    private void newQuiz(List<WordItem> mItemList, Random mRandom) {
        int answerIndex = mRandom.nextInt(mItemList.size());
        WordItem item = mItemList.get(answerIndex);

        mQuestionImageView.setImageResource(item.imageResId);

        mAnswerWord = item.word;

        int randomButton = mRandom.nextInt(4);
        mButtons[randomButton].setText(item.word);
        mItemList.remove(item);
        Collections.shuffle(mItemList);

        for(int i = 0; i < 4; i++){
            if(i == randomButton){
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);
        }
    }

    @Override
    public void onClick(View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();
        score = findViewById(R.id.score_text);
        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        mRandom = new Random();
        if(count < 4){
            if(buttonText.equals(mAnswerWord)){
                countScore = countScore + 1;
                score.setText(countScore+" คะแนน");
                count++;
                newQuiz(mItemList, mRandom);
                //Toast.makeText(GameActivity.this, "ถูกต้อง", Toast.LENGTH_SHORT).show();
            }else{
                score.setText(countScore+" คะแนน");
                count++;
                newQuiz(mItemList, mRandom);
                //Toast.makeText(GameActivity.this, "ผิด", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(buttonText.equals(mAnswerWord)){
                countScore = countScore + 1;
                score.setText(countScore+" คะแนน");
                //Toast.makeText(GameActivity.this, "ถูกต้อง", Toast.LENGTH_SHORT).show();
            }else{
                score.setText(countScore+" คะแนน");
                //Toast.makeText(GameActivity.this, "ผิด", Toast.LENGTH_SHORT).show();
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
            dialog.setTitle("สรุปผล");
            dialog.setMessage("คุณได้ "+countScore+" คะแนน\n\nต้องการเริ่มเกมใหม่หรือไม่");
            dialog.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    count = 0;
                    countScore = 0;
                    score.setText("");
                    newQuiz(mItemList, mRandom);
                }
            });
            dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.show();
        }

    }
}