package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * SoundService is a service that plays a sound when started.
 * It ensures that the sound is played only once, and releases resources after playback is complete.
 */
public class SoundService extends Service {

    private static MediaPlayer mediaPlayer;  // MediaPlayer instance for playing the sound.

    /**
     * Called when the service is started. If the sound is not already playing, it starts playing the sound.
     * If the sound is already playing, it does nothing.
     *
     * @param intent  The Intent that started the service.
     * @param flags  Additional data about the service start.
     * @param startId An identifier for this specific request to start the service.
     * @return Returns {@link #START_NOT_STICKY}, indicating that the service should not be restarted if it is killed.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // If the sound is already playing, do not start again.
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return START_NOT_STICKY;
        }

        // Create and start the MediaPlayer to play the sound.
        mediaPlayer = MediaPlayer.create(this, R.raw.win_sound);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> {
                // Release the MediaPlayer when the sound finishes.
                mp.release();
                mediaPlayer = null;  // Reset the MediaPlayer reference
                stopSelf();  // Stop the service once playback is complete
            });
            mediaPlayer.start();
        } else {
            // If MediaPlayer creation fails, stop the service.
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    /**
     * Called when the service is destroyed. Stops and releases the MediaPlayer if it's still playing.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;  // Nullify the MediaPlayer reference
        }
    }

    /**
     * This method is not used in this service as it is a started service and not a bound service.
     *
     * @param intent The Intent that is passed when binding to the service.
     * @return null as binding is not supported.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
