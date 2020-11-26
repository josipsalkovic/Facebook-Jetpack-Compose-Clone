package hr.josip.composeapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import hr.josip.composeapp.R
import hr.josip.composeapp.ui.shared.compose.darkGrey
import hr.josip.composeapp.ui.shared.compose.lightGrey

@Composable
fun Status(onStatusClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.surface) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.preferredSize(56.dp).then(Modifier.padding(8.dp)),
                shape = CircleShape,
                elevation = 0.dp
            ) {
                Image(
                    imageResource(id = R.drawable.avatar),
                    contentScale = ContentScale.Crop
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth().then(
                    Modifier.padding(horizontal = 8.dp, vertical = 8.dp).then(
                        Modifier.clickable(
                            onClick = onStatusClick
                        )
                    )
                ),
                backgroundColor = MaterialTheme.colors.secondary,
                elevation = 0.dp
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(id = R.string.status_hint),
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}