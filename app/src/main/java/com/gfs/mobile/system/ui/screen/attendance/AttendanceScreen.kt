package com.gfs.mobile.system.ui.screen.attendance

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun AttendanceScreen(
    navController: NavHostController,
    viewModel: AttendanceViewModel = hiltViewModel()
) {

}


@Composable
private fun AttendanceContent(
    callback: AttendanceCallback,
    uiState: AttendanceUIState
) {

}

@Composable
@Preview(showBackground = true)
private fun AttendanceContentPreview() {

}