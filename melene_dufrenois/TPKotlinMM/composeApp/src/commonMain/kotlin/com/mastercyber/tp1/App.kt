package com.mastercyber.tp1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.ColorFilter.Companion.colorMatrix
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Modifier
import org.example.project.Pokemon
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent){
                val greeting = remember { Greeting().greet() }

                var isGood by remember { mutableStateOf<Boolean?>(null) }
                var input by remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }
                var imageUrl by remember { mutableStateOf<String?>(null) }
                var isGreyImage by remember { mutableStateOf(false) }
                var counter by remember { mutableStateOf(0) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(showContent) {
                    if (showContent) {
                        val pokemon = Greeting().fetchPokemon()
                        name = pokemon.name?.fr ?: "Unknown"
                        imageUrl = pokemon.sprites?.regular
                        isGreyImage = true
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    if (imageUrl != null) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(200.dp),
                            colorFilter = ColorFilter.colorMatrix(
                                ColorMatrix().apply { setToSaturation(if (isGreyImage) 0f else 1f)}
                            )
                        )
                    }

                }

                Text("Compose: $name")
                Text("$counter /5")
                TextField(value = input, onValueChange = {input = it})

                Button(onClick = {
                    scope.launch {
                        isGreyImage= false
                        delay(3_000L)
                        val correct = Greeting().guessPokemon(name, input)
                        isGood = correct
                        if (correct) counter += 1

                        val pokemon = Greeting().fetchPokemon()
                        name = pokemon.name?.fr ?: "Unknown"
                        imageUrl = pokemon.sprites?.regular
                        input = ""
                        isGreyImage = true
                    }
                }) {
                    Text("Devine")
                }
                if (isGood != null){
                    Text(if(isGood == true) "Bravo, c'est gagné :" else "Dommage, c'est perdu..")
                }
            }
        }
    }
}