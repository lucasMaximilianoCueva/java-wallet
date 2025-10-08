# Guía de Configuración: SonarQube + GitHub Actions para Proyectos Java

Esta guía documenta los requisitos y consideraciones para integrar SonarQube con GitHub Actions en proyectos Java/Kotlin con Gradle.

---

## 📋 Tabla de Compatibilidad

### Gradle vs Java

| Gradle Version | Java Soportado | Notas |
|----------------|----------------|-------|
| 5.0 - 5.4      | Java 8 - 12    | No soporta Java 13+ |
| 6.0 - 6.8      | Java 8 - 15    | Mínimo para Kotlin 1.4+ |
| 7.0 - 7.6      | Java 8 - 17    | Recomendado para Java 17 |
| 8.0+           | Java 8 - 20+   | Última versión estable |

### Kotlin vs Gradle

| Kotlin Version | Gradle Mínimo | Gradle Recomendado |
|----------------|---------------|-------------------|
| 1.3.x          | 4.10          | 5.6+ |
| 1.4.x - 1.6.x  | 5.3           | 6.8+ |
| 1.7.x - 1.8.x  | 6.8           | 7.5+ |
| 1.9.x+         | 6.8           | 7.6+ |

### Spring Boot vs Java/Gradle

| Spring Boot | Java Mínimo | Gradle Mínimo |
|-------------|-------------|---------------|
| 2.1.x       | Java 8      | 4.10+ |
| 2.7.x       | Java 8      | 6.8+ |
| 3.0.x+      | Java 17     | 7.5+ |

---

## 🔍 Problemas Comunes y Soluciones

### 1. Error: `Unrecognized VM option 'MaxPermSize'`

**Causa:** La opción `-XX:MaxPermSize` fue eliminada en Java 8+.

**Solución:**
```properties
# gradle.properties - ANTES (❌)
org.gradle.jvmargs=-Xmx1024m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError

# gradle.properties - DESPUÉS (✅)
org.gradle.jvmargs=-Xmx1024m -XX:+HeapDumpOnOutOfMemoryError
```

---

### 2. Error: `Could not initialize class org.codehaus.groovy.vmplugin.v7.Java7`

**Causa:** Versión de Gradle incompatible con la versión de Java.

**Solución:** Ajustar la versión de Java en el CI según la versión de Gradle.

```yaml
# .github/workflows/build.yml
- name: Set up JDK
  uses: actions/setup-java@v4
  with:
    java-version: 11  # Usar Java 11 para Gradle 5.x
    distribution: 'zulu'
```

---

### 3. Error: `buildscript {} blocks must appear before any plugins {} blocks`

**Causa:** Orden incorrecto de bloques en `build.gradle`.

**Solución:** Orden correcto para Gradle 5.x:

```gradle
// build.gradle - Orden correcto (✅)
buildscript {
    ext.kotlinVersion = '1.3.72'
    ext.springBootVersion = '2.1.18.RELEASE'
    
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }
}

plugins {
    id "org.sonarqube" version "4.4.1.3373"
}

allprojects {
    // configuración
}

subprojects {
    // configuración
}
```

---

### 4. Error: `NoClassDefFoundError: KotlinBasePlugin`

**Causa:** Versión de Kotlin incompatible con la versión de Gradle.

**Solución:** Usar versiones compatibles:
- Gradle 5.3 → Kotlin 1.3.x
- Gradle 6.8+ → Kotlin 1.4.x+
- Gradle 7.0+ → Kotlin 1.5.x+

---

### 5. Error: `No value has been specified for property 'mainClassName'`

**Causa:** Spring Boot 2.x requiere especificar `mainClassName` explícitamente cuando se usa el plugin `application`.

**Solución:** Agregar la propiedad en `build.gradle`:

```gradle
// Para archivos Kotlin, agregar 'Kt' al final del nombre de la clase
apply plugin: 'application'
mainClassName = 'com.example.ApplicationKt'  // Para Kotlin
// mainClassName = 'com.example.Application'  // Para Java
```

---

### 6. Error: Compilación de Kotlin falla con dependencias

**Causa:** Incompatibilidad entre versión de Kotlin y versiones de librerías Kotlin (ej: kotlinx-coroutines).

**Solución:** Usar versiones compatibles de kotlinx-coroutines:

| Kotlin Version | kotlinx-coroutines Compatible |
|----------------|-------------------------------|
| 1.3.x          | 1.3.x                        |
| 1.4.x - 1.5.x  | 1.4.x - 1.5.x                |
| 1.6.x - 1.8.x  | 1.6.x - 1.7.x                |
| 1.9.x+         | 1.7.x+                       |

```gradle
// Ejemplo para Kotlin 1.3.72
compile "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"

// Ejemplo para Kotlin 1.9.10
compile "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"
```

---

## 🚀 Configuración Paso a Paso

### Paso 1: Verificar Versiones Actuales

```bash
# Ver versión de Gradle
./gradlew --version

# Ver versión de Java configurada
cat .github/workflows/*.yml | grep java-version

# Ver versiones en build.gradle
cat build.gradle | grep "ext\."
```

