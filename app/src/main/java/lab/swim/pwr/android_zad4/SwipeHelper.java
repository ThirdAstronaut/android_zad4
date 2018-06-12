package lab.swim.pwr.android_zad4;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.END;
import static android.support.v7.widget.helper.ItemTouchHelper.START;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

/**
 * Created by Rafał on 2018-03-27.
 */

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    private CustomAdapter mCustomAdapter;

    SwipeHelper(CustomAdapter customAdapter) {
        super(UP | DOWN, ItemTouchHelper.LEFT);
        mCustomAdapter = customAdapter;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }


    private boolean mDraggable = false;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = mDraggable ? UP | DOWN | START | END : 0;
        return makeMovementFlags(dragFlags, 0);
    }
    public void setDraggable(boolean value) {
        mDraggable = value;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof CustomAdapter.ViewHolder) return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mCustomAdapter.removeItem(viewHolder.getAdapterPosition());
    }
}
