package com.gfs.mobile.system.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme

@Composable
fun OutlineTextField2(
    modifier: Modifier,
    hint: String,
    value: String,
    onValueChanged: (value: String) -> Unit,
    maxLines: Int = 1,
    enabled: Boolean = true,
    textAlign: TextAlign = TextAlign.Start,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onClick: () -> Unit = { },
) {

    Box(
        modifier = Modifier
            .then(modifier)
            .background(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding4)),
                color = MaterialTheme.colorScheme.background
            )
            .clickable { if (!enabled) onClick() },
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.view_padding8)),
            maxLines = maxLines,
            value = value,
            onValueChange = { onValueChanged(it) },
            enabled = enabled,
            textStyle = LocalTextStyle.current.copy(textAlign = textAlign),
            label = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = hint,
                    textAlign = textAlign,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = keyboardCapitalization,
                keyboardType = keyboardType,
                imeAction = imeAction
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun OutlineTextFieldPreview() {
    GFSMaterialTheme {
        OutlineTextField2(
            modifier = Modifier,
            hint = "Customer Name",
            value = "Marcelito Mangsat",
            onValueChanged = { },
            maxLines = 1,
            enabled = true
        )
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier,
    value: String,
    onValueChanged: (value: String) -> Unit,
    onClickCancel: () -> Unit
) {

    OutlinedTextField(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.view_padding4)),
        maxLines = 1,
        value = value,
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(id = R.dimen.view_padding8))),
        label = {
                Text(
                    text = stringResource(id = R.string.sentence_search_customer)
                )
        },
        onValueChange = { onValueChanged(it) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            if (value.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
                        .clickable { onClickCancel() },
                    text = stringResource(id = R.string.label_cancel),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun SearchTextFieldPreview() {
    GFSMaterialTheme {
        SearchTextField(
            modifier = Modifier,
            value = stringResource(id = R.string.label_sub_total),
            onValueChanged = { },
            onClickCancel = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTextField(
    userNames: List<String> = emptyList(),
    onSelectItem: (value: String) -> Unit = { }
) {
    var selectedItem by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {

            TextField(
                value = selectedItem,
                onValueChange = { }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { }
            ) {

                repeat(userNames.size) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = userNames[it],
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        },
                        onClick = {
                            onSelectItem(userNames[it])
                            selectedItem = userNames[it]
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DropDownTextFieldPreview() {
    GFSMaterialTheme {
        DropDownTextField(
            userNames = emptyList(),
            onSelectItem = {}
        )
    }
}