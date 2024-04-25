package com.gfs.mobile.system.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun NumKey(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button (
        modifier = Modifier
            .then(modifier)
            .padding(all = dimensionResource(id = R.dimen.view_padding4)),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        onClick = {
            onClick()
        },
        enabled = enabled
    ) {
        Box(modifier = Modifier) {
            content()
        }
//        if (!backSpace) {

//        } else {
//            Icon(
//                modifier = Modifier
//                    .padding(vertical = dimensionResource(id = R.dimen.view_padding8)),
//                painter = painterResource(id = R.drawable.ic_backspace),
//                contentDescription = null
//            )
//        }
    }
}

@Composable
@Preview(showBackground = true)
private fun NumKeyPreview() {
    GFSMaterialTheme {
        NumKey(
            modifier = Modifier,
            enabled = true,
            onClick = { },
            content = {
                Text(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                    text = "1"
                )
            }
        )
    }
}