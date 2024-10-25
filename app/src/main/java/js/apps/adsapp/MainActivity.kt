package js.apps.adsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import js.apps.adsapp.ui.theme.AdsAppTheme

class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LaunchedEffect(Unit) {
                val adRequest = AdRequest.Builder().build()
                InterstitialAd.load(
                    this@MainActivity,
                    "ca-app-pub-8489206541399786/8590359620",
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            mInterstitialAd = interstitialAd
                        }

                        // ... (manejo de errores)
                    }
                )
            }
            AdsAppTheme {
                AdsScreen()
            }
        }
    }


    @Composable
    fun AdsScreen() {
        val localContext = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@MainActivity)
                }else{
                    Toast.makeText(localContext, "No se pudo mostrar el anuncio", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Mostrar interstitial")
            }
            Spacer(modifier = Modifier.weight(1f))

            BannerAd(adUnitId = "ca-app-pub-8489206541399786/2797594003", modifier = Modifier
                .fillMaxWidth())

        }
    }

    @Composable
    fun BannerAd(adUnitId: String, modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                AdView(context).apply {
                    setAdUnitId(adUnitId)
                    setAdSize(AdSize.BANNER)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
