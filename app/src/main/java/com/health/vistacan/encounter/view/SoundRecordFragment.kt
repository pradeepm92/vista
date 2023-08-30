package com.health.vistacan.encounter.view

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import coil.load
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.health.vistacan.R
import com.health.vistacan.databinding.FragmentSoundRecordBinding
import com.health.vistacan.encounter.viewmodel.EncounterViewModel
import java.io.File
import java.io.IOException
import java.util.*


public class SoundRecordFragment : Fragment(), View.OnClickListener {
    lateinit var Encounterbinding: FragmentSoundRecordBinding
    lateinit var encounterviewmodel: EncounterViewModel
    private lateinit var navController: NavController
    private lateinit var requestRecordPermissionLauncher: ActivityResultLauncher<String>
    lateinit var mediaRecorder: MediaRecorder
    val REQUEST_AUDIO_PERMISSION_CODE = 100
    var seconds = 0
    var playableseconds = 0
    var dummyseconds = 0
    var isplaying: Boolean = false
    var ispause: Boolean = false
    var isrecording: Boolean = false
    lateinit var handler: Handler
    var path: String? = null
    var player: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Encounterbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sound_record, container, false)
        navController = NavHostFragment.findNavController(this)
        handler = Handler()
        doInitContent()
        player = ExoPlayer.Builder(requireContext()).build()
        Encounterbinding.playerView.controllerHideOnTouch = false
        Encounterbinding.playerView.controllerShowTimeoutMs = 0
        Encounterbinding.playerView.player = player

        Encounterbinding.playerView.visibility = View.GONE
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            READ_MEDIA_AUDIO
        } else {
            RECORD_AUDIO
        }

        requestRecordPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startRecord()
            } else {
                Toast.makeText(requireContext(), "Permission required", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            requestRecordPermissionLauncher.launch(permission)
        }

        return Encounterbinding.root
    }

    private fun doInitContent() {
        Encounterbinding.mikeImg.load(R.drawable.mike)
        Encounterbinding.recordImg.load(R.drawable.record)
        Encounterbinding.playImg.load(R.drawable.play)
        Encounterbinding.deleteImg.load(R.drawable.delete)
        Encounterbinding.playImg.visibility = View.GONE
        Encounterbinding.deleteImg.visibility = View.GONE
        Encounterbinding.submitBtn.visibility = View.GONE
        Encounterbinding.recordImg.setOnClickListener(this)
        Encounterbinding.playImg.setOnClickListener(this)
        Encounterbinding.deleteImg.setOnClickListener(this)
        Encounterbinding.submitBtn.setOnClickListener(this)
    }

