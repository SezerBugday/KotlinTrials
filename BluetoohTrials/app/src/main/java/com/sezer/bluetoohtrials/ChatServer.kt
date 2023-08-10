package com.sezer.bluetoohtrials

import androidx.activity.ComponentActivity

fun <Application> startServer(app: Application, activity: ComponentActivity) {
    bluetoothManager = app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    adapter = bluetoothManager.adapter
    if (!adapter.isEnabled) {
        _requestEnableBluetooth.value = true

        val takeResultListener =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == -1) {
                    Toast.makeText(activity, "Bluetooth ON", Toast.LENGTH_LONG).show()
                    setupGattServer(app)
                    startAdvertisement()
                } else {
                    Toast.makeText(activity, "Bluetooth OFF", Toast.LENGTH_LONG).show()
                }
            }

        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        takeResultListener.launch(intent)

    } else {
        _requestEnableBluetooth.value = false
        setupGattServer(app)
        startAdvertisement()
    }
}