package com.example.mcnews.data.repo;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class ArticleRepositoryImpl_Factory implements Factory<ArticleRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> clientProvider;

  public ArticleRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<OkHttpClient> clientProvider) {
    this.contextProvider = contextProvider;
    this.clientProvider = clientProvider;
  }

  @Override
  public ArticleRepositoryImpl get() {
    return newInstance(contextProvider.get(), clientProvider.get());
  }

  public static ArticleRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<OkHttpClient> clientProvider) {
    return new ArticleRepositoryImpl_Factory(contextProvider, clientProvider);
  }

  public static ArticleRepositoryImpl newInstance(Context context, OkHttpClient client) {
    return new ArticleRepositoryImpl(context, client);
  }
}