//    private fun requestRecordPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            val permission = READ_MEDIA_AUDIO
//            val permissionGranted = PackageManager.PERMISSION_GRANTED
//            requestPermissions(
//                arrayOf(permission),
//                REQUEST_AUDIO_PERMISSION_CODE
//            )
//
//        }else{
//            val permission = RECORD_AUDIO
//            val permissionGranted = PackageManager.PERMISSION_GRANTED
//            requestPermissions(
//                arrayOf(permission),
//                REQUEST_AUDIO_PERMISSION_CODE
//            )
//
//
//        }
//
//
//    }
//
//    private fun checkRecordPermission(): Boolean {
//        val permissionGranted = PackageManager.PERMISSION_GRANTED
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            val permission = READ_MEDIA_AUDIO
//            if (ContextCompat.checkSelfPermission(requireActivity(), permission) != permissionGranted) {
//                requestRecordPermission()
//                return false
//            }
//
//        }else{
//            val permission = RECORD_AUDIO
//            if (ContextCompat.checkSelfPermission(requireActivity(), permission) != permissionGranted) {
//                requestRecordPermission()
//                return false
//            }
//        }
//
//        return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startRecord()
//            } else {
//                Toast.makeText(requireContext(), "permission required", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
private fun requestRecordPermission() {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        READ_MEDIA_AUDIO
    } else {
        RECORD_AUDIO
    }
    val permissionGranted = PackageManager.PERMISSION_GRANTED
    when {
        ContextCompat.checkSelfPermission(requireActivity(), permission) == permissionGranted -> {
            startRecord()
        }
        else -> {
            val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    startRecord()
                } else {
                    Toast.makeText(requireContext(), "Permission required", Toast.LENGTH_SHORT).show()
                }
            }
            requestPermissionLauncher.launch(permission)
        }
    }
}

    private fun checkRecordPermission(): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            READ_MEDIA_AUDIO
        } else {
            RECORD_AUDIO
        }
        val permissionGranted = PackageManager.PERMISSION_GRANTED
        return when {
            ContextCompat.checkSelfPermission(requireActivity(), permission) == permissionGranted -> {
                true
            }
            else -> {
                requestRecordPermission()
                false
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecord()
            } else {
                Toast.makeText(requireContext(), "Permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun startRecord() {
        handler.post(Runnable {
            isrecording = true
            mediaRecorder = MediaRecorder()
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            path = getRecordingFilePath()
            mediaRecorder!!.setOutputFile(path)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                mediaRecorder!!.prepare()
                mediaRecorder!!.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Encounterbinding.mikeImg.visibility = View.VISIBLE
            Encounterbinding.lottie.visibility = View.GONE
            playableseconds = 0
            seconds = 0
            dummyseconds = 0
            Encounterbinding.recordImg.load(R.drawable.stop)
            Encounterbinding.playImg.visibility = View.GONE
            Encounterbinding.deleteImg.visibility = View.GONE
            Encounterbinding.submitBtn.visibility = View.GONE
            runTimer()
        })
    }


    override fun onClick(v: View?) {
        when (v) {
            Encounterbinding.recordImg -> {
                if (checkRecordPermission()) {
                    Encounterbinding.playerView.visibility = View.GONE

                    if (!isrecording) {
                        handler.post(Runnable {
                            isrecording = true
                            mediaRecorder = MediaRecorder()
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                            path = getRecordingFilePath()
                            mediaRecorder!!.setOutputFile(path)
                            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                            try {
                                mediaRecorder!!.prepare()
                                mediaRecorder!!.start()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                            Encounterbinding.mikeImg.visibility = View.VISIBLE
                            Encounterbinding.lottie.visibility = View.GONE
                            playableseconds = 0
                            seconds = 0
                            dummyseconds = 0
                            Encounterbinding.recordImg.load(R.drawable.stop)
                            Encounterbinding.playImg.visibility = View.GONE
                            Encounterbinding.deleteImg.visibility = View.GONE
                            Encounterbinding.submitBtn.visibility = View.GONE
                            runTimer()
                        })


                    } else {
                        handler.post(Runnable {
                            mediaRecorder.stop()
                            mediaRecorder.release()
//                            mediaRecorder = null
                            playableseconds = seconds - 1
                            dummyseconds = seconds - 1
                            seconds = 0
                            isrecording = false
                            Encounterbinding.mikeImg.visibility = View.VISIBLE
                            Encounterbinding.lottie.visibility = View.GONE
                            handler.removeCallbacksAndMessages(null)
                            Encounterbinding.recordImg.load(R.drawable.record)
                            Encounterbinding.playImg.visibility = View.VISIBLE
                            Encounterbinding.deleteImg.visibility = View.VISIBLE
                            Encounterbinding.submitBtn.visibility = View.VISIBLE

                        })
                    }
                } else {
                    requestRecordPermission()
                }

            }

            Encounterbinding.playImg -> {
                Log.e("entry", "entry")

                ispause = false
                if (!path.equals(null) && !path.equals("")) {
                    val file = File(path)
                    Log.e("path", file.toString())
                    val mediaItem: MediaItem = buildMediaSource(file)
                    Log.e("mediaItem", mediaItem.toString())
                      player?.setMediaItem(mediaItem)
                    player?.prepare()
                    Encounterbinding.playerView.visibility = View.VISIBLE
                    if (file.exists()) {
                        if (!isplaying) {
                            player?.playWhenReady = true;
                        }
                    }
                }
            }

            Encounterbinding.deleteImg -> {
                Encounterbinding.timeSec.text = "00:00"
                Encounterbinding.playImg.visibility = View.GONE
                Encounterbinding.playerView.visibility = View.GONE
                player?.stop()
                player?.clearMediaItems()
                deleteRecordedVoice()

            }
            Encounterbinding.submitBtn -> {

                navController.navigate(R.id.action_newEncounterFragment_to_diagnosisFragment)
            }
        }
    }


    private fun runTimer() {
        handler.post(timerRunnable)
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            val minutes = (seconds % 3600) / 60
            Log.e("minut", minutes.toString())
//            val secs = (seconds % 60)
            val secs = Math.round((seconds % 60).toDouble())
            Log.e("secs", secs.toString())
            val time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
            Encounterbinding.timeSec.text = time


            if (isrecording || (isplaying && playableseconds >= 0)) {
                seconds++
                playableseconds--

                if (playableseconds < 0 && isplaying) {
//                    mediaPlayer.stop()
//                    mediaPlayer.release()
                    playableseconds = dummyseconds
                    seconds = 0
                    isplaying = false
                    Encounterbinding.mikeImg.visibility = View.VISIBLE
                    Encounterbinding.lottie.visibility = View.GONE
                    Encounterbinding.playImg.load(R.drawable.play)
                    Encounterbinding.deleteImg.visibility = View.VISIBLE
                    handler.removeCallbacks(this)
                }
            }

            handler.postDelayed(this, 1000)

        }
    }

    private fun deleteRecordedVoice() {
        val file = File(path)
        file.delete()
        path = null
        Toast.makeText(requireContext(), "voice deleted", Toast.LENGTH_SHORT).show()
        Encounterbinding.deleteImg.visibility = View.GONE
        Encounterbinding.submitBtn.visibility = View.GONE
        seconds = 0
        playableseconds = 0
        dummyseconds = 0

    }


    private fun getRecordingFilePath(): String {
        val time = System.currentTimeMillis().toString()
        val musicDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDir, "record" + time)
        return file.absolutePath
    }

    private fun buildMediaSource(file: File): MediaItem {
        val mediaItem: MediaItem = MediaItem.fromUri(Uri.fromFile(file))
        return mediaItem;
    }


    override fun onDestroy() {
        super.onDestroy()
        player!!.release()
    }

    override fun onStop() {
        super.onStop()
//        player!!.release()
    }

}