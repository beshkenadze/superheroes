package net.beshkenadze.mysuperheroes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.beshkenadze.mysuperheroes.R;
import net.beshkenadze.mysuperheroes.api.MarvelService;
import net.beshkenadze.mysuperheroes.api.model.CharacterModel;
import net.beshkenadze.mysuperheroes.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A placeholder fragment containing a simple view.
 */
public class CharacterViewActivityFragment extends BaseFragment {
    private static final String ARG_ID = "ARG_ID";

    @InjectView(R.id.name)
    public TextView mName;
    @InjectView(R.id.description)
    public TextView mDescription;
    @InjectView(R.id.thumb)
    public ImageView mThumb;

    @InjectView(R.id.actionButton)
    public TextView mActionButton;

    private Action1<? super CharacterModel> mItemReceivedAction = new Action1<CharacterModel>() {
        @Override public void call (CharacterModel characterModel) {
            if (characterModel.getId() <= 0) {
                getActivity().finish();
                return;
            }

            bindData(characterModel);
        }
    };

    public CharacterViewActivityFragment () {
    }

    public static CharacterViewActivityFragment newInstance (int id) {
        CharacterViewActivityFragment fragment = new CharacterViewActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    private void bindData (CharacterModel characterModel) {
        mName.setText(characterModel.getName());
        mDescription.setText(characterModel.getDescription());
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setTitle(characterModel.getName());
        }
        Glide.with(getActivity()).load(characterModel.getThumbnail().getUrl()).into(mThumb);
    }

    private BaseActivity getBaseActivity () {
        return (BaseActivity) getActivity();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.character_card_view, container, false);
    }

    @Override public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        mActionButton.setText(R.string.add_to_favorite);
        mActionButton.setVisibility(View.GONE);

        fetchData();
    }

    private void fetchData () {
        Observable<CharacterModel> characterObservable = MarvelService.getInstance()
                .getCharacter(getCharacterId())
                .doOnError(new Action1<Throwable>() {
                    @Override public void call (Throwable throwable) {

                    }
                })
                .onErrorReturn(new Func1<Throwable, CharacterModel>() {
                    @Override public CharacterModel call (Throwable throwable) {
                        return new CharacterModel();
                    }
                })
                .doOnNext(mItemReceivedAction);
        subscribe(characterObservable);
    }

    private int getCharacterId () {
        return getArguments().getInt(ARG_ID, -1);
    }
}