### Paso 2: Determinar Compatibilidad

Usa la tabla de compatibilidad arriba para determinar:
1. ¿Qué versión de Java necesitas?
2. ¿Tu versión de Gradle es compatible?
3. ¿Tus versiones de Kotlin/Spring Boot son compatibles?

### Paso 3: Configurar GitHub Actions

```yaml
# .github/workflows/build.yml
name: Build and Analyze

on:
  push:
    branches:
      - master
      - main
  pull_request:
    types: [opened, synchronize, reopened]

jobs:
  build:
    name: Build and analyze
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0  # Necesario para SonarQube
          
      - name: Set up JDK
        uses: actions/setup-java@v4
        with:
          java-version: 11  # Ajustar según tu Gradle
          distribution: 'zulu'
          
      - name: Cache SonarQube packages
        uses: actions/cache@v4
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar
          restore-keys: ${{ runner.os }}-sonar
          
      - name: Cache Gradle packages
        uses: actions/cache@v4
        with:
          path: ~/.gradle/caches
          key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle') }}
          restore-keys: ${{ runner.os }}-gradle
          
      - name: Build and analyze
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          SONAR_HOST_URL: ${{ secrets.SONAR_HOST_URL }}
        run: ./gradlew build sonar --info
```

### Paso 4: Configurar SonarQube Plugin

```gradle
// build.gradle
plugins {
    id "org.sonarqube" version "4.4.1.3373"  // Versión estable
}

sonar {
    properties {
        property "sonar.projectKey", "tu-org_tu-proyecto"
        property "sonar.projectName", "Tu Proyecto"
        property "sonar.organization", "tu-organizacion"  // Si usas SonarCloud
        property "sonar.host.url", "https://sonarcloud.io"  // O tu instancia
    }
}
```

### Paso 5: Configurar Secrets en GitHub

1. Ve a tu repositorio → **Settings** → **Secrets and variables** → **Actions**
2. Agrega los siguientes secrets:
   - `SONAR_TOKEN`: Token generado en SonarQube/SonarCloud
   - `SONAR_HOST_URL`: URL de tu instancia (ej: `https://sonarcloud.io`)

---

## 📝 Checklist de Verificación

Antes de hacer push, verifica:

- [ ] `gradle.properties` no contiene opciones JVM obsoletas (`MaxPermSize`, `PermSize`)
- [ ] Versión de Java en CI es compatible con tu versión de Gradle
- [ ] Orden correcto en `build.gradle`: `buildscript` → `plugins` → `allprojects` → `subprojects`
- [ ] Versiones de Kotlin/Spring Boot son compatibles con tu Gradle
- [ ] Plugin de SonarQube está correctamente configurado
- [ ] Secrets `SONAR_TOKEN` y `SONAR_HOST_URL` están configurados en GitHub
- [ ] `fetch-depth: 0` está configurado en el checkout (necesario para análisis completo)

---

## 🔧 Configuraciones Recomendadas

### Para Proyectos Nuevos (2024+)

```gradle
// build.gradle
buildscript {
    ext.kotlinVersion = '1.9.20'
    ext.springBootVersion = '3.2.0'
}

plugins {
    id "org.sonarqube" version "4.4.1.3373"
}
```

```yaml
# .github/workflows/build.yml
java-version: 17
```

```properties
# gradle.properties
org.gradle.jvmargs=-Xmx2048m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
```

```properties
# gradle/wrapper/gradle-wrapper.properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-all.zip
```

### Para Proyectos Legacy

Si no puedes actualizar Gradle:

1. **Identifica tu versión de Gradle**
2. **Usa la tabla de compatibilidad** para elegir versiones apropiadas
3. **Ajusta Java en el CI** según tu Gradle
4. **Usa versiones compatibles** de Kotlin/Spring Boot

---

## 🐛 Debugging

### Ver logs completos del build

```bash
./gradlew build sonar --info --stacktrace
```

### Verificar configuración de SonarQube

```bash
./gradlew sonarqube --dry-run
```

### Limpiar caché de Gradle

```bash
./gradlew clean --refresh-dependencies
rm -rf ~/.gradle/caches/
```

---

## 📚 Referencias

