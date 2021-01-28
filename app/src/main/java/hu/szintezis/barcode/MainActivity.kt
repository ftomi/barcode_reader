package hu.szintezis.barcode
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_main.*

const val TAG: String = "TAG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            scanQRCode()
        }
    }

    private fun scanQRCode() = runWithPermissions(Manifest.permission.CAMERA){
        val integrator = IntentIntegrator(this).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scanneld be a vonalkódot!")
        }
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) Toast.makeText(this, "Megszakítva", Toast.LENGTH_LONG).show()
            else
            {
                Toast.makeText(this, "Vonalkód tartalma: " + result.contents, Toast.LENGTH_LONG).show()
                tvBarcode.text = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}