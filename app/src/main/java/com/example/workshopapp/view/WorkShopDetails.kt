package com.example.workshopapp.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.workshopapp.MainActivity
import com.example.workshopapp.R
import com.example.workshopapp.model.WorkShopModel


class WorkShopDetails : Fragment() {

    private var workshop: WorkShopModel? = null

    private lateinit var mTitle: TextView
    private lateinit var mDescription: TextView
    private lateinit var mText: TextView
    private lateinit var mVideo: VideoView

    private var stopPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workshop = it.getParcelable(ARG_WORKSHOP)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_shop_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (activity as MainActivity).getWorkshopViewModel()
        viewModel.setAppTitle(SCREEN_TITLE)

        initUI(view)
        workshop?.let {
            mVideo.setVideoURI(Uri.parse(it.video))
            mTitle.text = it.name
            mText.text = it.text
            mDescription.text = it.description
        }
    }

    private fun initUI(view: View) {
        mTitle = view.findViewById(R.id.tv_workshop_title)
        mDescription = view.findViewById(R.id.tv_workshop_description)
        mText = view.findViewById(R.id.tv_workshop_text)
        mVideo = view.findViewById(R.id.video_view)
    }

    override fun onResume() {
        super.onResume()
        mVideo.seekTo(stopPosition)
        mVideo.start()
    }

    override fun onPause() {
        super.onPause()
        stopPosition = mVideo.currentPosition
        mVideo.pause()
    }

    companion object {
        private const val ARG_WORKSHOP = "argWorkshop"
        private const val SCREEN_TITLE = "Workshop details"

        @JvmStatic
        fun newInstance(workshop: WorkShopModel) =
                WorkShopDetails().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_WORKSHOP, workshop)
                    }
                }
    }
}