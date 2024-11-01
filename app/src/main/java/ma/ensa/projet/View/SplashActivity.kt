package ma.ensa.projet.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ma.ensa.projet.R

class SplashActivity : AppCompatActivity() {
    private lateinit var splashText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashText = findViewById(R.id.splash_text)

        // Créer une animation pour faire disparaître le texte
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 1500 // 1.5 secondes
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // À faire au début de l'animation
            }

            override fun onAnimationEnd(animation: Animation) {
                // Passer à TaskMain une fois l'animation terminée
                startActivity(Intent(this@SplashActivity, TaskMain::class.java))
                finish() // Terminer la SplashActivity
            }

            override fun onAnimationRepeat(animation: Animation) {
                // À faire lors de la répétition de l'animation
            }
        })

        // Lancer l'animation sur le texte
        splashText.startAnimation(fadeOut)

        Handler(Looper.getMainLooper()).postDelayed({
            splashText.startAnimation(fadeOut)
        }, 1000) // 1 seconde avant de commencer à faire disparaître le texte
    }
}
