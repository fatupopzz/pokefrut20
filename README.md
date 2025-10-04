# PokeFrut 2.0

Una aplicación Android moderna desarrollada en Kotlin que consume la PokéAPI siguiendo principios de Clean Architecture y Clean Code.

## Arquitectura MVVM + Clean Architecture

### Estructura del Proyecto

```
app/src/main/java/com/example/pokefrut20/
├── ui/
│   ├── MainActivity.kt
│   ├── viewmodel/
│   │   ├── PokemonListViewModel.kt
│   │   └── PokemonDetailViewModel.kt
│   ├── components/
│   │   ├── PokemonListScreen.kt
│   │   ├── PokemonDetailScreen.kt
│   │   ├── CommonComponents.kt
│   │   └── FrutigerAeroBackground.kt
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
├── data/
│   ├── repository/
│   │   └── PokemonRepository.kt
│   ├── remote/
│   │   ├── PokemonApiService.kt
│   │   └── RetrofitClient.kt
│   └── model/
│       └── PokemonModels.kt
│
└── navigation/
    └── NavigationRoutes.kt
```

### Capas de la Arquitectura

#### UI Layer
- **Composables**: Pantallas y componentes de UI construidos con Jetpack Compose
- **ViewModels**: Manejo de estado y lógica de presentación
- **StateFlow**: Exposición de estados inmutables a la UI
- **Theme**: Configuración de Material Design 3 con paleta Frutiger Aero

#### Data Layer
- **Repository**: Intermediario entre ViewModels y fuentes de datos (Single Source of Truth)
- **RetrofitClient**: Configuración centralizada del cliente HTTP
- **ApiService**: Definición de endpoints de la PokéAPI
- **Models**: Clases de datos inmutables

### Flujo de Datos (Unidirectional Data Flow)

```
┌─────────────────────────────────────────┐
│            UI Layer                     │
│  ┌─────────────┐     ┌──────────────┐  │
│  │  Composable │ ←── │  ViewModel   │  │
│  │   Screens   │     │  (StateFlow) │  │
│  └─────────────┘     └──────────────┘  │
└──────────────────────────│──────────────┘
                           │
                           ↓
┌──────────────────────────│──────────────┐
│         Data Layer       │              │
│  ┌──────────────┐    ┌──────────────┐  │
│  │  Repository  │ ←→ │ ApiService   │  │
│  │              │    │ (Retrofit)   │  │
│  └──────────────┘    └──────────────┘  │
└─────────────────────────────────────────┘
                           │
                           ↓
                   ┌──────────────┐
                   │   PokéAPI    │
                   │  pokeapi.co  │
                   └──────────────┘
```

### Estados con Sealed Class

La aplicación utiliza `sealed class` para representar estados de forma exhaustiva y type-safe:

**PokemonListUiState:**
- `Loading`: Cargando datos desde la API
- `Success(pokemonList)`: Datos cargados exitosamente
- `Error(message)`: Error al cargar datos con mensaje específico
- `Empty`: No hay datos disponibles

**PokemonDetailUiState:**
- `Loading`: Cargando detalles del Pokémon
- `Success(pokemonDetail)`: Detalles cargados correctamente
- `Error(message)`: Error al cargar detalles

### Ventajas de esta Arquitectura

1. **Testeable**: Cada capa puede probarse independientemente sin dependencias
2. **Mantenible**: Cambios en una capa no afectan a las demás (bajo acoplamiento)
3. **Escalable**: Fácil agregar nuevas funcionalidades sin romper código existente
4. **Type-Safe**: Sealed classes garantizan manejo exhaustivo de estados
5. **Clara**: Separación de responsabilidades bien definida (Single Responsibility)
6. **Resiliente**: Manejo robusto de errores en toda la aplicación

## Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Kotlin | 1.9.0 | Lenguaje principal |
| Jetpack Compose | 1.5.8 | UI moderna y declarativa |
| Navigation Compose | 2.8.3 | Navegación entre pantallas |
| Retrofit | 2.9.0 | Cliente HTTP |
| Gson | 2.9.0 | Serialización JSON |
| Coil | 2.5.0 | Carga de imágenes asíncrona |
| Coroutines | 1.7.3 | Programación asíncrona |
| StateFlow | - | Manejo de estado reactivo |
| OkHttp | 4.12.0 | Logging de peticiones HTTP |

## Funcionalidades

### Pantalla Principal (Lista de Pokémon)
- Lista scrolleable de 100 Pokémon desde PokéAPI
- Navegación a pantalla de detalles al hacer click
- Estados de carga, error y vacío
- Imágenes optimizadas con Coil
- Botón de reintentar en caso de error

### Pantalla de Detalles
- Información completa del Pokémon seleccionado
- 4 sprites diferentes:
  - Front normal
  - Back normal
  - Front shiny
  - Back shiny
- Datos básicos: altura y peso
- Navegación hacia atrás con botón de retroceso
- Manejo de errores específico

