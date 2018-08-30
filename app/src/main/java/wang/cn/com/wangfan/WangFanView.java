package wang.cn.com.wangfan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wangZL
 * @description:
 * @projectName: Wangfan
 * @date: 2018-08-29
 * @time: 15:47
 */
public class WangFanView extends View {

    private List<SectorAny> sectorAnyList = new ArrayList<>();
    private Paint paint;
    private Paint paintRed;
    //中心坐标
    private float centerX;
    private float centerY;
    private float radius;

    private float[] newNumber = {0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0};

    // 线程控制
    private boolean display = true;
    // 是否开启动画效果
    private boolean animMode = true;
    private static final int STOPNOW= 113;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < sectorAnyList.size(); i++){
                        if (msg.what == 1 &&  newNumber[i] < DensityUtils.dp2px(getContext(),
                                sectorAnyList.get(i).sum)) {
                            newNumber[i] += DensityUtils.dp2px(getContext(), sectorAnyList.get(i).sum)/40;
                        }
                    }
                    int size = 0;
                    for (int i = 0; i < sectorAnyList.size(); i++){
                        if (newNumber[i] == DensityUtils.dp2px(getContext(), sectorAnyList.get(i).sum)){
                            size++;
                        }
                    }

                    if (size == sectorAnyList.size()){
                        display = false;
                    }

                    invalidate();
                    break;
                case STOPNOW:
                    display = false;
                    break;
                default:
                    break;
            }
        }
    };

    public WangFanView(Context context) {
        this(context,null);
    }

    public WangFanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WangFanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int total = 0;

        for (int i = 0; i < sectorAnyList.size(); i++){
            total += sectorAnyList.get(i).getNumber();
        }

        centerX = getPivotX();
        centerY = getPivotY();

        if (radius == 0){
            radius = getWidth()/2;
        }

        setFan(canvas);

        //计算角度,毕竟半圆
        float sweep = -180/sectorAnyList.size();
        //将当前扇形的结束角度作为下一个扇形的起始角度
        for (int i = 0; i < sectorAnyList.size(); i++){
            setRadiusNew(canvas,i,sweep);
        }
    }

    public void setFan(Canvas canvas) {

        RectF rectF = new RectF(centerX-radius, centerY-radius,
                centerX+radius, centerY+radius);
        canvas.drawArc(rectF, -180, 180, true, paintRed);
    }

    /**
     *  画扇形
     * @param canvas
     * @param i
     * @param sweep
     */
    public void setRadiusNew(Canvas canvas,int i,float sweep) {

        //画扇形的方法
        float startWang = sweep*i;
        //做增长
        RectF rectF = new RectF(centerX - newNumber[i], centerY - newNumber[i],
                centerX + newNumber[i], centerY + newNumber[i]);
        canvas.drawArc(rectF, startWang-1, sweep+2, true, paint);
        //将每个扇形的起始角度 和 结束角度 放入对应的对象
        sectorAnyList.get(i).setStartC(startWang);
        sectorAnyList.get(i).setEndC(startWang + sweep);

    }

    /**
     *  设置半径
     * @param r
     */
    public void setRadius(float r) {
        this.radius = r;
    }


    /**
     * 设置数据并刷新
     */
    public void setWangAnies(List<SectorAny> data) {
        this.sectorAnyList = data;
        animMode = true;
        init();
    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.blue));

        paintRed = new Paint();
        paintRed.setTextSize(DensityUtils.sp2px(getContext(), 12));
        paintRed.setAntiAlias(true);
        paintRed.setStyle(Paint.Style.FILL);
        paintRed.setColor(getResources().getColor(R.color.red));

        if (animMode) {
            // 启动一个线程来实现缓慢增高
            display = true;
            new TThread().start();
            handler.sendEmptyMessageDelayed(STOPNOW, 2000);
        }

        handler.sendEmptyMessageDelayed(STOPNOW, 2000);
    }




    /**
     * 获取每个扇形的对象
     */
    public static class SectorAny {

        //扇形数量
        private int number;
        //扇形数值
        private float sum;
        //扇形起始角度
        private float startC;
        //扇形结束角度
        private float endC;

        public SectorAny(int number, float sum) {
            this.number = number;
            this.sum = sum;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public float getStartC() {
            return startC;
        }

        public void setStartC(float startC) {
            this.startC = startC;
        }

        public float getEndC() {
            return endC;
        }

        public void setEndC(float endC) {
            this.endC = endC;
        }
    }

    class TThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (!Thread.currentThread().isInterrupted() && display) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    System.err.println("InterruptedException！线程关闭");
                    this.interrupt();
                }
            }
        }
    }
}
