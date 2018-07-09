package com.example.administrator.testdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;



/**
 * 作者：lily on 2018/7/1 0001.
 * 邮箱：1369246510@qq.com
 */

public class RoundChatView extends View {

    float orderMoney=-1;
    float coupon=-1;
    float award=-1;

    float orderPercent;
    float couponPercent;
    float awardPercent;
    Paint paint=new Paint();

    Paint whitePotPaint=new Paint();
    Paint linePaint=new Paint();
    Paint textPaint=new TextPaint();


    float dp50, dp54, dp56;

    RectF mRect=new RectF();
    RectF middleRect=new RectF();
    RectF largeRect=new RectF();

    int screenWidth;
    private float totalMoney;
    private RectF smallLeastRect=new RectF();
    private float orderMoneyLength;
    private float awardLength;
    private float couponLength;
    private int dp22;
    private int dp8;
    private int dp5;
    private int dp3;
    private int dp58;


    public RoundChatView(Context context) {
        super(context);
        init(context);
    }

    public RoundChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RoundChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context){


        paint.setColor(Color.parseColor("#27aae1"));
        paint.setAntiAlias(true);
        screenWidth= Util.getScreenWidth(context);
        dp50 =Util.dip2px(context,50);
        dp54 =Util.dip2px(context,54);
        dp56 =Util.dip2px(context,56);
        dp58 =Util.dip2px(context,58);
        dp22 =Util.dip2px(context,22);
        dp8 =Util.dip2px(context,8);
        dp5 =Util.dip2px(context,5);
        dp3 = Util.dp2px(getContext(), 3);

        int circleOutMargin=Util.dip2px(context,45);
        int smallTop=getTop()+Util.dip2px(context,9f)+circleOutMargin;
        int middleTop=getTop()+Util.dip2px(context,5f)+circleOutMargin;
        int largeTop=getTop()+Util.dip2px(context,1f)+circleOutMargin;
        // smallRect.set(screenWidth/2-Util.dip2px(context,25),smallTop,screenWidth/2+Util.dip2px(context,25),smallTop+Util.dip2px(context,50));
        smallLeastRect.set(screenWidth/2-dp50,smallTop,screenWidth/2+dp50,smallTop+Util.dip2px(context,100));

        middleRect.set(screenWidth/2-dp54,middleTop,screenWidth/2+dp54,middleTop+Util.dip2px(getContext(),108));

        largeRect.set(screenWidth/2-dp58,largeTop,screenWidth/2+dp58,largeTop+Util.dip2px(context,116));
        whitePotPaint.setColor(Color.parseColor("#ffffff"));
        linePaint.setColor(Color.parseColor("#333333"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);
        textPaint.setTextSize(Util.dip2px(getContext(),14));
        textPaint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(screenWidth,Util.dip2px(getContext(),180));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    Path path=new Path();
    PathMeasure pathMeasure=new PathMeasure();
    float[] pos=new float[2];  //用来存圆弧上点的xy 坐标
    float[] tan =new float[2];
    Path xiePath=new Path();
    Path horizontalPath=new Path();

    public void setOrderMoney(float orderMoney,float coupon,float award) {
        this.orderMoney = orderMoney;
        this.coupon = coupon;
        this.award = award;
        float totalMoney=orderMoney+coupon+award;
        if (totalMoney!=0) {
            orderPercent = orderMoney * 1.00f / (totalMoney) * 1.00f;
            couponPercent = coupon * 1.00f / totalMoney * 1.00f;
            awardPercent = award * 1.00f / totalMoney * 1.00f;
            if (orderPercent != 0) {
                orderMoneyLength = textPaint.measureText(orderMoney + "");
            }
            if (couponPercent != 0) {
                couponLength = textPaint.measureText(coupon + "");
            }
            if (awardPercent != 0) {
                awardLength = textPaint.measureText(award + "");
            }
            invalidate();
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (coupon==-1||orderMoney==-1)return;
        mRect.set(middleRect);
        paint.setColor(Color.parseColor("#ffab00"));
        int whitePotRadius=Util.dip2px(getContext(),2);
        if (awardPercent !=0){
            drawPattern(canvas,0,360* awardPercent, whitePotRadius,award,"award");
        }
        mRect.set(smallLeastRect);
        paint.setColor(Color.parseColor("#27aae1"));
        if (orderPercent !=0){
            drawPattern(canvas,360* awardPercent,360* orderPercent, whitePotRadius,orderMoney,"order");
        }
        mRect.set(largeRect);
        paint.setColor(Color.parseColor("#be1e2d"));

        if (couponPercent !=0){
            drawPattern(canvas,360*(awardPercent + orderPercent),360*couponPercent,whitePotRadius,coupon,"coupon");
        }



    }

    private void drawPattern(Canvas canvas, float startAngle, float endAngle, int whitePotRadius, float value,String type) {
        canvas.drawArc(mRect,startAngle,endAngle,true,paint);
        path.reset();
        path.addArc(mRect,startAngle,endAngle);
        pathMeasure.setPath(path,false);
        pathMeasure.getPosTan(pathMeasure.getLength()/2,pos,tan);
        xiePath.reset();
        horizontalPath.reset();
        double degree=Math.atan2(tan[1], tan[0])*180/Math.PI;
        if (degree>=90&&degree<=180){
            canvas.drawCircle(pos[0]- dp8,pos[1]-dp3,whitePotRadius,whitePotPaint);

            xiePath.moveTo(pos[0]- dp8+whitePotRadius,pos[1]- dp3);
            xiePath.lineTo(pos[0]- dp8+whitePotRadius+dp22,pos[1]-dp3+dp22);
            horizontalPath.moveTo(pos[0]- dp8+whitePotRadius+dp22,pos[1]-dp3+dp22);
            horizontalPath.lineTo(pos[0]- dp8+whitePotRadius+dp22+dp50,pos[1]+dp22);

        }else if (degree>-180&&degree<=-90){
            canvas.drawCircle(pos[0]+dp8,pos[1]-dp8,whitePotRadius,whitePotPaint);
            horizontalPath.moveTo(pos[0]+dp8-whitePotRadius-dp22-dp50,pos[1]-dp8+dp22);
            horizontalPath.lineTo(pos[0]+dp8-whitePotRadius-dp22,pos[1]-dp8+dp22);
            xiePath.moveTo(pos[0]+dp8-whitePotRadius,pos[1]-dp8);
            xiePath.lineTo(pos[0]+dp8-whitePotRadius-dp22,pos[1]-dp8+dp22);


        }else if (degree<0&&degree>-90){
            canvas.drawCircle(pos[0]+ dp8,pos[1],whitePotRadius,whitePotPaint);


            horizontalPath.moveTo(pos[0]+ dp8-whitePotRadius-dp22-dp50,pos[1]-dp22);
            horizontalPath.lineTo(pos[0]+ dp8-whitePotRadius-dp22,pos[1]-dp22);
            xiePath.moveTo(pos[0]+ dp8-whitePotRadius-dp22,pos[1]-dp22);
            xiePath.lineTo(pos[0]+ dp8-whitePotRadius,pos[1]);
        }else if(degree>=0&&degree<90){
            canvas.drawCircle(pos[0]- dp8,pos[1]+dp8,whitePotRadius,whitePotPaint);

            xiePath.moveTo(pos[0]+whitePotRadius-dp8,pos[1]+dp8);
            xiePath.lineTo(pos[0]+whitePotRadius-dp8+dp22,pos[1]+dp8-dp22);
            horizontalPath.moveTo(pos[0]+whitePotRadius-dp8+dp22,pos[1]+dp8-dp22);
            horizontalPath.lineTo(pos[0]+whitePotRadius-dp8+dp22+dp50,pos[1]+dp8-dp22);
        }

        canvas.drawPath(xiePath,linePaint);
        canvas.drawPath(horizontalPath,linePaint);
        if (type.equals("order")&&orderMoneyLength!=0){
            canvas.drawTextOnPath(value+"",horizontalPath,(dp50-orderMoneyLength)/2,-10,textPaint);

        }else if (type.equals("award")&&awardLength!=0){
            canvas.drawTextOnPath(value+"",horizontalPath,(dp50-awardLength)/2,-10,textPaint);

        }else if (type.equals("coupon")&&couponLength!=0){
            canvas.drawTextOnPath(value+"",horizontalPath,(dp50-couponLength)/2,-10,textPaint);

        }

    }
}
