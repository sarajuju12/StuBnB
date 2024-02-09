import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.HousingRepository   // import class not file?
import com.example.myapplication.components.HousingListComponent
import com.example.myapplication.models.Housing
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.R


@Composable
fun HousingList(housings: List<Housing>) {
    LazyColumn {
        items(housings.size) { index ->
            val housing = housings[index]
            HousingItem(housing = housing) //
        }
    }
}

@Composable
fun HousingItem(housing: Housing) { //?
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Image
        Image(
            painter = painterResource (id = R.drawable.anchor), // Placeholder image
            contentDescription = "Housing Picture",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Inventory Details
        Column {
            Text(text = housing.name, fontSize = 20.sp)
            Text(text = housing.description, fontSize = 14.sp)
        }
    }
}





