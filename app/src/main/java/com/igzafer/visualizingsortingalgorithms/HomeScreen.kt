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

    val randomList = listOf(1, 48, 56, 75, 3, 81, 55, 4, 22, 2, 5, 7, 98, 11, 14, 52, 16)
    val rgbs = mutableListOf<MutableList<Int>>()
    val rgbList = mutableListOf<rgbList>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        _binding!!.result.text = rgbList.toString()
        _binding!!.startBtn.setOnClickListener {
            //startSorting()
            startMagic()
        }
        prepareImage()

    }

    private fun startMagic() {
        val imageView: ImageView = _binding!!.imageViewRobot
        val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        var index = 0
        CoroutineScope(Dispatchers.Default).launch {
            for (x in 0 until 200) {
                for (y in 0 until 200) {
                    paint.color = Color.rgb(
                        rgbs[index][0],
                        rgbs[index][1],
                        rgbs[index][2],
                    )

                    canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
                    CoroutineScope(Dispatchers.Main).launch {
                        imageView.setImageBitmap(bitmap)

                    }
                    if (index % 20 == 0) {

                        delay(1)
                    }
                    index++
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun prepareImage() {

        val bm = BitmapFactory.decodeResource(resources, R.drawable.eminem).scale(200, 200)
        CoroutineScope(Dispatchers.IO).launch {
            for (x in 0 until 200) {
                for (y in 0 until 200) {
                    val pixel = bm.getPixel(x, y)


                    rgbs.add(
                        mutableListOf(pixel.red, pixel.green, pixel.blue)
                    )


                }
            }
            CoroutineScope(Dispatchers.Main).launch {

                binding.startBtn.isEnabled = true
            }
            /*
            CoroutineScope(Dispatchers.Main).launch {
                binding.result.text = rgbs.toString()
            }*/
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