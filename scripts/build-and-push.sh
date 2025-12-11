#!/bin/bash
set -e  # Sale si hay algún error

# ----------------------------------------
# Variables
# ----------------------------------------
DOCKER_USER="agustinherzkovich"
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"

# Módulos backend
BACKEND_MODULES=("agregador" "apiPublica" "apiAdministrativa" "fuenteEstatica" "fuenteDinamica" "fuenteProxy" "estadisticas")

# Módulos frontend
FRONTEND_MODULES=("interfaz-agregador")

# ----------------------------------------
# 1️⃣ Build Maven raíz
# ----------------------------------------
echo "=== BUILD Maven raíz ==="
cd "$ROOT_DIR"
mvn clean install -DskipTests

# ----------------------------------------
# 2️⃣ Build Maven EurekaServer
# ----------------------------------------
echo "=== BUILD Maven EurekaServer ==="
cd "$ROOT_DIR/EurekaServer"
mvn clean package -DskipTests

# ----------------------------------------
# 3️⃣ Build Maven módulos backend
# ----------------------------------------
for module in "${BACKEND_MODULES[@]}"; do
    echo "=== BUILD Maven módulo backend: $module ==="
    cd "$ROOT_DIR/backend/$module"
    mvn clean package -DskipTests
done

# ----------------------------------------
# 4️⃣ Build Maven módulos frontend
# ----------------------------------------
for module in "${FRONTEND_MODULES[@]}"; do
    echo "=== BUILD Maven módulo frontend: $module ==="
    cd "$ROOT_DIR/frontend/$module"
    mvn clean package -DskipTests
done

# ----------------------------------------
# 5️⃣ Build Docker imágenes
# ----------------------------------------
echo "=== BUILD Docker imágenes ==="
# EurekaServer
cd "$ROOT_DIR/EurekaServer"
docker build -t "$DOCKER_USER/eureka-server:latest" .

# Backend
for module in "${BACKEND_MODULES[@]}"; do
    cd "$ROOT_DIR/backend/$module"
    IMAGE_NAME="$DOCKER_USER/$(echo $module | tr '[:upper:]' '[:lower:]'):latest"
    docker build -t $IMAGE_NAME .
done

# Frontend
for module in "${FRONTEND_MODULES[@]}"; do
    cd "$ROOT_DIR/frontend/$module"
    IMAGE_NAME="$DOCKER_USER/$(echo $module | tr '[:upper:]' '[:lower:]'):latest"
    docker build -t $IMAGE_NAME .
done

# ----------------------------------------
# 6️⃣ Push Docker imágenes
# ----------------------------------------
echo "=== PUSH Docker imágenes ==="
ALL_MODULES=("eureka-server" "${BACKEND_MODULES[@]}" "${FRONTEND_MODULES[@]}")
for module in "${ALL_MODULES[@]}"; do
    IMAGE_NAME="$DOCKER_USER/$(echo $module | tr '[:upper:]' '[:lower:]'):latest"
    docker push $IMAGE_NAME
done

echo "=== TODO TERMINADO ==="
