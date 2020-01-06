package bd.com.nabdroid.openvote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView addVoteIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        addVoteIV.setOnClickListener(this);
    }

    private void init() {
        addVoteIV = findViewById(R.id.addVoteId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.addVoteId){
            startActivity(new Intent(HomeActivity.this, PostVoteActivity.class));
        }
    }
}
