#!/bin/bash

ENV=${1:-development}
BASE_CONFIG="config/default.yaml"
ENV_CONFIG="config/${ENV}.yaml"
OUTPUT_DIR="config/generated"

mkdir -p $OUTPUT_DIR

# Функция для запуска yq через Docker
run_yq() {
    docker run --rm -v "$(pwd):/workdir" mikefarah/yq "$@"
}

# Объединение базовой конфигурации с конфигурацией окружения
run_yq eval-all '. as $item ireduce ({}; . * $item)' "$BASE_CONFIG" "$ENV_CONFIG" > "$OUTPUT_DIR/config.yaml"

# Генерация конфигураций для каждого сервиса
for service in api_gateway api_service hash_generator metrics_service comment_service; do
    run_yq eval ".services.${service}" "$OUTPUT_DIR/config.yaml" > "$OUTPUT_DIR/${service}.yaml"
done

echo "Configuration files generated in $OUTPUT_DIR"