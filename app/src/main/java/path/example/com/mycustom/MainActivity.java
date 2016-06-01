package path.example.com.mycustom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int num = 0;

    private String[] mStrData = {"苏宁看不下去，全场免费送！","58同城来凑热闹!","京东大促,全场5元!","天猫不服,全场不要钱!"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RollView rv = (RollView) findViewById(R.id.rollview);
        Button mbtn_jingdong = (Button) findViewById(R.id.jingdong);
        Button mbtn_tianmao = (Button) findViewById(R.id.tianmao);

        mbtn_jingdong.setOnClickListener(this);
        mbtn_tianmao.setOnClickListener(this);
        rv.setOnPreTextChangeListener(new RollView.OnPreTextChangeListener() {
            @Override
            public void SetOnPreTextChangeListener(TextView tv) {
               if(num >= mStrData.length){
                   num = 0;
               }
                tv.setText(mStrData[num]);
                num++;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jingdong:
            case R.id.tianmao:
                Toast.makeText(this,((TextView)v).getText().toString().trim(),Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
