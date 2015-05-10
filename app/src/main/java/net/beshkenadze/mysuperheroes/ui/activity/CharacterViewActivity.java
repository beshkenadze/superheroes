package net.beshkenadze.mysuperheroes.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import net.beshkenadze.mysuperheroes.R;
import net.beshkenadze.mysuperheroes.ui.fragment.CharacterViewActivityFragment;

public class CharacterViewActivity extends BaseActivity {

    public static final String EXTRA_ID = "EXTRA_ID";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras().containsKey(EXTRA_ID)) {
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id > 0) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, CharacterViewActivityFragment.newInstance(id)).commit();
            } else {
                finish();
            }
        }
    }

}
