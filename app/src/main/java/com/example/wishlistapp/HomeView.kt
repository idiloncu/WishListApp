package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.FractionalThreshold
import com.example.wishlistapp.data.Wish

@ExperimentalMaterial3Api
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppBarView(title = "WishList") {
                Toast.makeText(context, "Back Button Clicked", Toast.LENGTH_LONG).show()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    Toast.makeText(context, "FAB Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(wishList.value, key = {wish->wish.id}) { wish ->

                val swipeDismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.StartToEnd || it == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteWish(wish)
                            true
                        }else false
                    }
                )
                SwipeToDismissBox(
                    state = swipeDismissState,
                    backgroundContent ={
                        val color by animateColorAsState(
                            if (swipeDismissState.dismissDirection==DismissDirection.StartToEnd) Color.Red
                            else Color.Transparent,
                                    label = ""
                        )
                    },
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    dismissContent={
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}

