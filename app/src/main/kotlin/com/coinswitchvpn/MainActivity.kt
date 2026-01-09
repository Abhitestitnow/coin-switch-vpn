package com.coinswitchvpn

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private var isConnected = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        connectBtn.setOnClickListener {
            if (isConnected) {
                stopVpn()
            } else {
                startVpn()
            }
        }
    }
    
    private fun startVpn() {
        val intent = VpnService.prepare(this)
        if (intent == null) {
            startService(Intent(this, VpnService::class.java))
            findViewById<Button>(R.id.connectBtn).text = "🔓 Disconnect VPN"
            isConnected = true
            Toast.makeText(this, "✅ Connected to US Free Server!", Toast.LENGTH_LONG).show()
        } else {
            startActivityForResult(intent, 0)
        }
    }
    
    private fun stopVpn() {
        stopService(Intent(this, VpnService::class.java))
        findViewById<Button>(R.id.connectBtn).text = "🔐 Connect VPN"
        isConnected = false
        Toast.makeText(this, "❌ VPN Disconnected", Toast.LENGTH_SHORT).show()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == RESULT_OK) {
            startVpn()
        }
    }
}
