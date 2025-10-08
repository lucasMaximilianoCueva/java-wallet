# Dependencias Comentadas/Removidas

Este archivo documenta las dependencias que fueron comentadas debido a problemas de disponibilidad.

## 📋 Dependencias No Disponibles en JitPack

### 1. bitcoin-cash-converter
```gradle
// compile 'com.github.sealedtx:bitcoin-cash-converter:1.0'
```
**Razón:** Repositorio no disponible o sin release `1.0`  
**Verificado en:** https://github.com/sealedtx/bitcoin-cash-converter  
**Alternativa:** Implementar conversión manualmente o buscar librería alternativa en Maven Central

---

### 2. tokencore
```gradle
// compile 'com.github.TraderGalax:tokencore:1.2.7'
```
**Razón:** Repositorio no disponible o sin release `1.2.7`  
**Verificado en:** https://github.com/TraderGalax/tokencore  
**Alternativa:** 
- Buscar fork mantenido
- Usar `org.web3j:core:4.2.0` (ya incluido) para funcionalidad similar

---

## ✅ Dependencias Ajustadas

### caffeine
```gradle
// Antes (❌)
compile 'com.github.ben-manes.caffeine:caffeine:3.1.8'

// Después (✅)
compile 'com.github.ben-manes.caffeine:caffeine:2.8.8'
```
**Razón:** Versión 3.1.8 requiere Java 11+, pero la versión 2.8.8 es más compatible  
**Nota:** Caffeine SÍ está disponible en Maven Central con el groupId correcto: `com.github.ben-manes.caffeine`

---

## 🔍 Otras Dependencias Problemáticas Detectadas en Logs

Estas no están en el `build.gradle` principal pero pueden estar en submódulos:

### 3. trident (Tron)
```gradle
// compile 'com.github.lailaibtc:trident:0.0.3'
```
**Razón:** Repositorio no disponible  
**Alternativa:** Usar SDK oficial de Tron si existe

### 4. Blake2b
```gradle
// compile 'com.github.alphazero:Blake2b:bbf094983c'
```
**Razón:** Commit hash como versión no es válido para JitPack  
**Alternativa:** Usar `org.bouncycastle:bcprov-jdk15on` (ya incluido)

### 5. FilecoinJ
```gradle
// compile 'com.github.paipaipaipai:FilecoinJ:0.0.1'
```
**Razón:** Repositorio no disponible  
**Alternativa:** Buscar SDK oficial de Filecoin

---

## 📝 Recomendaciones

### Antes de agregar dependencias de JitPack:

1. **Verificar el repositorio existe:**
   ```bash
   # Visitar: https://github.com/USUARIO/REPO
   ```

2. **Verificar el tag/release existe:**
   ```bash
   # Visitar: https://github.com/USUARIO/REPO/releases
   # O: https://github.com/USUARIO/REPO/tags
   ```

3. **Verificar JitPack puede buildear:**
   ```bash
   # Visitar: https://jitpack.io/#USUARIO/REPO/VERSION
   # Debe mostrar "Build passing" en verde
   ```

4. **Preferir Maven Central cuando sea posible:**
   ```gradle
   repositories {
       mavenCentral()  // Primero
       maven { url 'https://jitpack.io' }  // Último recurso
   }
   ```

---

## 🔄 Cómo Restaurar Dependencias

Si necesitas restaurar alguna dependencia:

1. Verifica que el repositorio ahora existe y tiene el release correcto
2. Descomenta la línea en `build.gradle`
3. Ejecuta `./gradlew clean build --refresh-dependencies`
4. Si falla, busca alternativas en Maven Central

---

**Última actualización:** 2025-10-08  
**Gradle Version:** 5.3  
**Java Version:** 11  
**Kotlin Version:** 1.3.72
