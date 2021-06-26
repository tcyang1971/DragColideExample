package tw.edu.pu.csim.tcyang.dragcolideexample

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnTouchListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SetFullScreen() //水平全螢幕

        txv1.setOnTouchListener(this)
        txv2.setOnTouchListener(this)
    }

    fun SetFullScreen(){
        //設定螢幕水平顯示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        //隱藏狀態列
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // 隱藏動作列
        val actionBar = supportActionBar
        actionBar!!.hide()
    }

    override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_MOVE) {
            v.y = motionEvent.rawY-v.height/2
            v.x = motionEvent.rawX-v.width/2

            var rTxv: Rect = Rect(v.x.toInt(), v.y.toInt(),
                v.x.toInt() + v.width, v.y.toInt() + v.height)
            var rBtnx: Rect = Rect(btn.x.toInt(), btn.y.toInt(),
                btn.x.toInt() + btn.width, btn.y.toInt() + btn.height)

            if(rTxv.intersect(rBtnx)) {
                if (v==txv1){
                    btn.text = "文字1碰撞到按鈕"
                }
                else if  (v==txv2){
                    btn.text = "文字2碰撞到按鈕"
                }
            }
            else{
                btn.text = "按鈕"
            }
        }
        return true
    }


}