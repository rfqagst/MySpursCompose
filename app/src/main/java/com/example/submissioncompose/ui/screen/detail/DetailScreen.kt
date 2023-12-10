package com.example.submissioncompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.R
import com.example.submissioncompose.ViewModelFactory
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.ui.common.UiState


@Composable
fun DetailScreen(
    playerId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,

    ) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlayerById(playerId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.id,
                    data.photoId,
                    data.name,
                    data.desc,
                    onBackClick = navigateBack,
                    isFavorite = data.isFavorite,
                    onFavoriteClick = { id, state ->
                        viewModel.updateFavorite(id, state)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }

}

@Composable
fun DetailContent(
    playerId: Long,
    @DrawableRes image: Int,
    playerName: String,
    desc: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: (id: Long, state: Boolean) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.clickable { onBackClick() }
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 20.dp)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

            }

            IconButton(
                onClick = {
                    onFavoriteClick(playerId, isFavorite)
                },
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .size(width = 35.dp, height = 35.dp)

                )
            }

        }

        item {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = playerName,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Card(
                modifier = Modifier
                    .width(360.dp)
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.Center)
                    .padding(top = 30.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = desc,
                    fontSize = 21.sp,
                )
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
@Composable
fun PreviewDetailContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        DetailContent(
            1,
            R.drawable.lloris,
            "Lloris",
            "A quietly–spoken yet authoritative figure, Hugo Lloris is one of our longest–serving players. Following his arrival from Lyon in 2012, Hugo celebrated 10 years at the Club in August, 2022, having moved into our all–time top 10 appearance makers with his 416th in all competitions on the final day of 2021/22 – the day we clinched fourth place in the Premier League and UEFA Champions League qualification with a 5–0 win at Norwich City. The 2022/23 campaign was one of highs and lows for Hugo. In December, he led France to a second-successive World Cup Final, only to be denied history by becoming the first skipper to lift the World Cup trophy twice when Le Bleus were beaten by Argentina in a penalty shoot-out in a thrilling final in Qatar. He retired from international football after the tournament as France's most-capped player and captain with 145 caps.",
            onBackClick = {},
            isFavorite = false,
            onFavoriteClick = { _, _ -> }
        )
    }
}

