package com.example.orangesnote

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


 class RecycleItemTouchHelper(private val helperCallback: ItemTouchHelperCallback) :
    ItemTouchHelper.Callback() {
     private val adapter: NoteAdapter? = null
    companion object {
        private const val TAG = "RecycleItemTouchHelper"
    }

    //设置滑动类型标记
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        Log.e(TAG, "getMovementFlags: ")
        return makeMovementFlags(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.END
        )
    }


    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    //滑动
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    //拖拽回调
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.e(TAG, "onMove: ")
        helperCallback.onMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    //滑动
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int): Unit {
        Log.e(TAG, "onSwiped: ")
        helperCallback.onItemDelete(viewHolder.adapterPosition)
    }

    //状态回调
    override fun onSelectedChanged(
        viewHolder: RecyclerView.ViewHolder?,
        actionState: Int
    ) {
        super.onSelectedChanged(viewHolder, actionState)
    }

    interface ItemTouchHelperCallback {
        fun onItemDelete(positon: Int)
        fun onMove(fromPosition: Int, toPosition: Int)
    }


}