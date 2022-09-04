package com.sopian.mygithub

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.sopian.mygithub.core.ui.ConnectionViewModel
import com.sopian.mygithub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val connectionViewModel: ConnectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            banner.setLeftButtonAction { banner.dismiss() }
            banner.setRightButtonAction {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                    startActivity(panelIntent)
                } else {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivity(intent)
                }
            }

            checkConnectivity()
        }
    }

    private fun checkConnectivity() {
        binding.apply {
            connectionViewModel.hasConnection.observe(this@MainActivity) {
                if (it) {
                    banner.isVisible = false
                } else {
                    banner.show()
                    banner.isVisible = true
                }
            }
        }
    }
}