package com.ashok.myapplication.ui.navigation

import android.health.connect.datatypes.ExerciseRoute
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
fun navigationDrawer(
    route: String,
    modifier: Modifier= Modifier,

) {

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {

            }
        }
    ) {

    }
}