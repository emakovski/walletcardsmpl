package com.makovsky.walletcardsmpl

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.makovsky.walletcardsmpl.ui.theme.PurpleGrey80
import com.makovsky.walletcardsmpl.ui.theme.WalletCardSmplTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WalletCardSmplTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            UserProfileCard(generateBarcode("886498407578"))
            Spacer(modifier = Modifier.height(16.dp))
            AddToWalletButton()
        }
    }
}

@Composable
fun UserProfileCard(barcodeBitmap: Bitmap?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(10.dp),
        colors = CardDefaults.cardColors(PurpleGrey80),
        elevation = CardDefaults.cardElevation(10.dp)
        ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Multipass test",
                style = typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            BarcodeScanner(barcodeBitmap)

            Text(
                text = "886498407578",
                style = typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun BarcodeScanner(barcodeBitmap: Bitmap?) {
    if (barcodeBitmap != null) {
        Image(
            bitmap = barcodeBitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AddToWalletButton() {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = "Add Card To Wallet")
    }
}

fun generateBarcode(content: String): Bitmap? {
    try {
        val hints = mutableMapOf<EncodeHintType, Any>().apply {
            put(EncodeHintType.MARGIN, 1)
            put(EncodeHintType.CHARACTER_SET, "UTF-8")
        }
        val barcodeMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, 750, 150, hints)
        val barcodeBitmap = Bitmap.createBitmap(barcodeMatrix.width, barcodeMatrix.height, Bitmap.Config.ARGB_8888)
        for (x in 0 until barcodeMatrix.width) {
            for (y in 0 until barcodeMatrix.height) {
                barcodeBitmap.setPixel(x, y, if (barcodeMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return barcodeBitmap
    } catch (e: WriterException) {
        e.printStackTrace()
    }
    return null
}

@Preview(showSystemUi = true)
@Composable
fun ContentPreview() {
    WalletCardSmplTheme {
        Content()
    }
}