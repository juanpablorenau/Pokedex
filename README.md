# <span style="color:blue">Pokedex: Discover all the Pokemon</span>

## Description

**Pokedex** is an Android application developed in Kotlin that consumes the PokeAPI to display a list of pokemon and their details from the TV series.

## Features

The application has been built following these principles and patterns:

- **Compose**: The application is designed with compose.
- **Clean Architecture**: The application follows the Clean Architecture principle to separate responsibilities into different layers, which facilitates code maintenance and scalability.
- **Repository Pattern**: The Repository Pattern is used to abstract the data access logic, allowing data sources to be changed without affecting the rest of the application.
- **MVVM**: The Model-View-ViewModel (MVVM) pattern is used to separate the user interface logic from the business logic.
- **Hilt Dependency injection**: Allows a class to receive its dependencies from an external source, rather than creating them internally. 
- **Flows**: Flows are used to handle user interface events asynchronously.
- **Coroutines**: Coroutines are used to handle asynchronous operations and improve the efficiency of the application.
- **Room**: Room is used for data persistence in the application.
- **Retrofit**: Retrofit is used for calls to the Rick and Morty API.
- **Animations**: They create a more polished and engaging interface, making interactions intuitive and enjoyable for users
