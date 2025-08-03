package com.example.composeselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSuggestionTextField(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    placeholder: String,
    cornerRadius: CornerBasedShape = MaterialTheme.shapes.extraSmall,
    focusedBorderColor: Color? = null,
    unfocusedBorderColor: Color? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,     // custom text color
    highlightColor: Color = MaterialTheme.colorScheme.primary   // custom highlight color
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val textFieldSize = remember { mutableStateOf(IntSize.Zero) }
    val textFieldHeight = remember { mutableStateOf(0f) }
    val textFieldCoordinates = remember { mutableStateOf(Offset.Zero) }
    val density = LocalDensity.current
    val view = LocalView.current
    val windowSize = remember { IntSize(view.width, view.height) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    expanded = true
                },
                shape = cornerRadius,
                placeholder = { Text(placeholder, color = textColor.copy(alpha = 0.6f)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = focusedBorderColor ?: MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = unfocusedBorderColor ?: MaterialTheme.colorScheme.outline,
                    cursorColor = textColor
                )
                ,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        val pos = coordinates.positionInRoot()
                        val size = coordinates.size
                        textFieldCoordinates.value = pos
                        textFieldHeight.value = size.height.toFloat()
                        textFieldSize.value = coordinates.size
                    },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable {
                                query = ""
                                expanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
                },
                singleLine = true
            )
        }

        val filteredItems = items.filter { it.contains(query, ignoreCase = true) }

        if (expanded && query.isNotEmpty() && filteredItems.isNotEmpty()) {
            Popup(
                alignment = Alignment.TopStart,
                offset = with(density) { IntOffset(0, textFieldHeight.value.toInt()) },
                properties = PopupProperties(focusable = false)
            ) {
                Surface(
                    modifier = Modifier
                        .width(with(density) { textFieldSize.value.width.toDp() })
                        .fillMaxWidth()
                        .heightIn(max = 200.dp),
                    shape = cornerRadius,
                    tonalElevation = 8.dp
                ) {
                    LazyColumn {
                        items(filteredItems.size) { pos ->
                            DropdownMenuItem(
                                text = {
                                    val item = filteredItems[pos]
                                    val start = item.indexOf(query, ignoreCase = true)
                                    val annotated = buildAnnotatedString {
                                        if (start != -1) {
                                            append(item.substring(0, start))
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    color = highlightColor
                                                )
                                            ) {
                                                append(item.substring(start, start + query.length))
                                            }
                                            append(item.substring(start + query.length))
                                        } else {
                                            append(item)
                                        }
                                    }
                                    Text(
                                        text = annotated,
                                        color = textColor,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                },
                                onClick = {
                                    onItemSelected(filteredItems[pos])
                                    query = filteredItems[pos]
                                    expanded = false
                                    focusManager.clearFocus()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}