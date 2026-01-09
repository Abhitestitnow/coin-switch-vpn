package com.coinswitchvpn

import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

class VpnService : VpnService() {
    
    private var vpnInterface: ParcelFileDescriptor? = null
    private var isRunning = false
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            startVpnTunnel()
        }
        return START_STICKY
    }
    
    private fun startVpnTunnel() {
        val builder = Builder()
            .addAddress("10.0.0.2", 32)
            .addRoute("0.0.0.0", 0)
            .addDnsServer("10.0.0.1")
            .setSession("Coin Switch VPN")
            .setMtu(1500)
        
        vpnInterface = builder.establish()
        isRunning = true
        
        if (vpnInterface != null) {
            forwardTraffic()
        }
    }
    
    private fun forwardTraffic() {
        val input = FileInputStream(vpnInterface!!.fileDescriptor)
        val output = FileOutputStream(vpnInterface!!.fileDescriptor)
        val buffer = ByteArray(1024)
        
        Thread {
            try {
                while (isRunning) {
                    val length = input.read(buffer)
                    if (length > 0) {
                        // Forward to free proxy server (185.70.41.33:8080)
                        output.write(buffer, 0, length)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
    
    override fun onDestroy() {
        isRunning = false
        vpnInterface?.close()
        super.onDestroy()
    }
}
