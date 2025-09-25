# PokeFrut 2.0

Una aplicación Android moderna desarrollada en Kotlin que consume la PokéAPI siguiendo principios de Clean Architecture y Clean Code.

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/8e8eb7aa-3216-4514-bcdc-99850fdf23f9" />

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/b4fec409-2635-4d89-8f8e-e91f2482089b" />


## Arquitectura

### Clean Architecture Implementation
```
UI Layer (Jetpack Compose)
├── Components (Screens & UI)
├── ViewModels (State Management)
├── Theme (Material 3)
└── Navigation (Compose Navigation)

Data Layer
├── Network (Retrofit + API)
├── Repository (Single Source of Truth)
└── Models (Data Classes)
```

### Principios Implementados
- SOLID Principles
- Single Source of Truth (SSOT)
- Unidirectional Data Flow (UDF)
- Separation of Concerns
- Clean Code Guidelines

## Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Kotlin | 1.9.22 | Lenguaje principal |
| Jetpack Compose | 1.5.8 | UI moderna |
| Navigation Compose | 2.7.4 | Navegación |
| Retrofit | 2.9.0 | HTTP client |
| Gson | 2.9.0 | JSON parsing |
| Coil | 2.5.0 | Image loading |
| Coroutines | 1.7.3 | Async programming |
| OkHttp | 4.12.0 | HTTP logging |

## Funcionalidades

### Pantalla Principal
- Lista de 100 Pokémon desde PokéAPI
- Navegación a pantalla de detalles
- Manejo de estados de carga y error
- Imágenes optimizadas

### Pantalla de Detalles
- Información completa del Pokémon
- 4 sprites diferentes (normal y shiny, front y back)
- Datos básicos (altura, peso)
- Navegación hacia atrás

## Estructura del Proyecto

```
app/src/main/java/com/example/pokefrut20/
├── data/
│   ├── model/              # Data classes
│   ├── network/            # API service & configuration
│   └── repository/         # Data repository
├── navigation/             # Navigation routes
├── ui/
│   ├── components/         # UI components & screens
│   ├── viewmodel/          # ViewModels
│   ├── theme/              # App theme
│   └── navigation/         # Navigation setup
└── MainActivity.kt         # App entry point
```

## Instalación

### Prerrequisitos
- Android Studio Hedgehog (2023.1.1) o superior
- Kotlin 1.9.22
- Android SDK 34
- Min SDK 24

### Setup

1. Clonar el repositorio
   ```bash
   git clone https://github.com/tu-usuario/pokefrut20.git
   cd pokefrut20
   ```

2. Abrir en Android Studio
   - File → Open → Seleccionar directorio del proyecto
   - Sync Project with Gradle Files

3. Ejecutar la aplicación
   - Run → Run 'app'

## API

La aplicación consume [PokéAPI](https://pokeapi.co/):

### Endpoints utilizados
- `GET /pokemon?limit=100` - Lista de Pokémon
- `GET /pokemon/{id}` - Detalles de Pokémon específico

### Ejemplo de respuesta
```json
{
  "id": 25,
  "name": "pikachu",
  "height": 4,
  "weight": 60,
  "sprites": {
    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
    "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/25.png",
    "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/25.png",
    "back_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/25.png"
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
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

**Error de compatibilidad Kotlin-Compose**
- Verificar que la versión de Kotlin (1.9.22) sea compatible con Compose Compiler (1.5.8)
- Actualizar `kotlinCompilerExtensionVersion` en `build.gradle.kts`

**NetworkModule no encontrado**
- Verificar que el archivo existe en `data/network/NetworkModule.kt`
- Revisar imports en los archivos que lo utilizan

**Problemas de build**
- Ejecutar `./gradlew clean` y luego `./gradlew build`
- Verificar configuración de SDK en `build.gradle.kts`

## Contribución

1. Fork del repositorio
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit de cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a branch (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

### Guidelines
- Seguir principios de Clean Code
- Mantener arquitectura limpia
- Usar nombres descriptivos para variables y funciones
- Escribir tests para nueva funcionalidad
- Documentar cambios importantes

## Licencia

Este proyecto está bajo la Licencia MIT. Ver archivo [LICENSE](LICENSE) para detalles.

## Enlaces

- [PokéAPI Documentation](https://pokeapi.co/docs/v2)
- [Jetpack Compose Guide](https://developer.android.com/jetpack/compose)
- [Clean Architecture Guide](https://developer.android.com/topic/architecture)
