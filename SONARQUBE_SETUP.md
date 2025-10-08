# SonarQube Integration - Maven Project

## ✅ Configuración Completada

Este proyecto está configurado para análisis con **SonarCloud** usando **Maven**.

---

## 📋 Archivos Configurados

### 1. `pom.xml`
- ✅ Plugin `sonar-maven-plugin` (v3.10.0.2594)
- ✅ Plugin `jacoco-maven-plugin` (v0.8.11) para cobertura de código
- ✅ Propiedades de SonarQube configuradas

### 2. `.github/workflows/build.yml`
- ✅ GitHub Actions configurado para Maven
- ✅ Cache de Maven y SonarQube
- ✅ Comando de análisis integrado

### 3. `sonar-project.properties` (Opcional)
- ✅ Configuración adicional de SonarQube
- ✅ Paths de código fuente y tests
- ✅ Configuración de JaCoCo

---

## 🔑 Configurar Secrets en GitHub

Antes de hacer push, configura estos secrets en tu repositorio:

1. Ve a: **Settings** → **Secrets and variables** → **Actions**
2. Agrega los siguientes secrets:

### SONAR_TOKEN
```
Valor: Tu token de SonarCloud
Obtener en: https://sonarcloud.io/account/security
```

### SONAR_HOST_URL
```
Valor: https://sonarcloud.io
```

---

## 🚀 Ejecutar Localmente

### Build normal
```bash
mvn clean verify
```

### Build + Análisis SonarQube
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=lucasMaximilianoCueva_java-wallet \
  -Dsonar.organization=lucasMaximilianoCueva \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=TU_TOKEN_AQUI
```

### Solo análisis (sin build)
```bash
mvn sonar:sonar \
  -Dsonar.projectKey=lucasMaximilianoCueva_java-wallet \
  -Dsonar.organization=lucasMaximilianoCueva \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=TU_TOKEN_AQUI
```

---

## 📊 Verificar Resultados

Después del push, verifica:

1. **GitHub Actions**: 
   - Ve a la pestaña **Actions** en tu repositorio
   - Verifica que el workflow "Build and analyze" se ejecute correctamente

2. **SonarCloud**:
   - Ve a: https://sonarcloud.io/organizations/lucasmaximilianocu eva/projects
   - Busca el proyecto `java-wallet`
   - Revisa métricas de calidad, bugs, vulnerabilidades, code smells

---

## 🔧 Personalizar Configuración

### Cambiar organización o projectKey

Edita `pom.xml`:
```xml
<properties>
    <sonar.organization>TU_ORGANIZACION</sonar.organization>
    <sonar.projectKey>TU_ORGANIZACION_TU_PROYECTO</sonar.projectKey>
</properties>
```

Y actualiza `.github/workflows/build.yml`:
```yaml
run: mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=TU_ORGANIZACION_TU_PROYECTO
```

### Excluir archivos del análisis

Edita `sonar-project.properties`:
```properties
sonar.exclusions=**/generated/**,**/dto/**,**/config/**
sonar.test.exclusions=**/integration/**
```

### Configurar umbrales de Quality Gate

En SonarCloud:
1. Ve a **Project Settings** → **Quality Gate**
2. Selecciona o crea un Quality Gate personalizado
3. Define umbrales para:
   - Coverage mínimo (ej: 80%)
   - Duplicación máxima (ej: 3%)
   - Bugs/Vulnerabilities: 0

---

## 🐛 Troubleshooting

### Error: "HeadlessException: No X11 DISPLAY variable was set"
**Causa:** Tests de UI (Swing/AWT) fallan en CI porque no hay display gráfico.

**Solución:** Ya configurado en `pom.xml`:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0</version>
    <configuration>
        <argLine>-Djava.awt.headless=true ${argLine}</argLine>
    </configuration>
</plugin>
```

### Error: "Not authorized. Please check the properties sonar.login"
**Solución:** Verifica que `SONAR_TOKEN` esté configurado correctamente en GitHub Secrets.

### Error: "Project key already exists"
**Solución:** Cambia el `sonar.projectKey` en `pom.xml` y en el workflow.

### Error: "Coverage report not found"
**Solución:** Asegúrate de ejecutar `mvn verify` antes de `sonar:sonar` para generar el reporte de JaCoCo.

### Build exitoso pero sin análisis en SonarCloud
**Solución:** 
1. Verifica que `SONAR_HOST_URL` esté configurado
2. Revisa los logs del workflow en GitHub Actions
3. Verifica que el proyecto exista en SonarCloud

---

## 📚 Plugins Incluidos

### 1. SonarQube Maven Plugin
- **Versión:** 3.10.0.2594
- **Función:** Ejecuta el análisis de código y envía resultados a SonarCloud
- **Documentación:** https://docs.sonarsource.com/sonarqube/latest/analyzing-source-code/scanners/sonarscanner-for-maven/

### 2. JaCoCo Maven Plugin
- **Versión:** 0.8.11
- **Función:** Genera reportes de cobertura de código
- **Documentación:** https://www.jacoco.org/jacoco/trunk/doc/maven.html

### 3. Maven Surefire Plugin
- **Versión:** 3.0.0
- **Función:** Ejecuta tests unitarios
- **Documentación:** https://maven.apache.org/surefire/maven-surefire-plugin/

---

## ✅ Checklist de Verificación

Antes de hacer push:

- [ ] `SONAR_TOKEN` configurado en GitHub Secrets
- [ ] `SONAR_HOST_URL` configurado en GitHub Secrets
- [ ] Proyecto creado en SonarCloud (se crea automáticamente en el primer análisis)
- [ ] `sonar.organization` correcto en `pom.xml`
- [ ] `sonar.projectKey` correcto en `pom.xml` y workflow
- [ ] Tests existen en `src/test/java`
- [ ] Build local exitoso: `mvn clean verify`

---

## 🎯 Próximos Pasos

1. **Commit y push** de los cambios:
   ```bash
   git add pom.xml .github/workflows/build.yml sonar-project.properties
   git commit -m "feat: add SonarQube integration with Maven"
   git push
   ```

2. **Verificar el workflow** en GitHub Actions

3. **Revisar resultados** en SonarCloud

4. **Configurar Quality Gate** según tus necesidades

5. **Agregar badge** de SonarCloud al README (opcional):
   ```markdown
   [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=lucasMaximilianoCueva_java-wallet&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=lucasMaximilianoCueva_java-wallet)
   ```

---

**Última actualización:** 2025-10-08  
**Build Tool:** Maven 3.x  
**Java Version:** 11  
**SonarQube Plugin:** 3.10.0.2594
