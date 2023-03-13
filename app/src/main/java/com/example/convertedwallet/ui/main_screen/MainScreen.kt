package com.example.convertedwallet.ui.main_screen

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.convertedwallet.model.Money
import com.example.convertedwallet.ui.main_screen.add_edit_dialog.AddEditDialog
import com.example.convertedwallet.ui.main_screen.main_screen_components.CurrencySlider
import com.example.convertedwallet.ui.main_screen.main_screen_components.MoneyItem
import com.example.convertedwallet.ui.main_screen.main_screen_components.UpdateButton

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val list = viewModel.moneyList.value
    val total = viewModel.total.value
    val currentCurrency = viewModel.currentCurrency.value
    val message = viewModel.showSnackbar.value
    val isLoading = viewModel.isLoading.value

    if (message.isNotEmpty()) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed) {
                viewModel.closeSnackbar()
            }
        }
    }

    val dialogState =  remember { mutableStateOf(DialogState(false,null)) }

    if(dialogState.value.showDialog)
        AddEditDialog(
            money = dialogState.value.money,
            setShowDialog = {
                dialogState.value = dialogState.value.copy(it,null)
            },
            modifier = Modifier.width(240.dp),
            onSavePressed = { currency, inCurrency, money ->
                viewModel.onEvent(MainScreenViewModel.MoneyEvent.SaveMoney(currency,inCurrency,money))
            },
            isLoading = isLoading,
            onError = { viewModel.onEvent(MainScreenViewModel.MoneyEvent.ShowMessage(it)) }
        )

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
                    dialogState.value = dialogState.value.copy(
                        true,
                        null
                    )
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .height(85.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CurrencySlider(
                    selectedCurrency = currentCurrency,
                    onChangeCurrency = {
                        viewModel.onEvent(
                            MainScreenViewModel.MoneyEvent.ChangeCurrency(it)
                        )
                    },
                    modifier = Modifier
                        .weight(3f)
                        .padding(10.dp)
                )
                UpdateButton(
                    onClick = {
                              viewModel.onEvent(MainScreenViewModel.MoneyEvent.UpdateRates)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(15.dp),
                    isLoading = isLoading
                )
            }

            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 60.dp
                    )
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            Text(
                text = total.toString()
            )

            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 12.dp)
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
                                    .padding(6.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(color)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.CenterStart,
                            ) {
                                Icon(
                                    modifier = Modifier.padding(start = 8.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = iconColor
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                shape = RoundedCornerShape(29.dp),
                                elevation = animateDpAsState(
                                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                                ).value,
                                modifier = Modifier
                                    .padding(
                                        vertical = 4.dp,
                                        horizontal = 8.dp)
                            ) {
                                MoneyItem(
                                    money = money,
                                    baseCurrency = currentCurrency,
                                    modifier = Modifier
                                        .background(MaterialTheme.colors.background)
                                        .fillMaxWidth()
                                        .clickable {
                                            dialogState.value = dialogState.value.copy(
                                                true,
                                                money
                                            )
                                        }
                                        .padding(
                                            vertical = 2.dp,
                                            horizontal = 4.dp
                                        )
                                )
                            }
                        },
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(0.5f) }
                    )
                }
            }
        }
    }
}

data class DialogState(
    var showDialog: Boolean,
    var money: Money?,
)
