/* (c) Helios Software Developer. All rights reserved. */
package com.heliossoftwaredeveloper.heliosandroidfundamentals

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.heliossoftwaredeveloper.heliosandroidfundamentals.Notification.NotificationActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Ruel N. Grajo on 06/24/2019.
 *
 * Main Activity to hold fragments and views.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_notification.setOnClickListener(View.OnClickListener {
            startActivity(NotificationActivity.newIntent(this))
        })
    }
}
