package com.example.submissioncompose.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.submissioncompose.R


@Composable
fun ProfileScreen(navigateBack: () -> Unit) {
    ProfileContent(
        onBackClick = navigateBack,
    )
}


@Composable
fun ProfileContent(
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() .padding(7.dp, 7.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier.clickable { onBackClick() }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 20.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.rifqi),
                    contentDescription = null,
                )
            }

            Text(
                text = stringResource(R.string.muhammad_rifqi_agustia),
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp

            )
            Text(
                text = stringResource(R.string.agustiarifqi_gmail_com),
                modifier = Modifier.padding(top = 1.dp),
                fontStyle = FontStyle.Italic
            )
        }

    }

}

@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
@Composable
fun PreviewDetailContent(
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ProfileContent(onBackClick = {})
    }
}
