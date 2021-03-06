package me.dkzwm.widget.srl;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import me.dkzwm.widget.srl.config.Constants;
import me.dkzwm.widget.srl.extra.footer.MaterialFooter;
import me.dkzwm.widget.srl.extra.header.MaterialHeader;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.utils.PixelUtl;

/**
 * @author dkzwm
 */
public class MaterialSmoothRefreshLayout extends SmoothRefreshLayout {
    protected OnUIPositionChangedListener mOnUIPositionChangedListener = new OnUIPositionChangedListener() {
        int mLastMovingStatus = Constants.MOVING_CONTENT;

        @Override
        public void onChanged(byte status, IIndicator indicator) {
            int movingStatus = indicator.getMovingStatus();
            if (movingStatus == Constants.MOVING_HEADER) {
                if (movingStatus != mLastMovingStatus) {
                    setEnablePinContentView(true);
                    setEnablePinRefreshViewWhileLoading(true);
                }
            } else {
                if (movingStatus != mLastMovingStatus)
                    setEnablePinContentView(false);
            }
            mLastMovingStatus = movingStatus;
        }
    };
    protected MaterialHeader mMaterialHeader;
    protected MaterialFooter mMaterialFooter;

    public MaterialSmoothRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public MaterialSmoothRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaterialSmoothRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialSmoothRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mMaterialHeader = new MaterialHeader(context);
        mMaterialHeader.setColorSchemeColors(new int[]{Color.RED, Color.BLUE, Color
                .GREEN, Color.BLACK});
        mMaterialHeader.setPadding(0, PixelUtl.dp2px(context, 25), 0,
                PixelUtl.dp2px(context, 20));
        setHeaderView(mMaterialHeader);
        mMaterialFooter = new MaterialFooter(context);
        setFooterView(mMaterialFooter);
    }

    /**
     * Quickly set to material style. Before you change the configuration, you must know which
     * parameters have been configured
     */
    public void materialStyle() {
        setRatioOfFooterToRefresh(.95f);
        setMaxMoveRatioOfFooter(1f);
        setRatioToKeep(1f);
        setMaxMoveRatioOfHeader(1.5f);
        setEnablePinContentView(true);
        setEnableKeepRefreshView(true);
        setEnablePinRefreshViewWhileLoading(true);
        setEnableNextPtrAtOnce(true);
        if (mHeaderView != null && mHeaderView instanceof MaterialHeader)
            ((MaterialHeader) mHeaderView).doHookUIRefreshComplete(this);
        if (!isDisabledLoadMore()) {
            removeOnUIPositionChangedListener(mOnUIPositionChangedListener);
            addOnUIPositionChangedListener(mOnUIPositionChangedListener);
        }
    }
}
