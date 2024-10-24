# ベースイメージの指定
FROM oracle/jdk:21-ol8

# ワーキングディレクトリの設定
WORKDIR /app

# アプリケーションのJARファイルをコンテナにコピー
COPY build/libs/*.jar app.jar

# ポートの公開
EXPOSE 8080

# アプリケーションの実行コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
