package com.dboy.navigation.livedata;

import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * @author DBoy
 * @date 2020/12/21
 * Class 描述 : 数据生命周期，以及数据改变状态监听
 */
public class MutableLiveDataStatus<T> extends MutableLiveData<T> {

    /**
     * 数据状态
     * <p>
     * 记录数据上一次的状态
     */
    private final MutableLiveData<LiveDataStatus> mLiveDataStatus = new MutableLiveData<>();

    /**
     * 重写构造方法，设置数据状态
     */
    public MutableLiveDataStatus() {
        super();
        //数据默认是需要重设的
        setReset();
    }

    /**
     * @param value 默认数据，如果需要直接获取数据的话
     */
    public MutableLiveDataStatus(T value) {
        this(value, LiveDataStatus.RESET);
    }

    /**
     * @param value      默认数据，如果需要直接获取数据的话
     * @param initStatus 默认数据的初始状态
     */
    public MutableLiveDataStatus(T value, LiveDataStatus initStatus) {
        super(value);
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            changeStatus(initStatus);
        } else {
            postChangeStatus(initStatus);
        }
    }

    /**
     * 设置有效数据
     *
     * @param value 数据 不可null
     */
    @Override
    public void setValue(T value) {
        setValue(value, LiveDataStatus.SUCCESS.getMessage());
    }

    /**
     * 设置数据，同时设置Success消息信息
     *
     * @param value 数据
     * @param msg   消息
     */
    public void setValue(T value, String msg) {
        super.setValue(value);
        changeStatus(LiveDataStatus.SUCCESS.setMessage(msg));
        //状态恢复到完成状态
        changeStatus(LiveDataStatus.COMPLETE);
    }

    /**
     * 子线程post数据
     *
     * @param value 数据 不可null
     */
    @Override
    public void postValue(T value) {
        postValue(value, LiveDataStatus.SUCCESS.getMessage());
    }

    /**
     * 子线程post数据 同时设置状态消息信息
     *
     * @param value 数据 不可null
     * @param msg   状态消息
     */
    public void postValue(T value, String msg) {
        super.postValue(value);
        postChangeStatus(LiveDataStatus.SUCCESS.setMessage(msg));
        //状态恢复到完成状态
        postChangeStatus(LiveDataStatus.COMPLETE);
    }

    /**
     * 数据改变前
     */
    public void setStart() {
        changeStatus(LiveDataStatus.START);
    }

    /**
     * 数据改变出现错误
     * @param msg 一些错误描述信息
     */
    public void setError(String msg) {
        changeStatus(LiveDataStatus.ERROR.setMessage(msg));
    }

    /**
     * 数据需要重置
     */
    public void setReset() {
        changeStatus(LiveDataStatus.RESET);
    }

    /**
     * 在fragment中要使用ViewLifecycleOwner
     */
    public void observe(@NonNull Fragment fragment, @NonNull Observer<? super T> observer, @NonNull Observer<LiveDataStatus> observerStatus) {
        this.observe(fragment.getViewLifecycleOwner(), observer, observerStatus);
    }

    /**
     * 在fragment中要使用ViewLifecycleOwner
     */
    public void observe(@NonNull Fragment fragment, @NonNull Observer<? super T> observer) {
        this.observe(fragment.getViewLifecycleOwner(), observer);
    }

    /**
     * 在fragment中要使用ViewLifecycleOwner
     */
    public void observeStatus(@NonNull Fragment fragment, @NonNull Observer<LiveDataStatus> observer) {
        this.observeStatus(fragment.getViewLifecycleOwner(), observer);
    }

    /**
     * 数据状态观测
     *
     * @param owner          生命周期
     * @param observer       数据改变
     * @param observerStatus 状态改变
     */
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer, @NonNull Observer<LiveDataStatus> observerStatus) {
        this.observe(owner, observer);
        observeStatus(owner, observerStatus);
    }

    /**
     * 数据观察
     *
     * @param owner    生命周期
     * @param observer 数据改变
     */
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
    }

    /**
     * 数据状态观察
     *
     * @param owner    生命周期
     * @param observer 状态改变
     */
    public void observeStatus(@NonNull LifecycleOwner owner, @NonNull Observer<LiveDataStatus> observer) {
        mLiveDataStatus.observe(owner, observer);
    }

    /**
     * 通知数据发生改变
     */
    public void notifyDataChanged() {
        setValue(getValue());
    }

    /**
     * 检查活动数据状态，如果不是完成状态，将改变成{@link LiveDataStatus#RESET}
     * 恢复到初始状态
     */
    public void checkNotCompleteReset() {
        if (mLiveDataStatus.getValue() != LiveDataStatus.COMPLETE) {
            mLiveDataStatus.setValue(LiveDataStatus.RESET);
        }
    }

    /**
     * 修改状态 进行判断防止多次观察重复相应
     */
    private void changeStatus(LiveDataStatus liveDataStatus) {
        mLiveDataStatus.setValue(liveDataStatus);
    }

    private void postChangeStatus(LiveDataStatus liveDataStatus) {
        mLiveDataStatus.postValue(liveDataStatus);
    }

}
