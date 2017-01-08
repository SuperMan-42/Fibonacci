package com.example.hpw.fibonacci;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.utils.StatusBarUtil;
import com.hpw.mvpframe.widget.recyclerview.BaseQuickAdapter;
import com.hpw.mvpframe.widget.recyclerview.BaseViewHolder;
import com.hpw.mvpframe.widget.recyclerview.CoreRecyclerView;
import com.hpw.mvpframe.widget.recyclerview.listener.OnItemClickListener;

import java.util.List;

public class MainActivity extends CoreBaseActivity<MainPresenter, MainModel> implements MainContract.View {
    CoreRecyclerView coreRecyclerView;
    static Boolean sort = true;
    static final int num = 20;

    @Override
    public int getLayoutId() {
        DataServer.createFibonacciData(this);
        return R.layout.activity_main;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void initView(Bundle bundle) {
        StatusBarUtil.setTranslucent(this);
        coreRecyclerView = (CoreRecyclerView) findViewById(R.id.recyclerview);
        findViewById(R.id.text).setOnClickListener(view -> {
            sort = sort == true ? false : true;
            if (!sort)
                showToast("逆序...");
            coreRecyclerView.getAdapter().getData().clear();
            coreRecyclerView.setPage(0);
            coreRecyclerView.getAdapter().openLoadMore(num);
            coreRecyclerView.getAdapter().removeAllFooterView();
            mPresenter.getData(num, 0, sort);
        });
        coreRecyclerView.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.value, item);
            }
        }).addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast("点击了" + position);
            }
        }).openLoadMore(num, page -> mPresenter.getData(num, page, sort))
                .openRefresh();

    }

    @Override
    public void showContent(List<String> info) {
        coreRecyclerView.getAdapter().addData(info);
    }

    @Override
    public void showError(String s) {
        showToast(s);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
