package com.dboy.navigation.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author DBoy
 * @date 2020/9/29
 * Class 描述 : 用于反射获取 ViewModel  和 ViewBinding
 */
public class BindingReflex {
    /**
     * 反射获取ViewBinding
     *
     * @param <V>    ViewBinding 实现类
     * @param aClass 当前类
     * @param from   layouinflater
     *
     * @return viewBinding实例
     */
    public static <V extends ViewBinding> V reflexViewBinding(Class<?> aClass, LayoutInflater from) {
        try {
            Type genericSuperclass = aClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(genericSuperclass)).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    Class<?> tClass;
                    try {
                        tClass = (Class<?>) actualTypeArgument;
                    } catch (Exception e) {
                        continue;
                    }
                    if (ViewBinding.class.isAssignableFrom(tClass)) {
                        Method inflate = tClass.getMethod("inflate", LayoutInflater.class);
                        return (V) inflate.invoke(null, from);
                    }
                }
            }
            return reflexViewBinding(aClass.getSuperclass(), from);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 反射获取ViewBinding
     *
     * @param <V>    ViewBinding 实现类
     * @param aClass 当前类
     * @param from   layoutInflater
     * @param b      attachToRoot
     *
     * @return viewBinding实例
     */
    public static <V extends ViewBinding> V reflexViewBinding(Class<?> aClass, LayoutInflater from, ViewGroup viewGroup, boolean b) {
        try {
            Type genericSuperclass = aClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(genericSuperclass)).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    Class<?> tClass;
                    try {
                        tClass = (Class<?>) actualTypeArgument;
                    } catch (Exception e) {
                        continue;
                    }
                    if (ViewBinding.class.isAssignableFrom(tClass)) {
                        Method inflate = tClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                        return (V) inflate.invoke(null, from, viewGroup, b);
                    }
                }
            }
            return (V) reflexViewBinding(aClass.getSuperclass(), from, viewGroup, b);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 反射获取ViewModel
     *
     * @param <VM>   ViewModel实现类
     * @param aClass 当前class
     * @param owner  生命周期管理
     *
     * @return ViewModel实例
     */
    public static <VM extends ViewModel> VM reflexViewModel(Class<?> aClass, @NonNull ViewModelStoreOwner owner) {
        try {
            Type genericSuperclass = aClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(genericSuperclass)).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    Class<?> aClass1;
                    try {
                        aClass1 = (Class<?>) actualTypeArgument;
                    } catch (Exception e) {
                        continue;
                    }
                    if (ViewModel.class.isAssignableFrom(aClass1)) {
                        return new ViewModelProvider(owner).get((Class<VM>) aClass1);
                    }
                }
            }
            return reflexViewModel(aClass.getSuperclass(), owner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 反射获取ViewModel,这个方法只提供给fragment使用.
     * 如果fragment的父Activity有相同的ViewModel 那么生成的ViewModel将会是同一个实例，做到Fragment与Activity的数据同步,
     * 或者说是同一个Activity中的多个Fragment同步使用用一个ViewModel达到数据之间的同步。
     *
     * @param <VM>     ViewModel实现类
     * @param aClass   当前class
     * @param fragment fragment  调用 {@link Fragment#requireActivity()} 方法
     *
     * @return ViewModel实例
     */
    public static <VM extends ViewModel> VM reflexFragmentModel(Class<?> aClass, @NonNull Fragment fragment) {
        try {
            Type genericSuperclass = aClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(genericSuperclass)).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    Class<?> tClass;
                    try {
                        tClass = (Class<?>) actualTypeArgument;
                    } catch (Exception e) {
                        continue;
                    }
                    if (ViewModel.class.isAssignableFrom(tClass)) {
                        return new ViewModelProvider(fragment.requireActivity()).get((Class<VM>) tClass);
                    }
                }
            }
            return reflexFragmentModel(aClass.getSuperclass(), fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
