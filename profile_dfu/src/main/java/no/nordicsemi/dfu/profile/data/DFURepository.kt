package no.nordicsemi.dfu.profile.data

import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import no.nordicsemi.android.dfu.DfuServiceController
import no.nordicsemi.ui.scanner.DiscoveredBluetoothDevice
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DFURepository @Inject constructor(
    private val fileManger: DFUFileManager,
    private val dfuManager: DFUManager,
    private val progressManager: DFUProgressManager,
) {

    private val _data = MutableStateFlow<DFUData>(IdleStatus)
    val data: StateFlow<DFUData> = _data.asStateFlow()

    var device: DiscoveredBluetoothDevice? = null
    var zipFile: ZipFile? = null
    var dfuServiceController: DfuServiceController? = null

    fun setZipFile(file: Uri): ZipFile? {
        return fileManger.createFile(file)?.also {
            zipFile = it
        }
    }

    fun launch(scope: CoroutineScope) {
        progressManager.registerListener()
        dfuServiceController = dfuManager.install(zipFile!!, device!!)

        progressManager.status.onEach {
            _data.value = it
        }.launchIn(scope)
    }

    fun hasBeenInitialized(): Boolean {
        return device != null
    }

    fun abort() {
        dfuServiceController?.abort()
    }

    fun release() {
        progressManager.unregisterListener()
    }
}