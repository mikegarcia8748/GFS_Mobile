package com.gfs.mobile.system.ui.screen.milling.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.system.R
import com.gfs.mobile.system.ui.component.Toolbar
import com.gfs.mobile.system.ui.theme.GFSMaterialTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun MillAttendanceScreen(
    navController: NavHostController,
    viewModel: MillAttendanceViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MillAttendanceContent(
        callback = MillAttendanceCallback(
            onBackPressed = {
                navController.popBackStack()
            },
            onClickPresent = {
                viewModel.tagAsPresent(it)
            },
            onClickAbsent = {
                viewModel.tagAsAbsent(it)
            }
        ),
        uiState = uiState
    )
}

@Composable
private fun MillAttendanceContent(
    callback: MillAttendanceCallback,
    uiState: MillAttendanceUiState
) {
    Scaffold(
        topBar = {
            Toolbar(
                onBackPressed = { callback.onBackPressed() },
                title = stringResource(id = R.string.label_attendance)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .padding(horizontal = dimensionResource(id = R.dimen.view_padding16))
        ) {

            item {
                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.view_padding8))
                        .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                    text = uiState.currentDate,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (uiState.loadingWorkers) {
                item {
                    repeat(7) {
                        WorkerShimmer()
                    }
                }
            } else if (uiState.workerList?.isEmpty() == true) {
                item {
                    // TODO: Add the lottie animation here...
                }
            } else if (!uiState.loadingWorkerError.isNullOrEmpty()) {
                item {
                    // TODO: Add the error lottie animation here...
                }
            } else {
                val workerList = uiState.workerList.orEmpty()

                items(workerList.size) {
                    val worker = workerList[it]

                    ItemWorker(
                        fullName = worker.fullName.orEmpty(),
                        alias = worker.userName.orEmpty(),
                        isPresent = worker.isPresent,
                        onClickPresent = { callback.onClickPresent(worker.workerID.orEmpty()) },
                        onClickAbsent = { callback.onClickAbsent(worker.workerID.orEmpty()) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemWorker(
    fullName: String,
    alias: String,
    isPresent: Boolean? = null,
    onClickPresent: () -> Unit,
    onClickAbsent: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.view_padding4)),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = dimensionResource(id = R.dimen.view_padding16))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_worker),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.view_padding8)),
                    text = "($alias)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                if (isPresent != null) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = if (isPresent) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding8))
                            ),
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(all = dimensionResource(id = R.dimen.view_padding4))
                                .padding(horizontal = dimensionResource(id = R.dimen.view_padding8)),
                            text = if (isPresent) stringResource(id = R.string.label_present)
                                    else stringResource(id = R.string.label_absent),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isPresent) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.view_padding16)),
                text = fullName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal
            )

            HorizontalDivider()

            // Validate if the attendance is already tagged...
            // Do not preview the option for tagging
            if (isPresent == null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.view_padding8)),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Present
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.view_padding4)),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        onClick = { onClickPresent() }
                    ) {

                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.label_present),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    // Absent
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.view_padding4)),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        onClick = { onClickAbsent() }
                    ) {

                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.label_absent),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkerShimmer() {
    Column(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.view_padding4))
            .padding(horizontal = dimensionResource(id = R.dimen.view_padding8)),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.view_padding24))
                .fillMaxWidth()
                .shimmer()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding8))
                )
        )
        Box(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.view_padding8))
                .height(dimensionResource(id = R.dimen.view_padding16))
                .fillMaxWidth(.0f)
                .shimmer()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.view_padding8))
                )
        )

    }
}

@Composable
@Preview(showBackground = true)
private fun WorkerShimmerPreview() {
    GFSMaterialTheme {
        WorkerShimmer()
    }
}

@Composable
@Preview(showBackground = true)
private fun ItemWorkerPreview() {
    GFSMaterialTheme {
        ItemWorker(
            fullName = "Lorem Ipsum",
            alias = "Onyong",
            isPresent = true,
            onClickPresent = { },
            onClickAbsent = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MillAttendanceContentPreview() {
    GFSMaterialTheme {
        MillAttendanceContent(
            callback = MillAttendanceCallback(
                onBackPressed = { },
                onClickPresent = { },
                onClickAbsent = { }
            ),
            uiState = MillAttendanceUiState()
        )
    }
}