- [Gradle Compatibility Matrix](https://docs.gradle.org/current/userguide/compatibility.html)
- [Kotlin Gradle Plugin Compatibility](https://kotlinlang.org/docs/gradle-configure-project.html#apply-the-plugin)
- [Spring Boot System Requirements](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.system-requirements)
- [SonarQube Gradle Plugin](https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-for-gradle/)

---

## 💡 Tips Adicionales

### Actualizar Gradle Wrapper

```bash
# Actualizar a una versión específica
./gradlew wrapper --gradle-version=7.6.4

# Actualizar a la última versión
./gradlew wrapper --gradle-version=latest
```

### Migrar de compile a implementation

Gradle 7+ deprecó `compile`. Usa:

```gradle
// Antes (❌)
compile 'org.example:library:1.0'
testCompile 'junit:junit:4.12'

// Después (✅)
implementation 'org.example:library:1.0'
testImplementation 'junit:junit:4.12'
```

### Configurar análisis incremental de SonarQube

```gradle
sonar {
    properties {
        property "sonar.projectKey", "tu-proyecto"
        property "sonar.projectName", "Tu Proyecto"
        
        // Análisis incremental (solo cambios en PRs)
        property "sonar.pullrequest.key", System.getenv("GITHUB_PR_NUMBER")
        property "sonar.pullrequest.branch", System.getenv("GITHUB_HEAD_REF")
        property "sonar.pullrequest.base", System.getenv("GITHUB_BASE_REF")
    }
}
```

---

## 🛡️ Checklist de Prevención de Errores

Antes de hacer commit y push, verifica estos puntos críticos:

### Compatibilidad de Versiones

- [ ] **Gradle vs Java**: Verifica que tu versión de Java en CI sea compatible con Gradle
  ```bash
  # Gradle 5.x → Java 8-12
  # Gradle 6.x → Java 8-15
  # Gradle 7.x → Java 8-17
  ```

- [ ] **Kotlin vs Gradle**: Verifica compatibilidad
  ```bash
  # Kotlin 1.3.x → Gradle 4.10+
  # Kotlin 1.4-1.6 → Gradle 5.3+
  # Kotlin 1.7-1.8 → Gradle 6.8+
  # Kotlin 1.9+ → Gradle 6.8+
  ```

- [ ] **kotlinx-coroutines vs Kotlin**: Deben coincidir las versiones mayores
  ```gradle
  // Kotlin 1.3.x → kotlinx-coroutines 1.3.x
  // Kotlin 1.9.x → kotlinx-coroutines 1.7.x+
  ```

- [ ] **Spring Boot vs Java**: Verifica requisitos
  ```bash
  # Spring Boot 2.x → Java 8+
  # Spring Boot 3.x → Java 17+
  ```

### Configuración de Gradle

- [ ] **gradle.properties**: Sin opciones JVM obsoletas
  ```properties
  # ❌ NO usar: -XX:MaxPermSize, -XX:PermSize
  # ✅ Usar: -Xmx, -XX:+HeapDumpOnOutOfMemoryError
  ```

- [ ] **build.gradle**: Orden correcto de bloques
  ```gradle
  // 1. buildscript {}
  // 2. plugins {}
  // 3. allprojects {}
  // 4. subprojects {}
  ```

- [ ] **Spring Boot 2.x**: Usar `bootJar` en lugar de `bootRepackage`
  ```gradle
  bootJar.enabled = false  // ✅
  // bootRepackage.enabled = false  // ❌
  ```

- [ ] **Application plugin**: Especificar mainClassName
  ```gradle
  apply plugin: 'application'
  mainClassName = 'com.example.ApplicationKt'  // Para Kotlin
  ```

### Dependencias

- [ ] **Versiones consistentes**: Todas las dependencias de Kotlin deben usar la misma versión
  ```gradle
  ext.kotlinVersion = '1.3.72'
  compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
  compile "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
  compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
  ```

- [ ] **Librerías modernas**: Evitar versiones muy antiguas que puedan tener vulnerabilidades
  ```gradle
  // ⚠️ Revisar: mysql-connector-java:5.1.40 (muy antiguo)
  // ⚠️ Revisar: HikariCP, caffeine, etc.
  ```

### GitHub Actions

- [ ] **Secrets configurados**: SONAR_TOKEN y SONAR_HOST_URL
- [ ] **fetch-depth: 0**: Necesario para análisis completo de SonarQube
- [ ] **Versión de Java**: Coincide con requisitos de Gradle

### Testing Local

Antes de hacer push, ejecuta localmente:

```bash
# Limpiar build anterior
./gradlew clean

# Build completo con tests
./gradlew build --info

# Verificar que SonarQube funciona (requiere SONAR_TOKEN)
./gradlew sonar --info
```

### Errores Comunes a Evitar

| Error | Causa | Prevención |
|-------|-------|------------|
| MaxPermSize | Opción JVM obsoleta | Eliminar de gradle.properties |
| NoClassDefFoundError Groovy | Java muy nuevo para Gradle | Usar Java compatible |
| buildscript order | Orden incorrecto | buildscript antes de plugins |
| bootRepackage | API de Spring Boot 1.x | Usar bootJar en Spring Boot 2.x |
| mainClassName missing | Plugin application sin config | Especificar mainClassName |
| Kotlin compilation error | Versiones incompatibles | Verificar tabla de compatibilidad |

---

## 📋 Matriz de Compatibilidad Rápida

Para proyectos con **Gradle 5.3**:

```gradle
// build.gradle
buildscript {
    ext.kotlinVersion = '1.3.72'
    ext.springBootVersion = '2.1.18.RELEASE'
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
}
```

```yaml
# .github/workflows/build.yml
java-version: 11
```

```properties
# gradle.properties
org.gradle.jvmargs=-Xmx1024m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
```

---

**Última actualización:** 2025-10-08
