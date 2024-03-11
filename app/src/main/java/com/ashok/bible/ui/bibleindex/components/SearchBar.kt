package com.ashok.bible.ui.bibleindex.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.bible.ui.theme.BibleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var search by remember {
        mutableStateOf("")
    }

    Box(
        modifier = modifier
            .padding(start = 6.dp, end = 6.dp)

    ) {
        OutlinedTextField(value = search,
            onValueChange = {
                search = it
                onValueChange.invoke(it)
                            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            trailingIcon = { Icon(imageVector = Icons.Default.Clear, contentDescription = "") },
            placeholder = { Text(text = "Search") }
        )
    }

}


@Preview
@Composable
fun SearchBarPreview() {
    BibleTheme {
        SearchBar(onValueChange = {

        })
    }
}
