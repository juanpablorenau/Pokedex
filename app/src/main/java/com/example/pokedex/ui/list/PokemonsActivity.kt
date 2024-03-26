package com.example.pokedex.ui.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.SuccessResult
import com.example.model.entities.Pokemon
import com.example.pokedex.R
import com.example.pokedex.theme.Black
import com.example.pokedex.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsActivity : AppCompatActivity() {

    private val viewModel: PokemonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PokedexTheme { PokemonListScreen() } }
    }

    @Preview(showBackground = true)
    @Composable
    private fun PokemonListScreen() {
        val uiState: PokemonsUiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (val state = uiState) {
            is PokemonsUiState.Loading -> LoadingScreen()
            is PokemonsUiState.Success -> SuccessScreen(state.pokemons)
            is PokemonsUiState.Error -> ErrorScreen(state.error)
        }
    }

    @Composable
    private fun LoadingScreen() {
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
            title = { Text("Ha ocurrido un error") },
            text = { Text(error) },
            confirmButton = {
                Text(
                    modifier = Modifier.clickable { viewModel.getPokemons() },
                    text = "Aceptar",
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
    fun SuccessScreen(pokemons: List<Pokemon> = listOf()) {
        val scrollState = rememberLazyListState()

        Scaffold(
            topBar = { SearchBar(scrollState, onSearch = { name -> viewModel.getPokemon(name) }) },
            content = { padding -> LazyColumn(pokemons, padding, scrollState) })
    }

    @Composable
    fun SearchBar(scrollState: LazyListState, onSearch: (String) -> Unit) {
        if (remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }.value <= 0) {
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
                label = { Text(text = "Search") },
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
                            viewModel.getPokemons()
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
            contentDescription = "Close",
            tint = Color.Gray,
            modifier = Modifier
                .padding(4.dp)
                .clickable(onClick = onClick)
        )
    }

    @Composable
    fun LazyColumn(
        pokemons: List<Pokemon>,
        padding: PaddingValues,
        scrollState: LazyListState,
    ) {
        val dominantColors = remember { mutableStateMapOf<String, Int>() }

        LazyColumn(
            modifier = Modifier.padding(padding),
            state = scrollState,
        ) {
            items(pokemons) { pokemon ->
                ItemPokemon(pokemon = pokemon,
                    imageColor = dominantColors.getOrDefault(
                        pokemon.name, Black.value.toInt()
                    ),
                    onSuccess = { result ->
                        dominantColors[pokemon.name] = viewModel.getDominantColor(result)
                    },
                    onError = { error -> viewModel.setErrorState(error) },
                    onPokemonClicked = {})
            }
        }
    }


    @Composable
    fun ItemPokemon(
        pokemon: Pokemon,
        imageColor: Int,
        onSuccess: (result: SuccessResult) -> Unit,
        onError: (error: String) -> Unit,
        onPokemonClicked: (name: String) -> Unit,
    ) {
        var visible by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0.25f,
            animationSpec = tween(durationMillis = 1000),
            label = "alphaAnim"
        )

        val scale by updateTransition(visible, label = "scaleTrans").animateFloat(
            transitionSpec = { tween(durationMillis = 500) }, label = "scaleAnim"
        ) { state -> if (state) 1f else 0.75f }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp)
                .alpha(alpha)
                .scale(scale)
                .clickable { onPokemonClicked(pokemon.name) },
            colors = CardDefaults.cardColors(containerColor = Color(imageColor)),
        ) {
            AsyncImage(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.75f)
                .padding(top = 16.dp),
                model = pokemon.url,
                contentDescription = "Pokemon image",
                onError = { error -> onError(error.result.toString()) },
                onSuccess = { result -> onSuccess(result.result) })
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
}
