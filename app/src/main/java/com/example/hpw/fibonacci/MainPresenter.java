package com.example.hpw.fibonacci;

/**
 * Created by hpw on 17-1-6.
 */
public class MainPresenter extends MainContract.Presenter {
    @Override
    public void onStart() {
        getData(MainActivity.num, 0, true);
    }

    @Override
    void getData(int num, int page, boolean sort) {
        mRxManager.add(mModel
                .getFibonacciData(num, page, sort)
                .subscribe(
                        data -> {
                            mView.showContent(data);
                        }, e -> mView.showError("数据加载失败ヽ(≧Д≦)ノ")
                ));
    }
}
