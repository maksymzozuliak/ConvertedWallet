package com.example.convertedwallet.ui.main_screen.add_edit_dialog

import android.app.Dialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.convertedwallet.model.Money

@Composable
fun AddEditDialog(
    money: Money?,
    modifier : Modifier = Modifier,
    onSavePressed : (String, Int, Money?) -> Unit,
    setShowDialog: (Boolean) -> Unit,
    backgroundColor: Color = Color.White
) {

    val currencyNameText = remember { mutableStateOf(money?.currency ?: "") }
    val inCurrencyText = remember { mutableStateOf(money?.inCurrency?.toString() ?: "") }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(28.dp),
            color = backgroundColor
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    OutlinedTextField(
                        value = currencyNameText.value,
                        onValueChange = { newText ->
                            if (newText.length <= 3) {
                                currencyNameText.value = newText.uppercase()
                            }
                        },
                        label = {
                            Text("Currency")
                        },
                        placeholder = {
                            Text(text = "Enter currency")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            autoCorrect = false,
                            capitalization = KeyboardCapitalization.Characters,
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 4.dp,
                                bottom = 4.dp
                            ),
                    )

                    OutlinedTextField(
                        value = inCurrencyText.value,
                        onValueChange = { inCurrencyText.value = it },
                        label = {
                            Text("Amount")
                        },
                        placeholder = {
                            Text(text = "Enter amount")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.NumberPassword,
                            autoCorrect = false
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 12.dp
                            ),
                    )

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 14.dp,
                                end = 14.dp,
                                top = 6.dp,
                                bottom = 6.dp
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { setShowDialog(false) },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(
                            onClick = {
                                onSavePressed(
                                    currencyNameText.value,
                                    inCurrencyText.value.toInt(),
                                    money
                                )
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 0.dp,
                                disabledElevation = 10.dp
                            ),
                        ) {
                            Text(
                                text = "Done"
                            )
                        }
                    }
                }
            }
        }
    }
}
