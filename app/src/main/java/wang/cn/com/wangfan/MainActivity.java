package wang.cn.com.wangfan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WangFanView wangFanView;
    private Float a = 35f;
    private Float b = 75f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wangFanView = findViewById(R.id.wang_fan_view);

        wangFanView.setRadius(DensityUtils.dp2px(MainActivity.this, 110f));


        float newA = (float) (a);
        float newB = a;
        float newC = (float) (a+b*0.1);
        float newD = (float) (a+b*0.2);
        float newE = (float) (a+b*0.2);
        float newF = (float) (a+b*0.4);
        float newG = (float) (a+b*0.6);
        float newH = (float) (a+b*0.8);
        float newI = a+b;
        float newJ = a+b;
        float newK = (float) (a+b*0.8);
        float newL = (float) (a+b*0.6);
        float newM = (float) (a+b*0.4);
        float newN = (float) (a+b*0.2);
        float newO = (float) (a+b*0.2);
        float newP = (float) (a+b*0.1);
        float newQ = a;
        float newR = (float) (a);

        List<WangFanView.SectorAny> pieEntries = new ArrayList<>();
        pieEntries.add(new WangFanView.SectorAny(1, newA));
        pieEntries.add(new WangFanView.SectorAny(1, newB));
        pieEntries.add(new WangFanView.SectorAny(1, newC));
        pieEntries.add(new WangFanView.SectorAny(1, newD));
        pieEntries.add(new WangFanView.SectorAny(1, newE));
        pieEntries.add(new WangFanView.SectorAny(1, newF));
        pieEntries.add(new WangFanView.SectorAny(1, newG));
        pieEntries.add(new WangFanView.SectorAny(1, newH));
        pieEntries.add(new WangFanView.SectorAny(1, newI));
        pieEntries.add(new WangFanView.SectorAny(1, newJ));
        pieEntries.add(new WangFanView.SectorAny(1, newK));
        pieEntries.add(new WangFanView.SectorAny(1, newL));
        pieEntries.add(new WangFanView.SectorAny(1, newM));
        pieEntries.add(new WangFanView.SectorAny(1, newN));
        pieEntries.add(new WangFanView.SectorAny(1, newO));
        pieEntries.add(new WangFanView.SectorAny(1, newP));
        pieEntries.add(new WangFanView.SectorAny(1, newQ));
        pieEntries.add(new WangFanView.SectorAny(1, newR));

        wangFanView.setWangAnies(pieEntries);
    }
}
