package com.igzafer.visualizingsortingalgorithms

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.igzafer.visualizingsortingalgorithms.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.*


@Suppress("UNUSED_CHANGED_VALUE")
class HomeScreen : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    val rgbs = mutableListOf<rgbList>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = _binding!!.imageViewRobot
        prepareImage()
        //_binding!!.result.text = rgbList.toString()
        _binding!!.startBtn.setOnClickListener {
            //startSorting()
            //startMagicBubble()

            CoroutineScope(Dispatchers.IO).launch {
                rgbs.shuffle()
                val result = quicksort(rgbs)

            }
        }


    }

    val factor = 51

    lateinit var imageView: ImageView
    val bitmap = Bitmap.createBitmap(factor, factor, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()
    fun quicksort(items: MutableList<rgbList>): List<rgbList> {
        var index = 0

        if (items.count() < 2) {

            return items
        }
        val pivot = items[items.count() / 2]

        val equal = items.filter { it == pivot }

        val less = items.filter { it.indexId < pivot.indexId }

        val greater = items.filter { it.indexId > pivot.indexId }

        for (x in 0 until factor) {
            for (y in 0 until factor) {

                    paint.color = Color.rgb(
                        pivot.rgbs[0],
                        pivot.rgbs[1],
                        pivot.rgbs[2],
                    )

                canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                CoroutineScope(Dispatchers.Main).launch {
                    imageView.setImageBitmap(bitmap)


                }

                index++
            }

        }

        return quicksort(less.toMutableList()) + equal + quicksort(greater.toMutableList())

    }


    private fun startMagicBubble() {

        rgbs.shuffle()
        var index: Int
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("magic", "rgb size ${rgbs.size}")
            do {
                next = false
                var level = rgbs.size - 1

                for (i in 0 until level) {
                    if (rgbs[i].indexId > rgbs[i + 1].indexId) {
                        next = true
                        index = 0
                        Collections.swap(rgbs, i, i + 1)
                        if (i % 10000 == 0) {
                            for (x in 0 until factor) {
                                for (y in 0 until factor) {
                                    paint.color = Color.rgb(
                                        rgbs[index].rgbs[0],
                                        rgbs[index].rgbs[1],
                                        rgbs[index].rgbs[2],
                                    )

                                    canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                                    CoroutineScope(Dispatchers.Main).launch {
                                        imageView.setImageBitmap(bitmap)

                                    }

                                    index++
                                }

                            }

                        }
                    }
                }
                level--

            } while (next)
            index = 0
            for (x in 0 until factor) {
                for (y in 0 until factor) {
                    paint.color = Color.rgb(
                        rgbs[index].rgbs[0],
                        rgbs[index].rgbs[1],
                        rgbs[index].rgbs[2],
                    )

                    canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                    CoroutineScope(Dispatchers.Main).launch {
                        imageView.setImageBitmap(bitmap)

                    }

                    index++
                }

            }
        }
    }


    /*

     */
    @SuppressLint("SetTextI18n")
    private fun prepareImage() {
        var index = 0
        val bm = BitmapFactory.decodeResource(resources, R.drawable.eminem).scale(factor, factor)
        CoroutineScope(Dispatchers.IO).launch {

            for (x in 0 until factor) {
                for (y in 0 until factor) {
                    val pixel = bm.getPixel(x, y)
                    rgbs.add(
                        rgbList(index, listOf(pixel.red, pixel.green, pixel.blue))
                    )
                    index++
                }

            }
            CoroutineScope(Dispatchers.Main).launch {

                binding.startBtn.isEnabled = true
            }

        }
    }

    var next = true

    /*
    private fun startSorting() {
        val imageView: ImageView = _binding!!.imageViewResult
        val bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        CoroutineScope(Dispatchers.IO).launch {
            do {
                next = false
                var level = rgbList.size - 1
                for (i in 0 until level) {
                    if (rgbList[i].indexId > rgbList[i + 1].indexId) {
                        next = true
                        Collections.swap(rgbList, i, i + 1)
                        CoroutineScope(Dispatchers.Main).launch {
                            _binding!!.result.text = rgbList.toString()
                            paint.color = Color.parseColor(
                                String.format(
                                    "#%02x%02x%02x",
                                    rgbList[i].rgbs[0],
                                    rgbList[i].rgbs[1],
                                    rgbList[i].rgbs[2]
                                )
                            )
                            if (i == 0) {
                                canvas.drawPoint(0f, 0f, paint)
                            } else if (i == 1) {
                                canvas.drawPoint(0f, 1f, paint)

                            } else if (i == 2) {
                                canvas.drawPoint(1f, 0f, paint)

                            } else if (i == 3) {
                                canvas.drawPoint(1f, 1f, paint)

                            }


                            imageView.setImageBitmap(bitmap)
                        }
                        delay(50)
                    }
                }
                level--
            } while (next)

        }
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}