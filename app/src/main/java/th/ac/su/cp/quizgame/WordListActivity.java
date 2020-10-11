package th.ac.su.cp.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import th.ac.su.cp.quizgame.model.WordItem;

public class WordListActivity extends AppCompatActivity {
    static public WordItem[] items = {
            new WordItem(R.drawable.cat, "CAT"),
            new WordItem(R.drawable.dog, "DOG"),
            new WordItem(R.drawable.dolphin, "DOLPHIN"),
            new WordItem(R.drawable.tiger, "TIGER"),
            new WordItem(R.drawable.lion, "LION"),
            new WordItem(R.drawable.penguin, "PENGUIN"),
            new WordItem(R.drawable.rabbit, "RABBIT"),
            new WordItem(R.drawable.pig, "PIG"),
            new WordItem(R.drawable.koala, "KOALA")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        // สร้าง Layout manager
        LinearLayoutManager lm = new LinearLayoutManager(WordListActivity.this);

        List<WordItem> wordList = Arrays.asList(items);
        // สร้าง Adapter object
        MyAdapter adapter = new MyAdapter(WordListActivity.this,wordList);


        // เข้าถึง RecyclerView ใน  layout
        RecyclerView rv = findViewById(R.id.word_list_recycler_view);
        rv.setLayoutManager(lm); // กำหนด layout manager ให้กับ RecyclerView
        rv.setAdapter(adapter); // กำหนด adapter ให้กับ RecyclerView
    }

}
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    final Context mContext;
    final List<WordItem> mWordList;
    //        WordItem[] items = {
//                new WordItem(R.drawable.cat,"CAT"),
//                new WordItem(R.drawable.dog, "DOG")
//        };



    //Alt+insert
    public MyAdapter(Context context , List<WordItem> WordList) {
        this.mContext = context;
        this.mWordList = WordList;
    }
    //Ctrl+O
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);
        MyViewHolder vh = new MyViewHolder(mContext,v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(mWordList.get(position).imageResId);
        holder.wordTextView.setText(mWordList.get(position).word);
        holder.item = mWordList.get(position);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        View rootView;
        ImageView imageView;
        TextView wordTextView;
        WordItem item;

        public MyViewHolder(final Context context ,@NonNull final View itemView) {
            super(itemView);
            rootView = itemView;
            imageView = itemView.findViewById(R.id.image_view);
            wordTextView = itemView.findViewById(R.id.word_text_view);

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, item.word, Toast.LENGTH_SHORT).show();

                    /*AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("MyDialog");
                    dialog.setMessage(item.word);
                    dialog.setPositiveButton("OK",null);
                    dialog.show();*/

                    //new AlertDialog.Builder(context).setTitle("MyDialog").setMessage(item.word).setPositiveButton("OK",null).show();
                    Intent i = new Intent(context,WordDetailsActivity.class);
                    //i.putExtra("word", item.word);
                    //i.putExtra("image", item.imageResId);
                    String itemJson = new Gson().toJson(item);
                    i.putExtra("item",itemJson);
                    context.startActivity(i);
                }
            });
        }
    }
}


