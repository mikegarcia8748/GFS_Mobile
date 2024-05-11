package com.gfs.mobile.system.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.view_loading_dialog))
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding16))
                )
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LoadingDialogPreview() {
    GFSMaterialTheme {
        LoadingDialog()
    }
}

@Composable
fun ResultDialog(
    result: Result,
    message: String,
    buttonText: String,
    onClickActionButton: () -> Unit
) {

    var displayDialog by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(spec =
//        if (result == Result.SUCCESS)
//            LottieCompositionSpec.RawRes(R.raw.anim_success)
//        else
//            LottieCompositionSpec.RawRes(R.raw.anim_failed)
        LottieCompositionSpec.RawRes(R.raw.anim_failed)
    )
    val progress by animateLottieCompositionAsState(composition = composition)

    LaunchedEffect(key1 = Unit) {
        delay(300)
        displayDialog = true
    }

    if (displayDialog) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding16))
                    )
                    .padding(all = dimensionResource(id = R.dimen.view_padding16)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LottieAnimation(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.view_result_dialog)),
                    composition = composition,
                    progress = { progress }
                )

                Text(
                    modifier = Modifier,
                    text = result.value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                    text = message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal
                )

                DialogActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.view_padding16)),
                    text = buttonText,
                    onClick = { onClickActionButton() }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MillPaymentDialogPreview() {
    GFSMaterialTheme {
        ResultDialog(
            result = Result.SUCCESS,
            message = stringResource(id = R.string.sentence_transaction_save),
            buttonText = stringResource(id = R.string.sentence_back_to_billing),
            onClickActionButton = { }
        )
    }
}

sealed class Result(val value: String) {
    data object SUCCESS: Result(value = "Success")
    data object FAILED: Result(value = "Failed")
}