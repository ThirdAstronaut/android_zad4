package lab.swim.pwr.android_zad4;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView durationTimeTextView;
        private Button startButtonSongRow;

        ViewHolder(final View v) {
            super(v);
            titleTextView = v.findViewById(R.id.titleTextView);
            authorTextView = v.findViewById(R.id.authorTextView);
            durationTimeTextView = v.findViewById(R.id.durationTimeTextView);
            startButtonSongRow = v.findViewById(R.id.startButtonSongsRow);


        }
    }

    Context ctx;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_row, parent, false);

        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        final Song song = SongsKeeper.getInstance().getMusicList().get(position);
        holder.titleTextView.setText(song.getTitle());
        holder.authorTextView.setText(song.getAuthor());
        holder.durationTimeTextView.setText(song.getDuration());

        holder.startButtonSongRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("song-changed");
                intent.putExtra("SongName", song.getTitle());
                intent.putExtra("SongID", song.getId());
                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getCount() {
        return SongsKeeper.getInstance().getMusicList().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Song getItemAtPosition(int position) {
        return SongsKeeper.getInstance().getMusicList().get(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getItemCount() {
        return SongsKeeper.getInstance().getMusicList().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void removeItem(int id) {
        SongsKeeper.getInstance().getMusicList().remove(id);
        this.notifyItemRemoved(id);
    }
}
