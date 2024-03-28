package com.example.pokedex.ui.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.SuccessResult
import com.example.model.entities.PokemonInfo
import com.example.pokedex.R
import com.example.pokedex.theme.Black
import com.example.pokedex.utils.getDominantColor
import com.example.pokedex.utils.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PokemonInfoScreen(navController: NavHostController, name: String) {
    val viewModel = LocalContext.current.getViewModel<PokemonInfoViewModel>()
    val uiState: PokemonInfoUiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.getPokemonInfo(name)

    when (val state = uiState) {
        is PokemonInfoUiState.Loading -> LoadingScreen()
        is PokemonInfoUiState.Error -> ErrorScreen(state.error)
        is PokemonInfoUiState.Success -> {
            SuccessScreen(
                navController = navController,
                pokemonInfo = state.pokemonInfo,
                setErrorState = { error -> viewModel.setErrorState(error) }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun ErrorScreen(error: String = "") {
    AlertDialog(onDismissRequest = { },
        title = { Text(stringResource(R.string.generic_error_msg)) },
        text = { Text(error) },
        confirmButton = {
            Text(
                modifier = Modifier.clickable { },
                text = stringResource(R.string.accept),
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "Error Icon"
            )
        })
}

@Composable
fun SuccessScreen(
    navController: NavHostController,
    pokemonInfo: PokemonInfo,
    setErrorState: (error: String) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val dominantColor = remember { mutableIntStateOf(Black.value.toInt()) }

    systemUiController.setSystemBarsColor(color = Color(dominantColor.intValue))

    Scaffold(
        topBar = { TopBar(navController, dominantColor) },
        content = { padding ->
            Content(
                padding = padding,
                pokemonInfo = pokemonInfo,
                dominantColor = dominantColor.intValue,
                onSuccess = { result -> dominantColor.intValue = getDominantColor(result) },
                onError = { error -> setErrorState(error) },
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, dominantColor: MutableIntState) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = Color(dominantColor.intValue)
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "backIcon"
                )
            }
        },
        title = {
            Text(
                color = Color.Black,
                text = stringResource(R.string.app_name)
            )
        }
    )
}

@Composable
fun Content(
    padding: PaddingValues,
    pokemonInfo: PokemonInfo,
    dominantColor: Int,
    onSuccess: (result: SuccessResult) -> Unit,
    onError: (error: String) -> Unit,
) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp, topEnd = 0.dp, bottomStart = 48.dp, bottomEnd = 48.dp
                    )
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(dominantColor)),
            shape = RectangleShape,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.75f),
                model = pokemonInfo.url,
                contentDescription = "Pokemon image",
                onError = { error -> onError(error.result.toString()) },
                onSuccess = { result -> onSuccess(result.result) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = pokemonInfo.name,
                color = Color(dominantColor),
                fontSize = 36.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Chip("Veneno", dominantColor)
                Chip("Planta", dominantColor)
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun Content() {

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp, topEnd = 0.dp, bottomStart = 48.dp, bottomEnd = 48.dp
                    )
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(Black.value)),
            shape = RectangleShape,
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.75f),
                painter = painterResource(id = R.drawable.ic_pokemons),
                contentDescription = "Pokemon image",

                )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Bulbasaur",
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Chip("Veneno", Red.toArgb())
                Chip("Planta", Red.toArgb())
            }

        }
    }
}*/

@Composable
private fun Chip(type: String, color: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color(color)),
    ) {
        Text(
            text = type.uppercase(),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}