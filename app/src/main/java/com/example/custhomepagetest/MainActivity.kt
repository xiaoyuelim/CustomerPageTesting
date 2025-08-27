package com.example.custhomepagetest

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.espresso.base.Default
import com.example.custhomepagetest.ui.theme.CustHomePageTestTheme

data class Menu(
    val name: String,
    val price: Double,
    val imageRes: Int,
    val category: List<String>
)

data class Category(
    val name: String,
    val imageRes: Int
)

val menuItem = listOf(
    Menu("Fried Chicken Spaghetti", 8.00, R.drawable.fried_chicken_spaghetti, listOf("Chicken", "Spaghetti")),
    Menu("Fried Fish Rice", 8.00, R.drawable.fried_fish_rice, listOf("Fish", "Rice")),
    Menu("Fried FIsh Spaghetti", 8.00, R.drawable.fried_fish_spaghetti, listOf("Fish", "Spaghetti")),
    Menu("Grilled Chicken Rice", 10.00, R.drawable.gilled_chicken_rice, listOf("Chicken", "Rice")),
    Menu("Grilled Chicken Spaghetti", 10.00, R.drawable.roasted_chicken_spaghetti, listOf("Chicken", "Spaghetti")),
    Menu("Soy Milk", 3.50, R.drawable.soya, listOf("Drinks")),
    Menu("100 Plus", 3.50, R.drawable._00_plus,listOf("Drinks")),
    Menu("Coca Cola", 3.50, R.drawable.coca_cola, listOf("Drinks"))
)

val categories = listOf(
    Category("All", R.drawable.all),
    Category("Rice", R.drawable.rice_icon),
    Category("Spaghetti", R.drawable.spaghetti_icon),
    Category("Chicken", R.drawable.chicken_icon),
    Category("Fish", R.drawable.fish_icon),
    Category("Drinks", R.drawable.drink_icon)
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustHomePageTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(points = 0, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(points: Int, modifier: Modifier = Modifier) {
    Box (Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxSize()) {
            var selectedCategory by remember { mutableStateOf("All") }

            //side navigation
            SideNavigation(
                categories = categories,
                selected = selectedCategory,
                onCategorySelected = { selectedCategory = it })

            Column(Modifier.fillMaxSize()) {
                Points(points = points, onClick = {/* TODO */ })

                val filteredItems = if (selectedCategory == "All") {
                    menuItem
                } else {
                    menuItem.filter { it.category.contains(selectedCategory) }
                }
                LazyColumn(Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 85.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredItems) { item ->
                        MenuCard(item)
                    }
                }
            }
        }
        BottomNavigation(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun SideNavigation(categories: List<Category>, selected: String, onCategorySelected: (String) -> Unit) {
    Column(
        Modifier
            .width(80.dp)
            .fillMaxHeight()
            .background(Color(0xFFEFEFEF))
            .padding(top = 32.dp, bottom = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        categories.forEach { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onCategorySelected(category.name) }
                    .width(70.dp)
            ) {
                Box (
                    modifier = Modifier
                        .clickable { onCategorySelected(category.name) }
                        .background(
                            color = if (category.name == selected) Color.White else Color.Transparent,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                        .size(90.dp),
                    contentAlignment = Alignment.Center
                ){
                    Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(category.imageRes),
                            contentDescription = category.name,
                            Modifier.size(40.dp),
                            tint = if (category.name == selected) Color.Black else Color.Unspecified
                        )
                        Text(
                            text = category.name,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Points(points: Int, onClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 23.dp, end = 8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
        ){
            Text("Points: $points", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun MenuCard(item: Menu){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.size(120.dp)
            )
            Text(item.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
            Text("RM %.2f".format(item.price))
        }
    }
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar (modifier = modifier.fillMaxWidth()) {
        NavigationBarItem(
            selected = true,
            onClick = {/* TODO */},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home"
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {/* TODO */},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {/* TODO */},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustHomePageTestTheme {
        HomeScreen(0)
    }
}