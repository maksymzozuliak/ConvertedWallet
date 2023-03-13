package com.example.convertedwallet.ui.main_screen.main_screen_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertedwallet.model.BaseCurrency
import com.example.convertedwallet.model.Money
import com.example.convertedwallet.model.toRate
import androidx.compose.ui.text.TextStyle

@Composable
fun MoneyItem (
    money: Money,
    modifier: Modifier,
    baseCurrency: BaseCurrency,
    currencyTextStyle: TextStyle = MaterialTheme.typography.h1,
    rateTextStyle: TextStyle = MaterialTheme.typography.subtitle1,
    moneyTextStyle: TextStyle = MaterialTheme.typography.body1,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(12.dp)
            ) {
                Text(
                    style = currencyTextStyle,
                    text = money.currency
                )

                Text(
                    text = String.format("%.2f", baseCurrency.toRate(money)),
                    style = rateTextStyle
                )
            }

            Text(
                text = money.inCurrency.toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                textAlign = TextAlign.End,
                style = moneyTextStyle
            )
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .padding(vertical = 10.dp),
                color = Color.Gray
            )

            Text(
                text = (money.inCurrency * baseCurrency.toRate(money)).toInt().toString(),
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                style = moneyTextStyle
            )
        }
    }
}

@Preview
@Composable
fun MoneyItemPreview() {
    MoneyItem(
        money = Money(
            currency = "MDL",
            inCurrency = 1000,
            rateToUSD = 0.2,
            rateToEUR = 0.3,
            rateToUAH = 2.0
        ),
        baseCurrency = BaseCurrency.UAH,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}