package com.example.dancebuddy.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dancebuddy.coredata.DanceClass

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DanceClassSection(
    innerPadding: PaddingValues,
    danceClasses: List<DanceClass>,
    onClassClick: (DanceClass) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
    ) {
        val cardWidth = maxWidth / 2 - 16.dp
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            danceClasses.forEach { danceClass ->
                DanceClassCard(
                    modifier = Modifier
                        .width(cardWidth)
                        .wrapContentHeight(),
                    danceClass = danceClass,
                    onClick = { onClassClick(danceClass) }
                )
            }
        }
    }
}

@Composable
fun DanceClassCard(modifier: Modifier = Modifier, danceClass: DanceClass, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = danceClass.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Level: ${danceClass.level}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Participants: ${danceClass.users.size}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DanceClassModal(danceClass: DanceClass, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = danceClass.title)
        },
        text = {
            Column {
                Text("Level: ${danceClass.level}")
                Text("Time: ${danceClass.time}")
                Text("Days: ${danceClass.days.joinToString(", ")}")
                Text("Description: ${danceClass.description}")
                Text("Participants:")
                danceClass.users.forEach { user ->
                    Text("- ${user.firstName} ${user.lastName}")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}