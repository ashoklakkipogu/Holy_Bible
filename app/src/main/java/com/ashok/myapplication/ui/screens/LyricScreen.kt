package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.theme.BibleTheme
import okhttp3.internal.wait


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricScreen(navController: NavController) {

    BibleTheme {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                SearchAppBar()
                lyricCard()
                lyricCard()
            }


        }
    }
}

@Composable
fun lyricCard() {
    Column {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = { Text(text = "You added a Note ") },
            supportingContent = { Text(text = "2 weeks ago") },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "AL",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
        Divider(
            modifier = Modifier
                .height(1.dp)
                .padding(start = 10.dp, end = 10.dp),
            color = Color.LightGray
        )
    }

}


@Composable
private fun SearchAppBar(
) {
    // Immediately update and keep track of query from text field changes.
    var query: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            // If user makes changes to text, immediately updated it.
            query = onQueryChanged
            // To avoid crash, only query when string isn't empty.
            if (onQueryChanged.isNotEmpty()) {
                // Pass latest query to refresh search results.
                //viewModel.performQuery(onQueryChanged)
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { query = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        maxLines = 1,
        placeholder = { Text(text = "Search Lyric") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background, shape = RectangleShape)
    )
}


@Preview
@Composable
fun LyricScreenPreview() {
    LyricScreen(rememberNavController())
}