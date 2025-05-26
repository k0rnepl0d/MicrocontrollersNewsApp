-if class com.example.mcnews.data.remote.dto.ArticleDto
-keepnames class com.example.mcnews.data.remote.dto.ArticleDto
-if class com.example.mcnews.data.remote.dto.ArticleDto
-keep class com.example.mcnews.data.remote.dto.ArticleDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
