package com.gfs.mobile.feature.payroll.ui.screen.attendance

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus
import com.gfs.mobile.core.domain.model.business.BusinessLine
import com.gfs.mobile.core.ui.theme.GFSMaterialTheme

@Composable
fun AttendanceScreen(
    navController: NavHostController,
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AttendanceContent(
        callback = AttendanceCallback(
            onBackPressed = { navController.popBackStack() },
            onBusinessLineSelected = { viewModel.setBusinessLine(it) },
            onMarkStatus = { id, status -> viewModel.markStatus(id, status) },
            onDismissError = { viewModel.refreshAttendance() }
        ),
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AttendanceContent(
    callback: AttendanceCallback,
    uiState: AttendanceUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Attendance") },
                navigationIcon = {
                    IconButton(
                        onClick = { callback.onBackPressed() },
                        modifier = Modifier.testTag("attendance-back-button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            BusinessLineSelector(
                selectedLine = uiState.currentBusinessLine,
                onLineSelected = callback.onBusinessLineSelected
            )

            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.summaries.size) { index ->
                        val summary = uiState.summaries[index]
                        WorkerAttendanceItem(
                            summary = summary,
                            onMarkStatus = { callback.onMarkStatus(summary.workerID, it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BusinessLineSelector(
    selectedLine: BusinessLine,
    onLineSelected: (BusinessLine) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedLine.ordinal,
        edgePadding = 16.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.testTag("business-line-selector")
    ) {
        BusinessLine.entries.forEach { line ->
            Tab(
                selected = selectedLine == line,
                onClick = { onLineSelected(line) },
                text = { Text(line.displayName) },
                modifier = Modifier.testTag("tab-${line.id.lowercase()}")
            )
        }
    }
}

@Composable
private fun WorkerAttendanceItem(
    summary: com.gfs.mobile.core.domain.model.attendance.AttendanceSummary,
    onMarkStatus: (AttendanceStatus) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("worker-item-${summary.workerID}")
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.gfs.mobile.core.ui.R.drawable.ic_worker),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(summary.fullName, fontWeight = FontWeight.Bold)
                    Text("@${summary.userName}", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.weight(1f))
                summary.overallStatus?.let {
                    StatusBadge(status = it)
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AttendanceStatus.entries.forEach { status ->
                    val isSelected = summary.overallStatus == status
                    OutlinedButton(
                        onClick = { onMarkStatus(status) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .testTag("btn-${status.name.lowercase()}"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) statusColor(status) else Color.Transparent,
                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(1.dp, if (isSelected) statusColor(status) else MaterialTheme.colorScheme.outline)
                    ) {
                        Text(
                            status.name.take(1).uppercase(),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: AttendanceStatus) {
    Surface(
        color = statusColor(status),
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.testTag("status-badge")
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

private fun statusColor(status: AttendanceStatus): Color = when (status) {
    AttendanceStatus.PRESENT -> Color(0xFF4CAF50)
    AttendanceStatus.ABSENT -> Color(0xFFF44336)
    AttendanceStatus.LATE -> Color(0xFFFF9800)
    AttendanceStatus.HALFDAY -> Color(0xFF2196F3)
}

@Preview(showBackground = true)
@Composable
private fun AttendancePreview() {
    GFSMaterialTheme {
        // Mock state and callback
    }
}
