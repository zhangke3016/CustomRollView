package path.example.com.mycustom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by win7 on 2016/5/31.
 * 展示一个滚动条效果
 */
public class RollView extends FrameLayout{

    /** 改变布局*/
    private boolean ischange = false;
    /** */
    private Scroller mScroller;
    /** 高度*/
    private int mHeight ;
    /** 宽度*/
    private int mWidth ;
    /** 文字改变前的监听   在这里给控件设置内容*/
    private OnPreTextChangeListener mlistener;
    public void setOnPreTextChangeListener(OnPreTextChangeListener mlistener){
        this.mlistener = mlistener;
    }
    public RollView(Context context) {
        this(context, null);
    }

    public RollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽高
        mHeight = getChildAt(0).getMeasuredHeight();
         mWidth = getChildAt(0).getMeasuredWidth();
    }

    @Override
    public void computeScroll() {
        //判断scroller是否滚动结束
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }else if (getScrollY() == mHeight){
            //改变设置布局参数
            ischange = !ischange;

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScroller.startScroll(0, 0, 0, mHeight, 2000);
                    scrollTo(0, 0);
                    requestLayout();

                    if(getChildAt(0).getBottom() == mHeight){
                        mlistener.SetOnPreTextChangeListener((TextView) getChildAt(0));
                    }else if(getChildAt(1).getBottom() == mHeight){
                        mlistener.SetOnPreTextChangeListener((TextView) getChildAt(1));
                    }
                    postInvalidate();
                }
            },3000);
        }else if(getScrollY() == 0){
            //第一次进来
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScroller.startScroll(0, 0, 0, mHeight,2000);
                    postInvalidate();
                }
            },3000);
        }
    }

    public interface OnPreTextChangeListener{
        void SetOnPreTextChangeListener(TextView tv);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() != 2){
            new IllegalArgumentException("must be has 2 children.");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mScroller.abortAnimation();
        getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e("TAG","onLayout");
        //来回切换内部两个控件的位置   形成来回滑动效果
            if(ischange){
                getChildAt(1).layout(0,0,mWidth,mHeight);
                getChildAt(0).layout(0,mHeight,mWidth,mHeight*2);
            }else{
                getChildAt(0).layout(0,0,mWidth,mHeight);
                getChildAt(1).layout(0,mHeight,mWidth,mHeight*2);
            }
    }
}
