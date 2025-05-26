-if class com.example.mcnews.data.remote.dto.LoginRequestDto
-keepnames class com.example.mcnews.data.remote.dto.LoginRequestDto
-if class com.example.mcnews.data.remote.dto.LoginRequestDto
-keep class com.example.mcnews.data.remote.dto.LoginRequestDtoJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
