package net.beshkenadze.mysuperheroes.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.beshkenadze.mysuperheroes.R;
import net.beshkenadze.mysuperheroes.api.model.CharacterModel;
import net.beshkenadze.mysuperheroes.ui.activity.CharacterViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Aleksandr Beshkenadze <beshkenadze@gmail.com> on 09/05/15.
 */
public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    List<CharacterModel> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.characters_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder (final ViewHolder viewHolder, int i) {
        final CharacterModel item = getItem(i);
        viewHolder.name.setText(item.getName());
        viewHolder.description.setText(item.getDescription());

        showImage(viewHolder.itemView.getContext(), item.getThumbnail().getUrl(), viewHolder.thumb);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick (View v) {
                Intent viewIntent = new Intent(v.getContext(), CharacterViewActivity.class);
                viewIntent.putExtra(CharacterViewActivity.EXTRA_ID, item.getId());
                v.getContext().startActivity(viewIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return items.size();
    }

    private void showImage (final Context context, final String url, final ImageView thumb) {
        Glide.with(context).load(url).into(thumb);
    }

    private CharacterModel getItem (int position) {
        CharacterModel item = new CharacterModel();

        if (getItemCount() > position) {
            item = items.get(position);
        }

        return item;
    }

    public void setItems (List<CharacterModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        public TextView name;

        @InjectView(R.id.description)
        public TextView description;

        @InjectView(R.id.thumb)
        public ImageView thumb;

        @InjectView(R.id.actionButton)
        public Button view;

        public ViewHolder (View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            description.setMaxLines(3);
            description.setEllipsize(TextUtils.TruncateAt.END);
        }
    }


}
