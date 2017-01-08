package com.example.hpw.fibonacci;

import com.hpw.mvpframe.utils.helper.RxUtil;

import java.util.List;

import rx.Observable;

/**
 * Created by hpw on 17-1-6.
 */
public class MainModel implements MainContract.Model {
    @Override
    public Observable<List<String>> getFibonacciData(int num, int page, boolean sort) {
        //让取数据运行在子线程中
        return Observable.just(DataServer.getFibonacciData(num, page, sort)).compose(RxUtil.rxSchedulerHelper());
    }
}
