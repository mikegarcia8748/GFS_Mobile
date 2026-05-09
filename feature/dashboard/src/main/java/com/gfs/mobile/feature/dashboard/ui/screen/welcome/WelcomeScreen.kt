package com.gfs.mobile.feature.dashboard.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gfs.mobile.feature.dashboard.R
import com.gfs.mobile.core.ui.theme.GFSMaterialTheme

@Composable
fun WelcomeScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = com.gfs.mobile.core.ui.R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun WelcomeScreenPreview() {
    GFSMaterialTheme {
        WelcomeScreen()
    }
}
