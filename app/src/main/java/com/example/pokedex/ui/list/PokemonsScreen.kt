package com.example.pokedex.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.model.entities.Pokemon
import com.example.pokedex.R
import com.example.pokedex.navigation.AppScreens.PokemonInfoScreen
import com.example.pokedex.theme.Black
import com.example.pokedex.utils.getDominantColor
import com.example.pokedex.utils.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PokemonsScreen(navController: NavHostController) {
    val viewModel = LocalContext.current.getViewModel<PokemonsViewModel>()
    val uiState: PokemonsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is PokemonsUiState.Loading -> LoadingScreen()
        is PokemonsUiState.Error -> ErrorScreen(state.error) { viewModel.getPokemons() }
        is PokemonsUiState.Success -> {
            SuccessScreen(
                navController = navController,
                pokemons = state.pokemons,
                getPokemons = { viewModel.getPokemons() },
                getPokemon = { name -> viewModel.getPokemon(name) },
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
private fun ErrorScreen(error: String = "", getPokemons: () -> Unit) {
    AlertDialog(onDismissRequest = { },
        title = { Text(stringResource(R.string.generic_error_msg)) },
        text = { Text(error) },
        confirmButton = {
            Text(
                modifier = Modifier.clickable { getPokemons() },
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
    pokemons: List<Pokemon>,
    getPokemons: () -> Unit,
    getPokemon: (name: String) -> Unit,
    setErrorState: (error: String) -> Unit,
) {
    val scrollState = rememberLazyListState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color(Color.Transparent.toArgb()))

    Scaffold(
        topBar = {
            SearchBar(
                scrollState = scrollState,
                onSearch = { name -> getPokemon(name) },
                getPokemons = { getPokemons() }
            )
        },
        content = { padding ->
            LazyColumn(
                navController = navController,
                pokemons = pokemons,
                padding = padding,
                scrollState = scrollState,
                setErrorState = setErrorState
            )
        }
    )
}

@Composable
fun SearchBar(
    scrollState: LazyListState,
    onSearch: (String) -> Unit,
    getPokemons: () -> Unit,
) {
    val isSearchBarVisible by remember { derivedStateOf { !scrollState.isScrollInProgress } }

    AnimatedVisibility(
        visible = isSearchBarVisible,
        enter = slideInVertically(initialOffsetY = { -400 }),
        exit = slideOutVertically(targetOffsetY = { -40 })
    ) {
        var searchText by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            ),
            value = searchText,
            onValueChange = { newText -> searchText = newText },
            label = { Text(text = stringResource(R.string.search)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onSearch(searchText.lowercase())
            }),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    CloseIcon {
                        searchText = ""
                        getPokemons()
                    }
                }
            }
        )
    }
}

@Composable
fun CloseIcon(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = stringResource(R.string.close),
        tint = Color.Gray,
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
fun LazyColumn(
    navController: NavHostController,
    pokemons: List<Pokemon>,
    padding: PaddingValues,
    scrollState: LazyListState,
    setErrorState: (error: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(padding),
        state = scrollState,
    ) {
        items(pokemons) { pokemon ->
            ItemPokemon(
                navController = navController,
                pokemon = pokemon,
                onError = { error -> setErrorState(error) }
            )
        }
    }
}

@Composable
fun ItemPokemon(
    navController: NavHostController,
    pokemon: Pokemon,
    onError: (error: String) -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    var dominantColor by remember { mutableIntStateOf(Black.value.toInt()) }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0.25f,
        animationSpec = tween(durationMillis = 1000),
        label = "alphaAnim"
    )

    val scale by updateTransition(visible, label = "scaleTrans").animateFloat(
        transitionSpec = { tween(durationMillis = 500) }, label = "scaleAnim"
    ) { state -> if (state) 1f else 0.75f }

    val route = PokemonInfoScreen.route.plus("/" + pokemon.name)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .alpha(alpha)
            .scale(scale)
            .clickable { navController.navigate(route) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(dominantColor)),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.75f)
                .padding(top = 16.dp),
            model = pokemon.url,
            contentDescription = "Pokemon image",
            onError = { error -> onError(error.result.toString()) },
            onSuccess = { result -> dominantColor = getDominantColor(result.result) }
        )
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = pokemon.name,
            fontSize = 36.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }

    LaunchedEffect(key1 = true) {
        visible = true
    }
}