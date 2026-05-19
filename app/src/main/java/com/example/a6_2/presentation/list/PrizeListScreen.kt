package com.example.a6_2.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a6_2.domain.entity.NobelPrize
import com.example.a6_2.presentation.list.components.FilterBar
import com.example.a6_2.presentation.list.components.PrizeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrizeListScreen(
    onPrizeClick: (NobelPrize) -> Unit,
    viewModel: PrizeListViewModel
) {
    val state by viewModel.state.collectAsState()
    val filterParams by viewModel.filterParams.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nobel Laureates",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.shadow(4.dp)
            )
        }
    )  { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            when (val currentState = state) {
                is PrizeListState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                is PrizeListState.Success -> {
                    Column {
                        FilterBar(
                            selectedYear = filterParams.year,
                            selectedCategory = filterParams.category,
                            onYearSelected = viewModel::updateYear,
                            onCategorySelected = viewModel::updateCategory,
                            onClearFilters = viewModel::clearFilters,
                            availableYears = viewModel.getAvailableYears(),
                            categories = viewModel.getCategories(),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(
                                items = currentState.prizes,
                                key = { prize -> "${prize.category}_${prize.awardYear}" }
                            ) { prize ->
                                PrizeCard(
                                    prize = prize,
                                    onClick = { onPrizeClick(prize) }
                                )
                            }
                        }
                    }
                }

                is PrizeListState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = " ${currentState.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = viewModel::loadPrizes,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }
    }
}