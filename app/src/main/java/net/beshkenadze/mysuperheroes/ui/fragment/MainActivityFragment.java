package net.beshkenadze.mysuperheroes.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.beshkenadze.mysuperheroes.R;
import net.beshkenadze.mysuperheroes.api.MarvelService;
import net.beshkenadze.mysuperheroes.api.model.CharacterResult;
import net.beshkenadze.mysuperheroes.ui.adapter.CharactersAdapter;
import net.beshkenadze.mysuperheroes.ui.fragment.BaseFragment;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CharactersAdapter mCharactersAdapter;

    private Action1<? super CharacterResult> mItemsReceivedAction = new Action1<CharacterResult>() {
        @Override public void call (CharacterResult characterResult) {
            mCharactersAdapter.setItems(characterResult.getData().getResults());
        }
    };

    public MainActivityFragment () {
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.characters_view);
//        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCharactersAdapter = new CharactersAdapter();
        mRecyclerView.setAdapter(mCharactersAdapter);
        fetchData();
    }

    private void fetchData () {
        Observable<CharacterResult> result = MarvelService.getInstance()
                .listCharacters()
                .doOnError(new Action1<Throwable>() {
                    @Override public void call (Throwable throwable) {

                    }
                })
                .onErrorReturn(new Func1<Throwable, CharacterResult>() {
                    @Override public CharacterResult call (Throwable throwable) {
                        return new CharacterResult();
                    }
                })
                .doOnNext(mItemsReceivedAction);
        subscribe(result);
    }
}
