package anna.money.tiger_ui_kit_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import anna.money.tiger_ui_kit.MessagePosition
import anna.money.tiger_ui_kit.text_message.AnnaTextMessage
import anna.money.tiger_ui_kit.text_message.UserTextMessage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    val positions = MessagePosition.values()

    Scaffold(
        backgroundColor = Color.LightGray,
        topBar = {
            TopAppBar(
                title = {
                    Text("Tiger Design System")
                },
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                for (position in positions)
                    AnnaTextMessage(
                        text = position.toString(),
                        position = position,
                    )
            }
            Spacer(Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                for (position in positions)
                    UserTextMessage(
                        text = position.toString(),
                        position = position,
                    )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    Content()
}