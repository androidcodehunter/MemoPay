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
import timber.log.Timber

const val IMAGE_URL = "key_image_url"

class SlidingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*TODO Setting DiskCacheStrategy.ALL we request glide to cache the image in file/disk. So that in future the images get cached.  */
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.onboarding_three)
            .placeholder(R.drawable.onboarding_three)


        arguments?.getString(IMAGE_URL)?.let { url ->
            Timber.d("umage url $url")
            introBackgroundImage.setImage(url, requestOptions)
        }
        Timber.d("umage url")
    }


    companion object{
        fun newInstance(url: String): Fragment {
            val arguments = Bundle()
            arguments.putString(IMAGE_URL, url)
            val slidingFragment = SlidingFragment()
            slidingFragment.arguments = arguments
            return slidingFragment
        }
    }

}
