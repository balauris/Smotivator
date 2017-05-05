package com.balauris.smotivator.generalStatistics.generalStatisticsFragment.smokeFree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.balauris.smotivator.R
import com.balauris.smotivator.utils.data.UserData
import com.balauris.smotivator.utils.fragment.BaseGeneralStatisticsFragment
import eu.inloop.simplerecycleradapter.ItemClickListener
import eu.inloop.simplerecycleradapter.SettableViewHolder
import eu.inloop.simplerecycleradapter.SimpleRecyclerAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_smoke_free.*
import java.util.*
import java.util.concurrent.TimeUnit


class SmokeFreeFragment : BaseGeneralStatisticsFragment(), ItemClickListener<SmokeFreeMonth>,
        SmokeFreeView {

    override var firstSubVal: String? = ""
    override var mainTitleFirstValue = 0.toString()
    override var mainTitmeSecondaryValue = "days"
    override var subTitleSecondaryValue = "smoke free"
    override var secondValStyle: Int? = null
    override var progress = 0f
    override val progressBarDrawableId = R.drawable.smoke_free_progress_bar
    override val fragmentTag = "SmokeFreeFragment"

    lateinit var recyclerAdapter: SimpleRecyclerAdapter<SmokeFreeMonth>

    private val presenter = SmokeFreePresenterImpl(SmokeFreeProviderImpl(SmokeFreeRepositoryImpl()))

    init {
        presenter.setupBasePresenter(this, activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerAdapter = SimpleRecyclerAdapter(this, SmokeFreeMonthsRecyclerAdapter(context))
        presenter.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_smoke_free, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smokeFreeRecycler.adapter = recyclerAdapter
        presenter.onViewCreated()
    }

    override fun showMonthSmokeFreeList(smokeFreeMonthList: ArrayList<SmokeFreeMonth>) {
        recyclerAdapter.addItems(smokeFreeMonthList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onItemClick(item: SmokeFreeMonth, viewHolder: SettableViewHolder<SmokeFreeMonth>,
                             view: View) {
        //empty
    }

    companion object {
        fun newInstance(): SmokeFreeFragment {
            return SmokeFreeFragment()
        }
    }
}