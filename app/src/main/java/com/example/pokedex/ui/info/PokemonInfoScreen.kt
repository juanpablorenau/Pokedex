package com.example.pokedex.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.SuccessResult
import com.example.model.entities.PokemonInfo
import com.example.model.entities.getFormattedId
import com.example.pokedex.R
import com.example.pokedex.theme.Black
import com.example.pokedex.ui.info.tabs.about.AboutTab
import com.example.pokedex.ui.info.tabs.moves.MovesTab
import com.example.pokedex.ui.info.tabs.stats.StatsTab
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
                setErrorState = { error -> viewModel.setErrorState(error) })
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
        topBar = { TopBar(navController, dominantColor, pokemonInfo) },
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
fun TopBar(
    navController: NavHostController,
    dominantColor: MutableIntState,
    pokemonInfo: PokemonInfo,
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = Color(dominantColor.intValue)
    ), navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "backIcon"
            )
        }
    }, title = {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                color = Color.Black,
                text = stringResource(R.string.app_name)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                textAlign = TextAlign.End,
                color = Color.White,
                text = pokemonInfo.getFormattedId()
            )
        }
    })
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
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding()
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(dominantColor), Color.White)
                    )
                )
        ) {
            AsyncImage(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.75f),
                model = pokemonInfo.url,
                contentDescription = "Pokemon image",
                onError = { error -> onError(error.result.toString()) },
                onSuccess = { result -> onSuccess(result.result) })

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
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) { pokemonInfo.types.forEach { type -> Chip(type.name, type.color) } }
        }
        TabLayout(pokemonInfo, dominantColor)
    }
}

@Composable
private fun Chip(type: String, color: Long) {
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

@Composable
fun TabLayout(pokemonInfo: PokemonInfo, dominantColor: Int) {
    val titles = listOf("About", "Stats", "Moves", "Evolutions")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TabRow(
                contentColor = Color.White,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 2.dp,
                        color = Color.Gray
                    )
                },
                selectedTabIndex = selectedTabIndex
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color = Color(dominantColor)
                            )
                        },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
        },
        content = { paddingValues ->
            when (selectedTabIndex) {
                0 -> AboutTab(paddingValues, pokemonInfo, dominantColor)
                1 -> StatsTab(paddingValues, pokemonInfo, dominantColor)
                2 -> MovesTab(paddingValues, pokemonInfo, dominantColor)
                3 -> {}
                else -> throw IllegalArgumentException("Tab desconocido.")
            }
        }
    )
}
