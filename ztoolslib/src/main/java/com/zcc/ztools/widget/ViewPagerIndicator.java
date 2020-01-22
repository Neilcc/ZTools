package com.zcc.ztools.widget;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cc on 2020-01-21.
 */
public class ViewPagerIndicator extends RecyclerView implements ViewPager.OnPageChangeListener {
    private IndicatorAdapter mIndicatorAdapter;
    private ViewPager mViewPager;

    public ViewPagerIndicator(@NonNull Context context) {
        super(context);
        init();
    }

    public ViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public <T extends IndicatorVH> void bindWidget(@NonNull IndicatorAdapter<T> indicatorAdapter, @NonNull ViewPager vp) {
        this.mIndicatorAdapter = indicatorAdapter;
        this.mViewPager = vp;
        vp.addOnPageChangeListener(this);
        setAdapter(mIndicatorAdapter);
        mIndicatorAdapter.setViewPager(mViewPager);
        if (mIndicatorAdapter.getItemCount() > 0 && getLayoutManager().getChildAt(0) != null) {
            mIndicatorAdapter.onIndicatorSelected
                    ((IndicatorVH) getChildViewHolder(getLayoutManager().getChildAt(0)), 0);
        }
        mIndicatorAdapter.mRv = ViewPagerIndicator.this;
        mIndicatorAdapter.notifyDataSetChanged();
    }

    private void init() {
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(HORIZONTAL);
        setLayoutManager(layoutManager);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //  首页没有滚动时候的indicator变化滑动需求 需要的自己加
    }

    @Override
    public void onPageSelected(int i) {
        if (mIndicatorAdapter != null
                && getAdapter() != null
                && getLayoutManager() != null
                && getLayoutManager().getChildAt(i) != null
                && getAdapter().getItemCount() > i
        ) {
            mIndicatorAdapter.onIndicatorSelected
                    ((IndicatorVH) getChildViewHolder(getLayoutManager().getChildAt(i)), i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public static abstract class IndicatorVH extends ViewHolder implements View.OnClickListener {

        private IndicatorAdapter mParentAdapter;

        public IndicatorVH(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        void setParentAdapter(IndicatorAdapter mParentAdapter) {
            this.mParentAdapter = mParentAdapter;
        }

        @Override
        public void onClick(View v) {
            if (mParentAdapter.getViewPager() != null) {
                mParentAdapter.getViewPager().setCurrentItem(getLayoutPosition());
            }
        }

        public abstract void onSelected();

        public abstract void onUnSelected();
    }

    public static abstract class IndicatorAdapter<VH extends IndicatorVH> extends RecyclerView.Adapter<VH> {

        ViewPager mVP;
        ViewPagerIndicator mRv;
        VH mSelectedVH;
        int mSelectedP = 0;

        public ViewPager getViewPager() {
            return mVP;
        }

        void setViewPager(ViewPager viewPager) {
            mVP = viewPager;
        }

        @NonNull
        @Override
        @CallSuper
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            VH vh = onCreateIndicatorViewHolder(viewGroup, i);
            vh.setParentAdapter(this);
            return vh;
        }

        @CallSuper
        public void onIndicatorSelected(VH vh, int i) {
            if(vh == null){
                return;
            }
            if (mSelectedVH != null) {
                mSelectedVH.onUnSelected();
            }
            vh.onSelected();
            mSelectedVH = vh;
            mSelectedP = i;
            if (mRv == null || mRv.getLayoutManager() == null) {
                return;
            }
            if (i > ((LinearLayoutManager) mRv.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                    || i < ((LinearLayoutManager) mRv.getLayoutManager()).findFirstCompletelyVisibleItemPosition()) {
                mRv.scrollToPosition(i);
            }
        }

        public abstract VH onCreateIndicatorViewHolder(@NonNull ViewGroup viewGroup, int i);

        @Override
        @CallSuper
        public void onBindViewHolder(@NonNull VH vh, int i) {
            if (i == mSelectedP) {
                // first Time;
                onIndicatorSelected(vh, i);
            }
        }

    }
}
