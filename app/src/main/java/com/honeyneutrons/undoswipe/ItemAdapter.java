package com.honeyneutrons.undoswipe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.honeyneutrons.undoswipe.helper.ItemTouchHelperAdapter;
import com.honeyneutrons.undoswipe.helper.ItemTouchHelperViewHolder;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    public interface OnStartDragListener {

        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    private final Context context;
    static List<Item> itemList = new ArrayList<>();
    private TextView tvNumber;


   private final OnStartDragListener dragStartListener;

    public ItemAdapter(Context context, OnStartDragListener dragStartListener, TextView tvNumber) {
        this.context = context;
        this.dragStartListener=dragStartListener;
        this.tvNumber=tvNumber;

    }

    @Override
    public void onItemDismiss(final int position) {

        final Item item =new Item();
        item.setItemName(itemList.get(position).getItemName());

        notifyItemRemoved(position);
        itemList.remove(position);
        notifyItemRangeChanged(0, getItemCount());
        tvNumber.setText(String.valueOf(itemList.size()));

        final Snackbar snackbar =  Snackbar

                .make(tvNumber,context.getResources().getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(context, R.color.white))
                .setAction(context.getResources().getString(R.string.item_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       itemList.add(position, item);
                        notifyItemInserted(position);
                        tvNumber.setText(String.valueOf(itemList.size()));

                    }
                });


        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        TextView tvSnackAction = (TextView) snackbar.getView().findViewById( android.support.design.R.id.snackbar_action );
        tvSnack.setTextColor(Color.WHITE);
        tvSnack.setTypeface(Typefaces.getRobotoMedium(context));
        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(context));
        snackbar.show();


       Runnable runnableUndo = new Runnable() {

            @Override
            public void run() {
                tvNumber.setText(String.valueOf(itemList.size()));
                snackbar.dismiss();
            }
        };
        Handler handlerUndo=new Handler();handlerUndo.postDelayed(runnableUndo,2500);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }

    public void addItem(int position, Item item) {

        itemList.add(position, item);
        notifyItemInserted(position);
        tvNumber.setText(String.valueOf(itemList.size()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getItemName());
        itemViewHolder.relativeReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(itemViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_adapter, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder,View.OnClickListener {

        protected RelativeLayout container;
        protected TextView tvItemName;
        protected ImageView ivReorder;
        protected RelativeLayout relativeReorder;


        public ItemViewHolder(final View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container);
            tvItemName = (TextView) v.findViewById(R.id.tvItemName);
            ivReorder = (ImageView) v.findViewById(R.id.ivReorder);
            relativeReorder = (RelativeLayout) v.findViewById(R.id.relativeReorder);
        }

        @Override
        public void onClick(View view) {
        }

        @Override
        public void onItemSelected(Context context) {
             container.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.white));
             ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onItemClear(Context context) {
            container.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.textlight), PorterDuff.Mode.SRC_IN);
            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.textlight));
        }

    }

}
