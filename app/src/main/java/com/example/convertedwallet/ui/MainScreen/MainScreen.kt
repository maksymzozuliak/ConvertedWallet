package com.example.convertedwallet.ui.MainScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val list = viewModel.moneyList.value
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary,
                    snackbarData = data
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    MainScreenViewModel.MoneyEvent.AddMoney
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(bottom = 34.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add money",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                //todo buttons
            }
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 12.dp)
                    .fillMaxSize()
            ) {

                itemsIndexed(
                    items = list,
                    key = { index, item ->
                        item.hashCode()
                    }
                ) { index, money ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                viewModel.onEvent(MainScreenViewModel.MoneyEvent.DeleteMoney(money))
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Money deleted",
                                    )
                                }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        modifier = Modifier
                            .animateItemPlacement()
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> MaterialTheme.colors.background
                                    else -> Color.Red
                                }
                            )

                            val iconColor by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> MaterialTheme.colors.background
                                    else -> MaterialTheme.colors.onSurface
                                }
                            )

                            Box(
                                Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = iconColor
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                elevation = animateDpAsState(
                                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                                ).value
                            ) {
                                ExerciseItem(
                                    exercise = exercise,
                                    modifier = Modifier
                                        .padding()
                                        .background(MaterialTheme.colors.background)
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate(
                                                Screen.AddEditExerciseScreen.route +
                                                        "?exerciseId=${exercise.id}"
                                            )
                                        }
                                )
                            }
                        },
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(0.6f) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}