package com.example.a6_2.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.a6_2.domain.entity.NobelPrize

@Composable
fun PrizeCard(
    prize: NobelPrize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = when (prize.category) {
        "phy" -> Color(0xFF3B82F6)  // синий
        "che" -> Color(0xFF10B981)  // зелёный
        "med" -> Color(0xFFEF4444)  // красный
        "lit" -> Color(0xFFF59E0B)  // оранжевый
        "pea" -> Color(0xFF8B5CF6)  // фиолетовый
        "eco" -> Color(0xFFEC4899)  // розовый
        else -> Color(0xFF6B7280)   // серый
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Верхняя строка: год + категория
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = prize.awardYear,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = categoryColor
                )
                Text(
                    text = when (prize.category) {
                        "phy" -> "Physics"
                        "che" -> "Chemistry"
                        "med" -> "Medicine"
                        "lit" -> "Literature"
                        "pea" -> "Peace"
                        "eco" -> "Economics"
                        else -> prize.categoryFullName
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = categoryColor,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Лауреаты
            prize.laureates.take(2).forEachIndexed { index, laureate ->
                Column {
                    Text(
                        text = laureate.fullName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1F2937)
                    )
                    Text(
                        text = laureate.motivation.take(80) + if (laureate.motivation.length > 80) "…" else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (index < prize.laureates.take(2).size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE5E7EB))
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            if (prize.laureates.size > 2) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+ ${prize.laureates.size - 2} more laureates",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}