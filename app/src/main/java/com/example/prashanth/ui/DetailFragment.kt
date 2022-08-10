package com.example.prashanth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.prashanth.R
import com.example.prashanth.models.ImageDownloaderOptions
import com.example.prashanth.obtainViewModel
import com.example.prashanth.provideImageDownloadRepository

internal class DetailFragment: Fragment(){

    private val imageDownloaderRepo = provideImageDownloadRepository()

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var publishDate: TextView
    private lateinit var publishAuthor: TextView
    private lateinit var authorId: TextView

    private val viewModel by lazy {
        obtainViewModel {
            DetailFragmentViewModel(
                requireArguments().getString("PORCUPINE_ITEM")
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        view.run {
            title = this.findViewById(R.id.p_title)
            image = this.findViewById(R.id.p_image)
            description = this.findViewById(R.id.p_desc)
            publishDate = this.findViewById(R.id.p_published_date)
            publishAuthor = this.findViewById(R.id.p_author)
            authorId = this.findViewById(R.id.p_author_id)
        }

        viewModel.porcupine.observe(viewLifecycleOwner, Observer {
            title.apply {
                text = HtmlCompat.fromHtml( it.title.orEmpty(),HtmlCompat.FROM_HTML_MODE_LEGACY)
                isVisible = !it.title.isNullOrEmpty()
            }

            imageDownloaderRepo.displayImage(ImageDownloaderOptions(it.media?.m.orEmpty(),null),
                image,view
            )

            description.apply {
                text = HtmlCompat.fromHtml( it.description.orEmpty(),HtmlCompat.FROM_HTML_MODE_LEGACY)
                isVisible = !it.description.isNullOrEmpty()
            }

            publishDate.apply {
                text = it.published
                isVisible = !it.published.isNullOrEmpty()
            }
            publishAuthor.apply {
                text = it.author
                isVisible = !it.author.isNullOrEmpty()
            }

        })

    }

}
