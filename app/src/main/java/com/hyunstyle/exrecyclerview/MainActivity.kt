package com.hyunstyle.exrecyclerview

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hyunstyle.exrecyclerview.adapter.ExpandableSwipeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.hyunstyle.exrecyclerview.adapter.ExpandableSwipeAdapter.Item
import com.hyunstyle.exrecyclerview.util.SwipeItemTouchHelper
import com.hyunstyle.exrecyclerview.util.SwipeItemTouchListener

/**
 * Created by hyunstyle on 2019.05.04
 */
class MainActivity : AppCompatActivity(), SwipeItemTouchListener {

    private val charList : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private lateinit var adapter: ExpandableSwipeAdapter

    companion object {
        private const val HEADER_ITEM_COUNT = 5
        private const val CONTENT_ITEM_COUNT = 5
        private const val RANDOM_STRING_LENGTH = 10
        private const val MAX_IMAGE_SIZE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        collapsing_toolbar_layout.title = "Expandable RecyclerView"
        expandable_recycler_view.layoutManager = LinearLayoutManager(this)

        // Swipe
        val swipeHelper = SwipeItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(expandable_recycler_view)

        // ExpandableSwipeAdapter
        adapter = ExpandableSwipeAdapter(this, Glide.with(this))
        expandable_recycler_view.adapter = adapter

        adapter.setData(getData())
    }

    /**
     * get data example.
     * actually, this function should be modified to do some network requests like Retrofit 2.0.
     * after that, add Items to List using builder pattern.
     */
    private fun getData() : List<Item> {
        val ret = ArrayList<Item>()
        for (i in 1..HEADER_ITEM_COUNT) {
            val header = Item.Builder()
                .type(ExpandableSwipeAdapter.HEADER)
                .title(generateRandomString(RANDOM_STRING_LENGTH))
                .build()

            ret.add(header)

            for(j in 1..CONTENT_ITEM_COUNT) {
                val content = Item.Builder()
                    .type(ExpandableSwipeAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .content(generateRandomString(RANDOM_STRING_LENGTH))
                    .build()

                ret.add(content)
            }
        }

        val footer = Item.Builder()
            .type(ExpandableSwipeAdapter.FOOTER)
            .title(resources.getString(R.string.footer_header))
            .content(resources.getString(R.string.footer_content))
            .build()

        ret.add(footer)

        return ret
    }

    private fun generateRandomString(length: Int) : String = (1..length)
                                                        .map { kotlin.random.Random.nextInt(0, charList.size) }
                                                        .map(charList::get)
                                                        .joinToString("")

    private fun generateRandomImageUrl(max: Int) : String = resources.getString(R.string.random_image_url, (1..max).shuffled().first())

    // SwipeItemTouchListener override area

    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {

        if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
            val swipedIndex = holder.adapterPosition
            val removedItem = adapter.remove(swipedIndex)

            val snackBar = Snackbar.make(expandable_recycler_view, resources.getString(R.string.remove, removedItem.content), Snackbar.LENGTH_LONG)
            snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.red))
            if(removedItem.type == ExpandableSwipeAdapter.CONTENT) {

                snackBar.setAction("Undo") {
                    adapter.add(swipedIndex, removedItem)
                }
            } else if(removedItem.type == ExpandableSwipeAdapter.HEADER) {
                snackBar.setText(resources.getString(R.string.header, removedItem.title))
                snackBar.setAction("XD") {
                    snackBar.dismiss()
                }
            }

            snackBar.show()
        }
    }

    override fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int, uiUtil: ItemTouchUIUtil) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                    uiUtil.onSelected(holder.container)
                }
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        holder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    ) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                    uiUtil.onDraw(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        holder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    ) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                    uiUtil.onDrawOver(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil) {
        if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
            uiUtil.clearView(holder.container)
        }
    }
}
