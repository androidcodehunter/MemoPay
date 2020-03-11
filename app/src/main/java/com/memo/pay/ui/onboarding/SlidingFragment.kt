package com.memo.pay.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.memo.pay.R
import com.memo.pay.extensions.setImage
import kotlinx.android.synthetic.main.fragment_intro.*

class SlidingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*TODO Setting DiskCacheStrategy.ALL we request glide to cache the image in file/disk. */
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_launcher_background)
        introBackgroundImage.setImage("https://lh3.googleusercontent.com/Rl7LQcjYNh20Id231XAmaRgEZ_NsulOPkm2vDks-N2L8G134i2WHIccMhsP7dZ_WHxuFxnwivkgpnugxVtypb-w4muM7J_yM0EIVS-mK5sd3JbKm1zcCMh9PclyaC7Mv74uZggs7=w375-h320-no", requestOptions)
    }


    companion object{
        fun newInstance(url: String): Fragment {
            return SlidingFragment()
        }
    }

}