## Principios de Clean Code Aplicados

### Nombres Descriptivos
- Variables y funciones con nombres que expresan intención
- Sin abreviaciones ambiguas
- Nombres pronunciables y buscables

### Funciones Pequeñas
- Cada función hace una sola cosa
- Máximo 20 líneas por función en la mayoría de casos
- Sin efectos secundarios

### Comentarios Útiles
- Documentación KDoc en funciones públicas
- Explicación de decisiones de diseño
- Sin comentarios redundantes

### Organización del Código
- Separación clara de responsabilidades
- Archivos organizados por feature/capa
- Imports ordenados y sin redundancia

## Instalación

### Prerrequisitos
- Android Studio Hedgehog (2023.1.1) o superior
- Kotlin 1.9.0 o superior
- Android SDK 34
- Min SDK 24 (Android 7.0)
- JDK 8 o superior

### Setup

1. Clonar el repositorio
   ```bash
   git clone https://github.com/tu-usuario/pokefrut20.git
   cd pokefrut20
   ```

2. Abrir en Android Studio
   - File → Open → Seleccionar directorio del proyecto
   - Esperar a que Gradle sincronice

3. Ejecutar la aplicación
   - Run → Run 'app' (o Shift+F10)
   - Seleccionar emulador o dispositivo físico

## API

La aplicación consume [PokéAPI](https://pokeapi.co/):

### Endpoints utilizados

**Lista de Pokémon:**
```
GET /pokemon?limit=100
```

**Detalles de Pokémon:**
```
GET /pokemon/{id}
```

### Ejemplo de respuesta

```json
{
  "id": 25,
  "name": "pikachu",
  "height": 4,
  "weight": 60,
  "sprites": {
    "front_default": "https://raw.githubusercontent.com/.../25.png",
    "back_default": "https://raw.githubusercontent.com/.../back/25.png",
    "front_shiny": "https://raw.githubusercontent.com/.../shiny/25.png",
    "back_shiny": "https://raw.githubusercontent.com/.../back/shiny/25.png"
  }
}
```

## Desarrollo

### Comandos Gradle

```bash
# Build del proyecto
./gradlew build

# Limpiar proyecto
./gradlew clean

# Ejecutar tests
./gradlew test

# Generar APK debug
./gradlew assembleDebug

# Generar APK release
./gradlew assembleRelease
```

### Testing

```bash
# Unit tests
./gradlew testDebugUnitTest

# UI tests (requiere dispositivo/emulador)
./gradlew connectedDebugAndroidTest
```

## Configuración de Dependencias

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.activity:activity-compose:1.9.2")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.3")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.5")
    
    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Image loading
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

## Resolución de Problemas

### Errores Comunes

**Error de compilación con Kotlin-Compose**
- Verificar compatibilidad: Kotlin 1.9.0 con Compose Compiler 1.5.1
- Actualizar `kotlinCompilerExtensionVersion` en `build.gradle.kts`

**RetrofitClient no encontrado**
- Verificar que existe en `data/remote/RetrofitClient.kt`
- Revisar imports en archivos que lo utilizan
- Limpiar y reconstruir proyecto

**Problemas de build**
- Ejecutar `./gradlew clean`
- Luego `./gradlew build`
- Verificar configuración de SDK en `build.gradle.kts`

**Error de red en el emulador**
- Verificar que el emulador tiene acceso a internet
- Revisar configuración de proxy si aplica

## Contribución

### Guía de Contribución

1. Fork del repositorio
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit de cambios (`git commit -m 'feat: agregar nueva funcionalidad'`)
4. Push a branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

### Guidelines de Código

- Seguir principios de Clean Code
- Mantener arquitectura MVVM
- Usar nombres descriptivos para variables y funciones
- Escribir tests para nueva funcionalidad
- Documentar cambios importantes con KDoc
- Commits siguiendo Conventional Commits

### Conventional Commits

```
feat: nueva funcionalidad
fix: corrección de bug
refactor: refactorización sin cambio de funcionalidad
docs: cambios en documentación
test: agregar o modificar tests
style: cambios de formato (sin cambio de código)
chore: tareas de mantenimiento
```


## Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo [LICENSE](LICENSE) para detalles.

## Agradecimientos

- [PokéAPI](https://pokeapi.co/) por proporcionar la API gratuita
- [Android Developers](https://developer.android.com/) por la documentación
- [Jetpack Compose](https://developer.android.com/jetpack/compose) por el framework de UI

## Enlaces Útiles

- [PokéAPI Documentation](https://pokeapi.co/docs/v2)
- [Jetpack Compose Guide](https://developer.android.com/jetpack/compose)
- [Clean Architecture Guide](https://developer.android.com/topic/architecture)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Material Design 3](https://m3.material.io/)

## Contacto

Para preguntas, sugerencias o reportar problemas, por favor abrir un issue en el repositorio.

---

Desarrollado con Kotlin y Jetpack Compose
