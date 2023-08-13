package com.sumere.catchthekenny

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.sumere.catchthekenny.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var random = Random(0)
    private var randomNumber = 0
    private var score = 0
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private var time: Long = 20000

    fun setInvisible(){
        binding.imageView1.visibility = View.INVISIBLE
        binding.imageView2.visibility = View.INVISIBLE
        binding.imageView3.visibility = View.INVISIBLE
        binding.imageView4.visibility = View.INVISIBLE
        binding.imageView5.visibility = View.INVISIBLE
        binding.imageView6.visibility = View.INVISIBLE
        binding.imageView7.visibility = View.INVISIBLE
        binding.imageView8.visibility = View.INVISIBLE
        binding.imageView9.visibility = View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        binding.timeLabelView.text = "Time: ${time/1000}"
        runnable = object: Runnable{
            override fun run() {
                randomNumber = random.nextInt() % 8 + 1

                when(randomNumber){
                    1 -> binding.imageView1.visibility = View.VISIBLE
                    2 -> binding.imageView2.visibility = View.VISIBLE
                    3 -> binding.imageView3.visibility = View.VISIBLE
                    4 -> binding.imageView4.visibility = View.VISIBLE
                    5 -> binding.imageView5.visibility = View.VISIBLE
                    6 -> binding.imageView6.visibility = View.VISIBLE
                    7 -> binding.imageView7.visibility = View.VISIBLE
                    8 -> binding.imageView8.visibility = View.VISIBLE
                    9 -> binding.imageView9.visibility = View.VISIBLE
                }
                handler.postDelayed(runnable,500)
            }
        }
    }


    fun startGame(view: View){
        binding.timeLabelView.text = "Time: 20"
        binding.scoreLabelView.text = "Score: 0"
        score = 0
        binding.startButtonView.visibility = View.INVISIBLE
        binding.startButtonView.isEnabled = false
        handler.post(runnable)
        object: CountDownTimer(time,1000){
            override fun onTick(p0: Long) {
                binding.timeLabelView.text = "Time: ${p0/1000}"
            }

            override fun onFinish() {
                binding.timeLabelView.text = "Time: 0"
                handler.removeCallbacks(runnable)
                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart?")
                alert.setNegativeButton("No",object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        binding.timeLabelView.text = "Time: ${time/1000}"
                        binding.scoreLabelView.text = "Score: 0"
                        score = 0
                        binding.startButtonView.visibility = View.VISIBLE
                        binding.startButtonView.isEnabled = true
                        setInvisible()
                    }
                })
                alert.setPositiveButton("Yes",object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        startGame(binding.startButtonView)
                    }
                })
                alert.setCancelable(false)
                alert.show()
            }
        }.start()
    }

    fun pointScored(view: View){
        if(!binding.startButtonView.isVisible){
            score += 1
            binding.scoreLabelView.text = "Score: ${score}"
        }
    }
}