package com.cd.keith.averagelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by keith wang on 2017/3/10.
 */

public class AverageLayout extends ViewGroup {


    public AverageLayout(Context context) {
        super(context);
    }

    public AverageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int width = 0, height = 0;

        for(int i = 0;  i < childCount; i++){
            View childVew = getChildAt(i);
            View childView = getChildAt(i);
            width += childView.getMeasuredWidth();
            if(height < childVew.getMeasuredHeight()){
                height = childVew.getMeasuredHeight();
            }
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if(childCount == 0){
            return;
        }
        int width = getWidth();
        int height = getHeight();
        if(childCount == 1){
            View childView = getChildAt(0);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            int left = (width - childWidth)/2;
            int top = (height - childHeight)/2;
            childView.layout(left, top, childWidth+left, childHeight+top);
        }
        if(childCount == 2){
            View childView1 = getChildAt(0);
            View childView2 = getChildAt(1);

            int topChildView1 = (height - childView1.getMeasuredHeight())/2;
            childView1.layout(0, topChildView1, childView1.getMeasuredWidth(), childView1.getMeasuredHeight()+topChildView1);

            int topChildView2 = (height - childView2.getMeasuredHeight())/2;
            childView2.layout(width - childView2.getMeasuredWidth(), topChildView2, width ,childView2.getMeasuredHeight()+topChildView2);
        }

        int avgSpaceWidth, sumWidth = 0;
        for(int i = 0;  i < childCount; i++){
            View childView = getChildAt(i);
            sumWidth += childView.getMeasuredWidth();
        }
        avgSpaceWidth = (width - sumWidth)/(childCount-1);
        int currentWidth = 0;
        for(int i = 0;  i < childCount; i++){
            View childView = getChildAt(i);
            int childViewTop = (height - childView.getMeasuredHeight())/2;
            if(i == 0){
                childView.layout(currentWidth, childViewTop, currentWidth + childView.getMeasuredWidth(), childView.getMeasuredHeight()+childViewTop);
            }else{
                childView.layout(currentWidth+avgSpaceWidth, childViewTop, currentWidth+avgSpaceWidth+childView.getMeasuredWidth() ,childView.getMeasuredHeight()+childViewTop);
            }
            currentWidth = childView.getRight();
        }
    }
}
