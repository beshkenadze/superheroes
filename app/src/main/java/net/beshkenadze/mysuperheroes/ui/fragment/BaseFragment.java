package net.beshkenadze.mysuperheroes.ui.fragment;


import android.support.v4.app.Fragment;

import rx.Observable;
import rx.Subscription;
import rx.android.app.AppObservable;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class BaseFragment extends Fragment {

    public Subscription subscribe (Observable observable) {
        return AppObservable.bindFragment(this, observable).subscribe();
    }
}
