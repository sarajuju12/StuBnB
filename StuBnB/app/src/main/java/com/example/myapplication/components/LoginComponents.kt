package com.example.myapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80

@Composable
fun TitleText(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 70.dp),
        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(labelValue: String, painterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean = false) {
    val textValue = remember { mutableStateOf("") }
    OutlinedTextField(
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple40,
            focusedLabelColor = Purple40,
            cursorColor = Purple40
        ),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "", modifier = Modifier.size(20.dp))
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean = false) {
    val textValue = remember { mutableStateOf("") }
    val visible = remember { mutableStateOf(false) }
    val focus = LocalFocusManager.current
    OutlinedTextField(
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple40,
            focusedLabelColor = Purple40,
            cursorColor = Purple40
        ),
        value = textValue.value,
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions {
            focus.clearFocus()
        },
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "", modifier = Modifier.size(20.dp))
        },
        trailingIcon = {
            val icon = if (visible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (visible.value) {
                "Hide password"
            } else {
                "Show password"
            }
            IconButton(onClick = { visible.value = !visible.value }) {
                Icon(imageVector = icon, contentDescription = description)
            }
        },
        visualTransformation = if (visible.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = !errorStatus
    )
}

@Composable
fun ActionButton(value: String, buttonClicked : () -> Unit, isEnabled: Boolean = true) {
    Button(
        onClick = {
              buttonClicked.invoke()
        },
        modifier = Modifier.fillMaxWidth().heightIn(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
    ) {
        Box(modifier = Modifier.fillMaxWidth().heightIn(50.dp).background(
                color = Purple40,
                shape = RoundedCornerShape(52.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoginRedirect(login: Boolean = true, onTextSelected: (String) -> Unit) {
    val normalText = if (login) "Already have an account? " else "Don't have an account? "
    val redirectText = if (login) "Sign in" else "Sign up"
    val annotatedString = buildAnnotatedString {
        append(normalText)
        withStyle(style = SpanStyle(color = Purple80)) {
            pushStringAnnotation(tag = redirectText, annotation = redirectText)
            append(redirectText)
        }
    }
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if (span.item == redirectText) {
                        onTextSelected(span.item)
                    }
                }
        },
        modifier = Modifier.fillMaxWidth().heightIn(50.dp),
        style = TextStyle(
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    )
}