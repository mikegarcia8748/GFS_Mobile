package com.gfs.mobile.system.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun NumPad(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledPeriod: Boolean = false,
    onNumKeyClick: (number: String) -> Unit,
    onClickBackSpace: () -> Unit
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .padding(all = dimensionResource(id = R.dimen.view_padding16))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "1"
                    )
                },
                onClick = {
                    onNumKeyClick("1")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "2"
                    )
                },
                onClick = {
                    onNumKeyClick("2")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "3"
                    )
                },
                onClick = {
                    onNumKeyClick("3")
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "4"
                    )
                },
                onClick = {
                    onNumKeyClick("4")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "5"
                    )
                },
                onClick = {
                    onNumKeyClick("5")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "6"
                    )
                },
                onClick = {
                    onNumKeyClick("6")
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "7"
                    )
                },
                onClick = {
                    onNumKeyClick("7")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "8"
                    )
                },
                onClick = {
                    onNumKeyClick("8")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "9"
                    )
                },
                onClick = {
                    onNumKeyClick("9")
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (enabledPeriod) {
                NumKey(
                    modifier = Modifier
                        .weight(1f),
                    enabled = enabled,
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                            text = "."
                        )
                    },
                    onClick = {
                        onNumKeyClick(".")
                    }
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                        text = "0"
                    )
                },
                onClick = {
                    onNumKeyClick("0")
                }
            )

            NumKey(
                modifier = Modifier
                    .weight(1f),
                enabled = enabled,
                content = {
                    Icon(
                        modifier = Modifier
                            .padding(13.dp),
                        painter = painterResource(id = R.drawable.ic_backspace),
                        contentDescription = null)
                },
                onClick = {
                    onClickBackSpace()
                }
            )
        }
    }
}

@Composable
fun InputPreview(
    inputLength: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.view_padding8))
            .padding(horizontal = dimensionResource(id = R.dimen.view_padding16)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 1
        )

        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 2
        )

        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 3
        )

        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 4
        )

        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 5
        )

        PINIndicator(
            modifier = Modifier
                .weight(1f),
            content = { },
            backgroundColor = MaterialTheme.colorScheme.outline,
            fillColor = MaterialTheme.colorScheme.primary,
            filled = inputLength >= 6
        )
    }
}

@Composable
private fun PINIndicator(
    modifier: Modifier,
    content: @Composable () -> Unit,
    filled: Boolean = false,
    backgroundColor: Color,
    fillColor: Color,
) {
    Row(
        modifier = Modifier
            .then(modifier)
            .padding(horizontal = dimensionResource(id = R.dimen.view_padding8)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(
                    if (!filled) dimensionResource(id = R.dimen.view_padding8)
                    else dimensionResource(id = R.dimen.view_padding16)
                )
                .clip(CircleShape)
                .background(if (!filled) backgroundColor else fillColor)
        ) {
            content()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PINIndicatorPreview() {
    GFSMaterialTheme {
        InputPreview(inputLength = 3)
    }
}

@Composable
@Preview(showBackground = true)
private fun NumPadPreview() {
    GFSMaterialTheme {
        NumPad(
            onNumKeyClick = { },
            onClickBackSpace = { }
        )
    }
}