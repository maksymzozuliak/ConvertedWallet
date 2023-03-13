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

data class CurrencyState(
    val color: Color = Color.White,
    val elevation: Dp = 35.dp,
    val iconColor: Color = Color.Black
)


@Composable
fun CurrencySlider(
    modifier: Modifier = Modifier,
    buttonColor: Color = MaterialTheme.colors.primaryVariant,
    pressedButtonColor : Color = MaterialTheme.colors.secondaryVariant,
    iconColor: Color =  Color.Black,
    pressedIconColor : Color = Color.DarkGray,
    selectedCurrency: BaseCurrency,
    onChangeCurrency: (BaseCurrency) -> Unit
) {

    val currencyState = remember { mutableStateOf(selectedCurrency)}

    val EURstate = remember { mutableStateOf(CurrencyState()) }
    val UAHstate = remember { mutableStateOf(CurrencyState()) }
    val USDstate = remember { mutableStateOf(CurrencyState()) }

    val defaultButtonState = CurrencyState(
        color = buttonColor,
        elevation = 35.dp,
        iconColor = iconColor
    )

    val pressedButtonState = CurrencyState(
        color = pressedButtonColor,
        elevation = 0.dp,
        iconColor = pressedIconColor
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
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
        ) {

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.EUR
                    onChangeCurrency(BaseCurrency.EUR)
                },
                modifier = Modifier
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

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.UAH
                    onChangeCurrency(BaseCurrency.UAH)
                },
                modifier = Modifier
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

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    currencyState.value = BaseCurrency.USD
                    onChangeCurrency(BaseCurrency.USD)
                },
                modifier = Modifier
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
