package anna.money.ui_kit.text_message

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import anna.money.ui_kit.MessagePosition
import anna.money.ui_kit.R

@Composable
fun AnnaTextMessage(
    text: String,
    position: MessagePosition,
    onClick: () -> Unit = {},
) {
    Text(
        modifier = Modifier
            .clip(
                shape = when (position) {
                    MessagePosition.Start -> RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_borderRadius_top_left),
                        topEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_borderRadius_top_right),
                        bottomStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_borderRadius_bottom_left),
                        bottomEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_borderRadius_bottom_right),
                    )
                    MessagePosition.Middle -> RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_mid_borderRadius_top_left),
                        topEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_mid_borderRadius_top_right),
                        bottomStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_mid_borderRadius_bottom_left),
                        bottomEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_mid_borderRadius_bottom_right),
                    )
                    MessagePosition.End -> RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_end_borderRadius_top_left),
                        topEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_end_borderRadius_top_right),
                        bottomStart = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_end_borderRadius_bottom_left),
                        bottomEnd = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_end_borderRadius_bottom_right),
                    )
                },
            )
            .background(
                color = colorResource(id = R.color.tigerds_bubble_chat_anna_background_color),
            )
            .clickable { onClick() }
            .padding(
                start = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_spacing_inset_left),
                end = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_spacing_inset_right),
                top = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_spacing_inset_top),
                bottom = dimensionResource(id = R.dimen.tigerds_bubble_chat_anna_start_spacing_inset_bottom),
            ),
        text = text,
        color = colorResource(id = R.color.tigerds_bubble_chat_anna_text_color),
    )
}

@Composable
@Preview
private fun Preview() {
    val positions = MessagePosition.values()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (position in positions)
            AnnaTextMessage(
                text = position.toString(),
                position = position,
            )
    }
}

class AnnaTextMessageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var text by mutableStateOf("")
    var position by mutableStateOf(MessagePosition.End)

    @Composable
    override fun Content() {
        AnnaTextMessage(
            text = text,
            position = position,
        )
    }
}