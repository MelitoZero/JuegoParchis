# 🎲 Proyecto Parchís P2P
Este repositorio contiene el proyecto del clásico juego de mesa Parchís desarrollado por el  **Equipo TILINES** 😎. El proyecto está desarrollado en Java. El objetivo es crear una versión digital del juego, siguiendo una arquitectura de software MVC(Modelo-Vista-Controlador) ademas de una implementación de conexión de red P2P (Peer-to-Peer) entre jugadores.

### Tecnológias Utilizadas:
- Lenguaje: Java (JDK 21+)
- Interfaz Gráfica (GUI): JavaFX 21
- Gestor de Tareas y Dependencias: Gradle (usando el DSL de Kotlin)

### Ejecutar el Proyecto:
Para compilar y ejecutar el juego en tu máquina, solo necesitas tener el JDK instalado, ya que el proyecto incluye el Gradle Wrapper.


**Prerrequisitos**
- JDK (Java Development Kit): Versión 21 (o superior).
- Git: Para clonar el repositorio.

### Pasos de Ejecución:
1. Clonar el repositorio: Abre una terminal y clona este repositorio con el siguiente comando.
````
git clone https://github.com/MelitoZero/JuegoParchis.git 
cd JuegoParchis
 ````
2. Ejecutar la aplicación: El proyecto usa el Gradle Wrapper (gradlew), que se encarga de descargar la versión correcta de Gradle y JavaFX automáticamente, por lo que puede tardar un ratito mientras configura el proyecto.


Para ejecutar el proyecto, utiliza el comando run de Gradle.


**En Windows (CMD o PowerShell):**
Con cualquiera de las dos funciona en windows
````Bash
./gradlew run
````
````Bash
.\gradlew.bat run.
````
**En Linux o macOS:**
````
Bash
./gradlew run
````
