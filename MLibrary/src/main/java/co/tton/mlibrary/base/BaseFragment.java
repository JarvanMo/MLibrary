package co.tton.mlibrary.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import co.tton.mlibrary.system.LeakCanaryHelper;

/**
 * Created by Administrator on 2015/9/8.
 *
 */
public abstract class BaseFragment extends Fragment {


    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutId = setLayoutId();
        if (layoutId != 0) {
            rootView = inflater.inflate(layoutId, null);
        }
        ButterKnife.bind(this, rootView);
        doMainJob();
        return rootView;
    }

    protected void doMainJob() {
    }


    protected abstract int setLayoutId();

    protected View getRootView() {
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(LeakCanaryHelper.leakCanaryHelper != null){
            LeakCanaryHelper.leakCanaryHelper.getRefWatcher(getActivity());
        }

    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(View view, int id) {

        return (T) view.findViewById(id);

    }


    public void showToast(String msg) {
        BaseApplication.MyToast.show(msg);
    }

    public void showToast(int id) {
        BaseApplication.MyToast.show(id);
    }

}
