package com.example.convertedwallet.ui.main_screen.main_screen_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.convertedwallet.R
import com.example.convertedwallet.model.BaseCurrency
import com.example.convertedwallet.ui.theme.darkGray

data class CurrencyState(
    val color: Color = Color.White,
    val elevation: Dp = 35.dp,
    val iconColor: Color = Color.Black
)


@Composable
fun CurrencySlider(
    modifier: Modifier = Modifier,
    selectedCurrency: BaseCurrency,
    onChangeCurrency: (BaseCurrency) -> Unit
) {

    val currencyState = remember { mutableStateOf(selectedCurrency)}

    val EURstate = remember { mutableStateOf(CurrencyState()) }
    val UAHstate = remember { mutableStateOf(CurrencyState()) }
    val USDstate = remember { mutableStateOf(CurrencyState()) }

    val pressedButtonState = CurrencyState(
        color = Color.LightGray,
        elevation = 0.dp,
        iconColor = darkGray
    )

    val defaultButtonState = CurrencyState(
        color = Color.White,
        elevation = 35.dp,
        iconColor = Color.Black
    )

    if (currencyState.value == BaseCurrency.EUR) {
        EURstate.value = pressedButtonState.copy()
    } else {
        EURstate.value = defaultButtonState.copy()
    }

    if (currencyState.value == BaseCurrency.UAH) {
        UAHstate.value = pressedButtonState.copy()
    } else {
        UAHstate.value = defaultButtonState.copy()
    }

    if (currencyState.value == BaseCurrency.USD) {
        USDstate.value = pressedButtonState.copy()
    } else {
        USDstate.value = defaultButtonState.copy()
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        elevation = 20.dp
    ) {
        Row(
        ) {

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.EUR
                },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = EURstate.value.elevation,
                    pressedElevation = 0.dp,
                    disabledElevation = 20.dp
                ),
                colors = ButtonDefaults.buttonColors(EURstate.value.color)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.eur_icon),
                    contentDescription = "EUR",
                    tint = EURstate.value.iconColor
                )
            }

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.UAH
                },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = UAHstate.value.elevation,
                    pressedElevation = 0.dp,
                    disabledElevation = 20.dp
                ),
                colors = ButtonDefaults.buttonColors(UAHstate.value.color)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.uah_icon),
                    contentDescription = "UAH",
                    tint = UAHstate.value.iconColor
                )
            }

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.USD
                },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = USDstate.value.elevation,
                    pressedElevation = 0.dp,
                    disabledElevation = 20.dp
                ),
                colors = ButtonDefaults.buttonColors(USDstate.value.color)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.usd_icon),
                    contentDescription = "USD",
                    tint = USDstate.value.iconColor
                )
            }
        }
    }
}

@Preview
@Composable
fun MyComposablePreview() {
    CurrencySlider(
        selectedCurrency = BaseCurrency.EUR,
        onChangeCurrency = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}
