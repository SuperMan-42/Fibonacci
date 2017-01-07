package com.example.hpw.fibonacci;

import com.hpw.mvpframe.base.CoreBaseModel;
import com.hpw.mvpframe.base.CoreBasePresenter;
import com.hpw.mvpframe.base.CoreBaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by hpw on 17-1-6.
 */

public interface MainContract {
    interface Model extends CoreBaseModel {
        Observable<List<String>> getFibonacciData(int num, int page, boolean sort);
    }

    interface View extends CoreBaseView {
        void showContent(List<String> info);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        abstract void getData(int num, int page, boolean sort);
    }
}
