package com.gfs.mobile.system.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import kotlinx.coroutines.delay

@Composable
fun PrimaryButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {

    var displayButton by remember { mutableStateOf(false) }
    var displayText by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = Unit) {
        delay(300)
        displayButton = true
    }

    LaunchedEffect(key1 = Unit) {
        delay(600)
        displayText = true
    }

    AnimatedVisibility(
        visible = displayButton,
        enter = expandVertically(expandFrom = Alignment.CenterVertically)) {

        Button(
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.view_padding4)),
            onClick = { onClick() }
        ) {

            AnimatedVisibility(visible = displayText) {
                Text(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

    }
}
@Composable
fun DialogActionButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {

    var displayText by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(600)
        displayText = true
    }
    Button(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.view_padding4)),
        onClick = { onClick() }
    ) {

        AnimatedVisibility(visible = displayText) {
            Text(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DialogActionButtonPreview() {
    GFSMaterialTheme {
        DialogActionButton(
            modifier = Modifier,
            text = stringResource(id = R.string.sentence_back_to_billing),
            onClick = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PrimaryButtonPreview() {
    GFSMaterialTheme {
        PrimaryButton(
            modifier = Modifier,
            text = stringResource(id = R.string.sentence_back_to_billing),
            onClick = { }
        )
    }
}